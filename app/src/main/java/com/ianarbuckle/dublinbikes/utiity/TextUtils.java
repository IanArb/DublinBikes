package com.ianarbuckle.dublinbikes.utiity;

/**
 * Created by Ian Arbuckle on 11/12/2016.
 *
 */

class TextUtils {

  static String getMinutesString(int seconds) {

    int mins = (seconds % 3600) / 60;

    return String.valueOf(mins);
  }

}
