package com.ianarbuckle.dublinbikes.stations.station;

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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ianarbuckle.dublinbikes.BaseFragment;
import com.ianarbuckle.dublinbikes.R;
import com.ianarbuckle.dublinbikes.utiity.TextUtils;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 14/12/2016.
 *
 */

public class StationFragment extends BaseFragment {

  public static final String NAME_KEY = "name";
  public static final String STANDS_KEY = "stands";
  public static final String BIKES_KEY = "available";
  public static final String SLOTS_KEY = "slots";
  public static final String STATUS_KEY = "status";
  public static final String UPDATE_KEY = "update";
  public static final String LAT_KEY = "lat";
  public static final String LNG_KEY = "lng";

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

  GoogleMap map;

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

    if(status.equals("OPEN")) {
      statusTv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
    } else {
      statusTv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
    }

    shareIv.setVisibility(View.GONE);
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

        double lat = getActivity().getIntent().getDoubleExtra(LAT_KEY, 0);
        double lng = getActivity().getIntent().getDoubleExtra(LNG_KEY, 0);
        LatLng latLng = new LatLng(lat, lng);

        map.addMarker(new MarkerOptions().position(latLng));
        map.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(latLng, 13)));

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
}
