package com.ianarbuckle.dublinbikes.map;

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

public class MapPresenterImpl implements MapPresenter {

  private static final String BASE_URL = "https://api.jcdecaux.com/";

  private MapView view;

  private DublinBikesServiceAPI serviceAPI;

  private List<Station> stationList = new ArrayList<>();

  public MapPresenterImpl(MapView view) {
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
  public void fetchStations() {
    if(NetworkClient.isConnectedOrConnecting(view.getContext())) {
      serviceAPI = getDublinBikesServiceAPI();

      view.showProgressDialog();

      Call<List<Station>> call = serviceAPI.getDublinStations();

      getAllStationsResponse(call);
    }
  }

  @Override
  public void fetchStation() {
    if(NetworkClient.isConnectedOrConnecting(view.getContext())) {
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
        view.showContractResponse(response);
      }

      @Override
      public void onFailure(Call<List<Contract>> call, Throwable throwable) {
        view.hideProgressDialog();
        view.onFailureMessage(throwable);
      }
    });
  }

}
