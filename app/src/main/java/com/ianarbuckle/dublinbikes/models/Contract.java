package com.ianarbuckle.dublinbikes.models;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Contract {

  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("cities")
  @Expose
  private List<String> cities = new ArrayList<String>();
  @SerializedName("commercial_name")
  @Expose
  private String commercialName;
  @SerializedName("country_code")
  @Expose
  private String countryCode;

  /**
   * No args constructor for use in serialization
   */
  public Contract() {
  }

  /**
   * @param cities
   * @param name
   * @param countryCode
   * @param commercialName
   */
  public Contract(String name, List<String> cities, String commercialName, String countryCode) {
    this.name = name;
    this.cities = cities;
    this.commercialName = commercialName;
    this.countryCode = countryCode;
  }

  /**
   * @return The name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name The name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return The cities
   */
  public List<String> getCities() {
    return cities;
  }

  /**
   * @param cities The cities
   */
  public void setCities(List<String> cities) {
    this.cities = cities;
  }

  /**
   * @return The commercialName
   */
  public String getCommercialName() {
    return commercialName;
  }

  /**
   * @param commercialName The commercial_name
   */
  public void setCommercialName(String commercialName) {
    this.commercialName = commercialName;
  }

  /**
   * @return The countryCode
   */
  public String getCountryCode() {
    return countryCode;
  }

  /**
   * @param countryCode The country_code
   */
  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

}
