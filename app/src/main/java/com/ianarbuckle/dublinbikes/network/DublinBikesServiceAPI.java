package com.ianarbuckle.dublinbikes.network;

import com.ianarbuckle.dublinbikes.models.Contract;
import com.ianarbuckle.dublinbikes.models.Station;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ian Arbuckle on 24/11/2016.
 *
 */

public interface DublinBikesServiceAPI {

  //Get list of all contracts
  @GET("vls/v1/contracts?&apiKey=503534cf9adae09a12ca03bf3f845347ab1cbfa0")
  Call<List<Contract>> getContracts();

  //Get all stations
  @GET("vls/v1/stations?&apiKey==503534cf9adae09a12ca03bf3f845347ab1cbfa0")
  Call<List<Station>> getAllStations();

  //Get stations of a contract - i.e. get all Dublin bike stations
  @GET("vls/v1/stations?contract=dublin&apiKey=503534cf9adae09a12ca03bf3f845347ab1cbfa0")
  Call<List<Station>> getDublinStations();

  //Get a specific station by number
  @GET("/stations/{station_number}?contract=dublin&apiKey=503534cf9adae09a12ca03bf3f845347ab1cbfa0")
  Call<List<Station>> getStationInformation(@Path("station_number") String stationNumber);

}
