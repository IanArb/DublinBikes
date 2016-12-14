package com.ianarbuckle.dublinbikes.stations;

import com.ianarbuckle.dublinbikes.models.Station;
import com.ianarbuckle.dublinbikes.network.DublinBikesCaller;
import com.ianarbuckle.dublinbikes.network.DublinBikesServiceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ian Arbuckle on 12/12/2016.
 *
 */

public class StationsPresenterImpl implements StationsPresenter {

  private StationsView view;

  DublinBikesServiceAPI serviceAPI;

  private List<Station> stationList;

  public StationsPresenterImpl(StationsView view) {
    this.view = view;
  }

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
        stationList = new ArrayList<Station>();
        stationList = response.body();
      }

      @Override
      public void onFailure(Call<List<Station>> call, Throwable throwable) {

      }
    });
  }

  @Override
  public List<Station> onResponseStations() {
    return stationList;
  }
}
