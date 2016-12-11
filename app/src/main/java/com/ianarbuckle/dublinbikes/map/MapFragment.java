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
import com.ianarbuckle.dublinbikes.utiity.ErrorFragmentDialog;
import com.ianarbuckle.dublinbikes.utiity.PopupFragmentDialog;

import java.util.List;

/**
 * Created by Ian Arbuckle on 15/11/2016.
 *
 */

public class MapFragment extends BaseFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    LocationListener, MapView {

  private static final int PERMISSION_REQUEST_ACCESS_LOCATION = 99;
  private static final String TAG = "dialogFragment";
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
    initMap();
    getStationsList();
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
    Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(TAG);

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
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      supportMapFragment = SupportMapFragment.newInstance();
      fragmentTransaction.replace(R.id.fragment_map, supportMapFragment).commit();
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
  }

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {
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

    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
    map.animateCamera(CameraUpdateFactory.zoomTo(12));

    if (googleApiClient != null) {
      LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }
    //TODO Calculate distance from location
//    showDistance(distance);
  }

  //TODO Calculate distance from location
//  private void showDistance(double distance) {
//    lastLocation.getLongitude();
//    lastLocation.getLatitude();
//    int size = presenter.onResponseStations().size();
//    final List<Station> stationList = presenter.onResponseStations();
//    for (int i = 0; i < size; i++) {
//      String name = stationList.get(i).getName();
//      double lat = stationList.get(i).getPosition().getLat();
//      double lng = stationList.get(i).getPosition().getLng();
//
//      LatLng latLngTo = new LatLng(lat, lng);
//
//      LatLng locLatLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
//
//      distance = SphericalUtil.computeDistanceBetween(locLatLng, latLngTo);
//
//      Toast.makeText(getContext(), "Name: " + name + " Distance: " + distance, Toast.LENGTH_SHORT).show();
//    }
//  }

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
          float update = markerItemModel.getUpdate();
          //TODO Calculate distance from location
//          markerItemModel.setDistance(distance);
//          distance = markerItemModel.getDistance();

          DialogFragment dialogFragment = PopupFragmentDialog.newInstance(nameMarker, addressMarker, statusMarker, slotsMarker, availMarker, update);
          dialogFragment.onCreateAnimation(R.anim.slide_up, true, R.anim.slide_down);
          dialogFragment.show(fragmentTransaction, TAG);

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
            PERMISSION_REQUEST_ACCESS_LOCATION);
      } else {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_ACCESS_LOCATION);
      }
      return false;
    } else {
      return true;
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case PERMISSION_REQUEST_ACCESS_LOCATION: {
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

    DialogFragment dialogFragment = ErrorFragmentDialog.newInstance(throwable.getMessage());
    dialogFragment.show(fragmentTransaction, TAG);
  }

}
