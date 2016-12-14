package com.ianarbuckle.dublinbikes.stations;

import com.ianarbuckle.dublinbikes.models.Station;

import java.util.List;

/**
 * Created by Ian Arbuckle on 12/12/2016.
 *
 */

public interface StationsPresenter {

  void fetchStations();

  List<Station> onResponseStations();

}
