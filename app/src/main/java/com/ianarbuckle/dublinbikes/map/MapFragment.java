package com.ianarbuckle.dublinbikes.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.ianarbuckle.dublinbikes.BaseFragment;
import com.ianarbuckle.dublinbikes.R;
import com.ianarbuckle.dublinbikes.models.Station;
import com.ianarbuckle.dublinbikes.utiity.Constants;
import com.ianarbuckle.dublinbikes.utiity.ErrorFragmentDialog;
import com.ianarbuckle.dublinbikes.utiity.PopupFragmentDialog;

import java.util.List;


/**
 * Created by Ian Arbuckle on 15/11/2016.
 *
 */

public class MapFragment extends BaseFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    LocationListener, MapView {

  private GoogleMap map;
  private GoogleApiClient googleApiClient;
  LocationRequest locationRequest;
  Location lastLocation;
  Marker currentLocation;
  ClusterManager<MarkerItemModel> clusterManager;

  private MapPresenterImpl presenter;

  public static Fragment newInstance() {
    return new MapFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_map, container, false);
  }

  @Override
  protected void initPresenter() {
    presenter = new MapPresenterImpl(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      checkPermission();
    }
    getStationsList();
    initMap();
  }

  private void getStationsList() {
    presenter.fetchStations();
  }


  private void initMap() {
    SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
        .findFragmentById(R.id.fragment_map);

    supportMapFragment = initFragment(supportMapFragment);

    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setMapToolbarEnabled(false);

        clusterManager = new ClusterManager<>(getContext(), map);

        final CustomMarkerRenderer clusterRenderer = new CustomMarkerRenderer(getContext(), map, clusterManager);
        clusterManager.setRenderer(clusterRenderer);

        map.setOnCameraChangeListener(clusterManager);
        map.setOnMarkerClickListener(clusterManager);

        setupLocations();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          if (isPermissionGranted()) {
            buildGoogleApiClient();
            map.setMyLocationEnabled(true);
          }
        } else {
          buildGoogleApiClient();
          map.setMyLocationEnabled(true);
        }

      }
    });
  }

  @NonNull
  private FragmentTransaction getFragmentTransaction() {
    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
    Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(Constants.TAG_POPUP_DIALOG);

    if (fragment != null) {
      fragmentTransaction.remove(fragment);
    }

    fragmentTransaction.addToBackStack(null);
    return fragmentTransaction;
  }

  @NonNull
  private SupportMapFragment initFragment(SupportMapFragment supportMapFragment) {
    if (supportMapFragment == null) {
      FragmentManager fragmentManager = getFragmentManager();
      supportMapFragment = new SupportMapFragment();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      fragmentTransaction.replace(R.id.fragment_map, supportMapFragment).commit();
      setRetainInstance(true);
    }
    return supportMapFragment;
  }

  protected synchronized void buildGoogleApiClient() {
    googleApiClient = new GoogleApiClient.Builder(this.getContext())
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();
    googleApiClient.connect();
  }

  @Override
  public void onConnected(Bundle bundle) {
    lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    locationRequest = new LocationRequest();
    locationRequest.setInterval(1000);
    locationRequest.setFastestInterval(1000);
    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    if (isPermissionGranted()) {
      LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }
  }

  private boolean isPermissionGranted() {
    return ContextCompat.checkSelfPermission(getActivity(),
        Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED;
  }

  @Override
  public void onConnectionSuspended(int count) {
    //Stub method
  }

  @Override
  public void onConnectionFailed(@Nullable ConnectionResult connectionResult) {
    //Stub method
  }

  @Override
  public void onLocationChanged(Location location) {
    lastLocation = location;
    if (currentLocation != null) {
      currentLocation.remove();
    }

    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

    MarkerOptions markerOptions = new MarkerOptions();
    markerOptions.position(latLng);
    currentLocation = map.addMarker(markerOptions);

    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
    map.animateCamera(CameraUpdateFactory.zoomTo(13));

    if (googleApiClient != null) {
      LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }
  }

  private void setupLocations() {

    int size = presenter.onResponseStations().size();
    final List<Station> stationList = presenter.onResponseStations();

    for (int i = 0; i < size; i++) {
      MarkerItemModel markerItemModel = presenter.getMarkerModelItems(stationList, i);
      clusterManager.getClusterMarkerCollection().getMarkers();
      clusterManager.addItem(markerItemModel);
      clusterManager.cluster();
      clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MarkerItemModel>() {
        @Override
        public boolean onClusterItemClick(MarkerItemModel markerItemModel) {

          FragmentTransaction fragmentTransaction = getFragmentTransaction();

          String nameMarker = markerItemModel.getName();
          String addressMarker = markerItemModel.getAddress();
          String statusMarker = markerItemModel.getStatus();
          int slotsMarker = markerItemModel.getSlots();
          int availMarker = markerItemModel.getAvailable();
          long update = markerItemModel.getUpdate();

          DialogFragment dialogFragment = PopupFragmentDialog.newInstance(nameMarker, addressMarker, statusMarker, slotsMarker, availMarker, update);
          dialogFragment.onCreateAnimation(R.anim.slide_up, true, R.anim.slide_down);
          dialogFragment.show(fragmentTransaction, Constants.TAG_POPUP_DIALOG);

          return false;
        }
      });
    }
  }

  private boolean checkPermission() {
    if (ContextCompat.checkSelfPermission(getActivity(),
        Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {

        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
            Constants.PERMISSION_REQUEST_ACCESS_LOCATION);
      } else {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.PERMISSION_REQUEST_ACCESS_LOCATION);
      }
      return false;
    } else {
      return true;
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case Constants.PERMISSION_REQUEST_ACCESS_LOCATION: {
        if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

          if (googleApiClient == null) {
            buildGoogleApiClient();
          }
          map.setMyLocationEnabled(true);
        }
      }
    }
  }

  @Override
  public void hideProgressDialog() {

  }

  @Override
  public void showProgressDialog() {
  }

  @Override
  public void onFailureMessage(Throwable throwable) {
    FragmentTransaction fragmentTransaction = getFragmentTransaction();

    DialogFragment dialogFragment = ErrorFragmentDialog.newInstance(R.string.error_message_internet);
    dialogFragment.show(fragmentTransaction, Constants.TAG_ERROR_DIALOG);
  }

}
