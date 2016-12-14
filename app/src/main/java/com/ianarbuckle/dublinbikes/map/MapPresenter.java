package com.ianarbuckle.dublinbikes.map;

import com.ianarbuckle.dublinbikes.models.Station;

import java.util.List;

/**
 * Created by Ian Arbuckle on 07/12/2016.
 *
 */

public interface MapPresenter {

  void fetchStations();

  MarkerItemModel getMarkerModelItems(List<Station> stationList, int size);

  List<Station> onResponseStations();

}
