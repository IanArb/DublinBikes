package com.ianarbuckle.dublinbikes.map;

import com.google.android.gms.maps.model.LatLng;
import com.ianarbuckle.dublinbikes.models.Contract;
import com.ianarbuckle.dublinbikes.models.Station;
import com.ianarbuckle.dublinbikes.network.DublinBikesServiceAPI;
import com.ianarbuckle.dublinbikes.network.NetworkClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ian Arbuckle on 07/12/2016.
 *
 */

class MapPresenterImpl implements MapPresenter {

  private static final String BASE_URL = "https://api.jcdecaux.com/";

  private MapView view;

  private DublinBikesServiceAPI serviceAPI;

  private List<Station> stationList = new ArrayList<>();

  MapPresenterImpl(MapView view) {
    this.view = view;
  }

  private DublinBikesServiceAPI getDublinBikesServiceAPI() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    return retrofit.create(DublinBikesServiceAPI.class);
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
    float update = stations.get(size).getLastUpdate();

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
    if (NetworkClient.isConnectedOrConnecting(view.getContext())) {
      serviceAPI = getDublinBikesServiceAPI();

      view.showProgressDialog();

      Call<List<Station>> call = serviceAPI.getDublinStations();

      getAllStationsResponse(call);
    }
  }

  @Override
  public void fetchStation() {
    if (NetworkClient.isConnectedOrConnecting(view.getContext())) {
      serviceAPI = getDublinBikesServiceAPI();

      view.showProgressDialog();

      Call<List<Station>> call = serviceAPI.getAllStations();

      getAllStationsResponse(call);
    }
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

  @Override
  public void fetchContracts() {
    DublinBikesServiceAPI serviceAPI = getDublinBikesServiceAPI();

    Call<List<Contract>> call = serviceAPI.getContracts();

    getContractResponse(call);
  }

  private void getContractResponse(Call<List<Contract>> call) {
    call.enqueue(new Callback<List<Contract>>() {
      @Override
      public void onResponse(Call<List<Contract>> call, Response<List<Contract>> response) {
        view.hideProgressDialog();
      }

      @Override
      public void onFailure(Call<List<Contract>> call, Throwable throwable) {
        view.hideProgressDialog();
        view.onFailureMessage(throwable);
      }
    });
  }

}
