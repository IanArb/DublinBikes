package com.ianarbuckle.dublinbikes.map;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Ian Arbuckle on 08/12/2016.
 *
 */

public class MarkerItem implements ClusterItem {

  private final LatLng position;

  public MarkerItem(double lat, double lng) {
    position = new LatLng(lat, lng);
  }

  @Override
  public LatLng getPosition() {
    return position;
  }
}
