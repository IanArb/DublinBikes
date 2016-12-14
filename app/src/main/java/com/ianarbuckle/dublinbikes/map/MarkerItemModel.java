package com.ianarbuckle.dublinbikes.map;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Ian Arbuckle on 08/12/2016.
 *
 */

public class MarkerItemModel implements ClusterItem {

  private final LatLng position;
  private final String name;
  private final String address;
  private final String status;
  private final int slots;
  private final int available;
  private final long update;

  MarkerItemModel(LatLng position, String name, String address, String status, int slots, int available,
                                long update) {
    this.position = position;
    this.name = name;
    this.address = address;
    this.status = status;
    this.slots = slots;
    this.available = available;
    this.update = update;
  }

  public String getName() {
    return name;
  }

  @Override
  public LatLng getPosition() {
    return position;
  }

  String getAddress() {
    return address;
  }

  String getStatus() {
    return status;
  }

  int getSlots() {
    return slots;
  }

  int getAvailable() {
    return available;
  }

  long getUpdate() {
    return update;
  }

//  public void setDistance(double distance) {
//    this.distance = distance;
//  }
}
