package com.ianarbuckle.dublinbikes.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Position {

  @SerializedName("lat")
  @Expose
  private double lat;
  @SerializedName("lng")
  @Expose
  private double lng;

  /**
   * No args constructor for use in serialization
   *
   */
  public Position() {
  }

  /**
   *
   * @param lng
   * @param lat
   */
  public Position(double lat, double lng) {
    this.lat = lat;
    this.lng = lng;
  }

  /**
   *
   * @return
   * The lat
   */
  public double getLat() {
    return lat;
  }

  /**
   *
   * @param lat
   * The lat
   */
  public void setLat(double lat) {
    this.lat = lat;
  }

  /**
   *
   * @return
   * The lng
   */
  public double getLng() {
    return lng;
  }

  /**
   *
   * @param lng
   * The lng
   */
  public void setLng(double lng) {
    this.lng = lng;
  }

}