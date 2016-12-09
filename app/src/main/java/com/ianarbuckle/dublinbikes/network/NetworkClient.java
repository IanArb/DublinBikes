package com.ianarbuckle.dublinbikes.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ian Arbuckle on 24/11/2016.
 *
 */

public class NetworkClient {


//  private static final String BASE_URL = "https://api.jcdecaux.com/vls/v1/";
//  private static final String API_KEY = "503534cf9adae09a12ca03bf3f845347ab1cbfa0";

  public boolean checkForConnection(Context context) {
    if(!isConnectedOrConnecting(context)) {
      return false;
    }
    return true;
  }

  public static boolean isConnectedOrConnecting(Context context) {
    NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(
        Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    return networkInfo != null && networkInfo.isConnectedOrConnecting();
  }


}
