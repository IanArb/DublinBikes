package com.ianarbuckle.dublinbikes.stations.station;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;
import com.ianarbuckle.dublinbikes.BaseFragment;
import com.ianarbuckle.dublinbikes.R;
import com.ianarbuckle.dublinbikes.utiity.TextUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ian Arbuckle on 14/12/2016.
 *
 */

public class StationFragment extends BaseFragment implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,
    LocationListener {

  public static final String NAME_KEY = "name";
  public static final String STANDS_KEY = "stands";
  public static final String BIKES_KEY = "available";
  public static final String SLOTS_KEY = "slots";
  public static final String STATUS_KEY = "status";
  public static final String UPDATE_KEY = "update";
  public static final String LAT_KEY = "lat";
  public static final String LNG_KEY = "lng";

  private static final int PERMISSION_REQUEST_ACCESS_LOCATION = 99;

  @BindView(R.id.nameTv)
  TextView nameTv;

  @BindView(R.id.bikeStandsTv)
  TextView bikeStandsTv;

  @BindView(R.id.statusTv)
  TextView statusTv;

  @BindView(R.id.bikesTv)
  TextView bikesTv;

  @BindView(R.id.slotsTv)
  TextView slotsTv;

  @BindView(R.id.updateTv)
  TextView updateTv;

  @BindView(R.id.shareIv)
  ImageView shareIv;

  @BindView(R.id.distanceTv)
  TextView distanceTv;

  GoogleMap map;
  private GoogleApiClient googleApiClient;
  LocationRequest locationRequest;
  Location lastLocation;
  Marker currentLocation;
  Marker stationLocation;
  Polyline polyline;

  double distance;

  public static StationFragment newInstance() {
    return new StationFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_station, container, false);
  }

  @Override
  public void onStart() {
    super.onStart();
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      checkPermission();
    }
    initMap();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
  }

  private void initView() {
    String name = getActivity().getIntent().getStringExtra(NAME_KEY);
    int stands = getActivity().getIntent().getIntExtra(STANDS_KEY, 0);
    int bikes = getActivity().getIntent().getIntExtra(BIKES_KEY, 0);
    int slots = getActivity().getIntent().getIntExtra(SLOTS_KEY, 0);
    String status = getActivity().getIntent().getStringExtra(STATUS_KEY);
    long update = getActivity().getIntent().getLongExtra(UPDATE_KEY, 0);
    String format = TextUtils.getDuration(update);
    nameTv.setText(name);
    bikeStandsTv.setText(String.valueOf(stands));
    bikesTv.setText(String.valueOf(bikes));
    slotsTv.setText(String.valueOf(slots));
    statusTv.setText(status);
    updateTv.setText(format);

    if (status.equals("OPEN")) {
      statusTv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
    } else {
      statusTv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
    }
  }

  @OnClick(R.id.shareIv)
  public void shareContent() {
    String name = getActivity().getIntent().getStringExtra(NAME_KEY);
    int bikes = getActivity().getIntent().getIntExtra(BIKES_KEY, 0);
    int slots = getActivity().getIntent().getIntExtra(SLOTS_KEY, 0);
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_TEXT, "Station - " + name + "Bikes - " + bikes + "Slots - " + slots + "Distance - " + distance);
    startActivity(intent);
  }

  @Override
  protected void initPresenter() {
    //Stub method
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
        map.addPolyline(new PolylineOptions().geodesic(true));

        double lat = getActivity().getIntent().getDoubleExtra(LAT_KEY, 0);
        double lng = getActivity().getIntent().getDoubleExtra(LNG_KEY, 0);
        LatLng latLng = new LatLng(lat, lng);

        stationLocation = map.addMarker(new MarkerOptions().position(latLng));
        map.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(latLng, 13)));

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
  public void onConnectionSuspended(int size) {

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

    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
    map.animateCamera(CameraUpdateFactory.zoomTo(12));

    if (googleApiClient != null) {
      LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    showDistance();

    String format = TextUtils.formatNumber(distance);
    distanceTv.setText(format);

  }

  private double showDistance() {
    lastLocation.getLongitude();
    lastLocation.getLatitude();

    double lat = getActivity().getIntent().getDoubleExtra(LAT_KEY, 0);
    double lng = getActivity().getIntent().getDoubleExtra(LNG_KEY, 0);

    LatLng latLngTo = new LatLng(lat, lng);

    LatLng locLatLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());

    distance = SphericalUtil.computeDistanceBetween(locLatLng, latLngTo);

    return distance;
  }


}
