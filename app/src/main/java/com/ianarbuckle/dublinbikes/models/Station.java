package com.ianarbuckle.dublinbikes.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Station {

  @SerializedName("number")
  @Expose
  private int number;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("address")
  @Expose
  private String address;
  @SerializedName("position")
  @Expose
  private Position position;
  @SerializedName("banking")
  @Expose
  private boolean banking;
  @SerializedName("bonus")
  @Expose
  private boolean bonus;
  @SerializedName("status")
  @Expose
  private String status;
  @SerializedName("contract_name")
  @Expose
  private String contractName;
  @SerializedName("bike_stands")
  @Expose
  private int bikeStands;
  @SerializedName("available_bike_stands")
  @Expose
  private int availableBikeStands;
  @SerializedName("available_bikes")
  @Expose
  private int availableBikes;
  @SerializedName("last_update")
  @Expose
  private int lastUpdate;

  /**
   * No args constructor for use in serialization
   *
   */
  public Station() {
  }

  /**
   *
   * @param position
   * @param bikeStands
   * @param status
   * @param address
   * @param lastUpdate
   * @param name
   * @param availableBikeStands
   * @param banking
   * @param bonus
   * @param number
   * @param contractName
   * @param availableBikes
   */
  public Station(int number, String name, String address, Position position, boolean banking, boolean bonus, String status, String contractName, int bikeStands, int availableBikeStands, int availableBikes, int lastUpdate) {
    this.number = number;
    this.name = name;
    this.address = address;
    this.position = position;
    this.banking = banking;
    this.bonus = bonus;
    this.status = status;
    this.contractName = contractName;
    this.bikeStands = bikeStands;
    this.availableBikeStands = availableBikeStands;
    this.availableBikes = availableBikes;
    this.lastUpdate = lastUpdate;
  }

  /**
   *
   * @return
   * The number
   */
  public int getNumber() {
    return number;
  }

  /**
   *
   * @param number
   * The number
   */
  public void setNumber(int number) {
    this.number = number;
  }

  /**
   *
   * @return
   * The name
   */
  public String getName() {
    return name;
  }

  /**
   *
   * @param name
   * The name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   *
   * @return
   * The address
   */
  public String getAddress() {
    return address;
  }

  /**
   *
   * @param address
   * The address
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   *
   * @return
   * The position
   */
  public Position getPosition() {
    return position;
  }

  /**
   *
   * @param position
   * The position
   */
  public void setPosition(Position position) {
    this.position = position;
  }

  /**
   *
   * @return
   * The banking
   */
  public boolean isBanking() {
    return banking;
  }

  /**
   *
   * @param banking
   * The banking
   */
  public void setBanking(boolean banking) {
    this.banking = banking;
  }

  /**
   *
   * @return
   * The bonus
   */
  public boolean isBonus() {
    return bonus;
  }

  /**
   *
   * @param bonus
   * The bonus
   */
  public void setBonus(boolean bonus) {
    this.bonus = bonus;
  }

  /**
   *
   * @return
   * The status
   */
  public String getStatus() {
    return status;
  }

  /**
   *
   * @param status
   * The status
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   *
   * @return
   * The contractName
   */
  public String getContractName() {
    return contractName;
  }

  /**
   *
   * @param contractName
   * The contract_name
   */
  public void setContractName(String contractName) {
    this.contractName = contractName;
  }

  /**
   *
   * @return
   * The bikeStands
   */
  public int getBikeStands() {
    return bikeStands;
  }

  /**
   *
   * @param bikeStands
   * The bike_stands
   */
  public void setBikeStands(int bikeStands) {
    this.bikeStands = bikeStands;
  }

  /**
   *
   * @return
   * The availableBikeStands
   */
  public int getAvailableBikeStands() {
    return availableBikeStands;
  }

  /**
   *
   * @param availableBikeStands
   * The available_bike_stands
   */
  public void setAvailableBikeStands(int availableBikeStands) {
    this.availableBikeStands = availableBikeStands;
  }

  /**
   *
   * @return
   * The availableBikes
   */
  public int getAvailableBikes() {
    return availableBikes;
  }

  /**
   *
   * @param availableBikes
   * The available_bikes
   */
  public void setAvailableBikes(int availableBikes) {
    this.availableBikes = availableBikes;
  }

  /**
   *
   * @return
   * The lastUpdate
   */
  public int getLastUpdate() {
    return lastUpdate;
  }

  /**
   *
   * @param lastUpdate
   * The last_update
   */
  public void setLastUpdate(int lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

}

