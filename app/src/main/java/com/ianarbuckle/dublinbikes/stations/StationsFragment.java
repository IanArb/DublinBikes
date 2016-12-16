package com.ianarbuckle.dublinbikes.stations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.dublinbikes.BaseFragment;
import com.ianarbuckle.dublinbikes.R;
import com.ianarbuckle.dublinbikes.models.Station;
import com.ianarbuckle.dublinbikes.network.DublinBikesCaller;
import com.ianarbuckle.dublinbikes.network.DublinBikesServiceAPI;
import com.ianarbuckle.dublinbikes.stations.station.StationActivity;
import com.ianarbuckle.dublinbikes.utiity.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Ian Arbuckle on 12/12/2016.
 *
 */

public class StationsFragment extends BaseFragment {

  RecyclerView.Adapter adapter;
  LinearLayoutManager layoutManager;

  DublinBikesServiceAPI serviceAPI;
  private List<Station> data;

  private RecyclerView recyclerView;

  public static StationsFragment newInstance() {
    return new StationsFragment();
  }

  @Override
  protected void initPresenter() {
  }

  @Nullable
  @Override
  public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_stations, container, false);
    recyclerView = (RecyclerView) view.findViewById(R.id.stationsRv);
    recyclerView.setHasFixedSize(true);
    layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        Intent intent = StationActivity.newIntent(getContext());
        String name = data.get(position).getName();
        int stands = data.get(position).getBikeStands();
        int bikes = data.get(position).getAvailableBikes();
        int slots = data.get(position).getAvailableBikeStands();
        String status = data.get(position).getStatus();
        long update = data.get(position).getLastUpdate();
        double lat = data.get(position).getPosition().getLat();
        double lng = data.get(position).getPosition().getLng();
        intent.putExtra(Constants.NAME_KEY, name);
        intent.putExtra(Constants.STANDS_KEY, stands);
        intent.putExtra(Constants.BIKES_KEY, bikes);
        intent.putExtra(Constants.SLOTS_KEY, slots);
        intent.putExtra(Constants.STATUS_KEY, status);
        intent.putExtra(Constants.UPDATE_KEY, update);
        intent.putExtra(Constants.LAT_KEY, lat);
        intent.putExtra(Constants.LNG_KEY, lng);
        startActivity(intent);
      }

      @Override
      public void onLongItemClick(View view, int position) {

      }
    }));
    loadJson();
    return view;
  }

  private void loadJson() {
    serviceAPI = DublinBikesCaller.getDublinBikesServiceAPI();

    Call<List<Station>> call = serviceAPI.getDublinStations();

    getResponse(call);
  }

  private void getResponse(Call<List<Station>> call) {
    call.enqueue(new Callback<List<Station>>() {
      @Override
      public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
        //TODO Pass list as parceable
        data = new ArrayList<>();
        data = response.body();
        adapter = new StationsAdapter(data, getContext());
        recyclerView.setAdapter(adapter);
      }

      @Override
      public void onFailure(Call<List<Station>> call, Throwable throwable) {
      }
    });
  }

}
