package com.ianarbuckle.dublinbikes.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ian Arbuckle on 12/12/2016.
 *
 */

public class DublinBikesCaller {

  private static final String BASE_URL = "https://api.jcdecaux.com/";

  public static DublinBikesServiceAPI getDublinBikesServiceAPI() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    return retrofit.create(DublinBikesServiceAPI.class);
  }

}
