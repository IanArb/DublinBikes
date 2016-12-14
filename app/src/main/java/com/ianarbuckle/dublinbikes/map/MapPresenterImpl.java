package com.ianarbuckle.dublinbikes.map;

import com.google.android.gms.maps.model.LatLng;
import com.ianarbuckle.dublinbikes.models.Station;
import com.ianarbuckle.dublinbikes.network.DublinBikesCaller;
import com.ianarbuckle.dublinbikes.network.DublinBikesServiceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ian Arbuckle on 07/12/2016.
 *
 */

public class MapPresenterImpl implements MapPresenter {

  private MapView view;

  DublinBikesServiceAPI serviceAPI;

  private List<Station> stationList = new ArrayList<>();

  public MapPresenterImpl(MapView view) {
    this.view = view;
  }

  @Override
  public List<Station> onResponseStations() {

    return stationList;
  }

  @Override
  public MarkerItemModel getMarkerModelItems(List<Station> stations, int size) {
    String name = stations.get(size).getName();
    double lng = stations.get(size).getPosition().getLng();
    double lat = stations.get(size).getPosition().getLat();
    LatLng latLng = new LatLng(lat, lng);
    String address = stations.get(size).getAddress();
    String status = stations.get(size).getStatus();
    int avail = stations.get(size).getAvailableBikes();
    int slots = stations.get(size).getAvailableBikeStands();
    long update = stations.get(size).getLastUpdate();

    return new MarkerItemModel(latLng, name, address, status, slots, avail, update);
  }


//  private double showDistance() {
//    double distance = 0;
//    int size = stationList.size();
//
//    for(int i = 0; i < size; i++) {
//      double lat = stationList.get(i).getPosition().getLat();
//      double lng = stationList.get(i).getPosition().getLng();
//      LatLng latLng = new LatLng(lat, lng);
//      LatLng location = getLocation(lastLocation);
//      distance = SphericalUtil.computeDistanceBetween(location, latLng);
//    }
//    return distance;
//  }

  @Override
  public void fetchStations() {
      serviceAPI = DublinBikesCaller.getDublinBikesServiceAPI();

      view.showProgressDialog();

      Call<List<Station>> call = serviceAPI.getDublinStations();

      getAllStationsResponse(call);
  }

  private void getAllStationsResponse(Call<List<Station>> call) {
    call.enqueue(new Callback<List<Station>>() {
      @Override
      public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
        view.hideProgressDialog();
        stationList = response.body();
      }

      @Override
      public void onFailure(Call<List<Station>> call, Throwable throwable) {
        view.hideProgressDialog();
        view.onFailureMessage(throwable);
      }
    });

  }

}
