package com.ianarbuckle.dublinbikes.map;

import com.ianarbuckle.dublinbikes.models.Station;

import java.util.List;

/**
 * Created by Ian Arbuckle on 07/12/2016.
 *
 */

public interface MapPresenter {

  void fetchStations();

  void fetchContracts();

  void fetchStation();

  List<Station> onResponseStations();
}
