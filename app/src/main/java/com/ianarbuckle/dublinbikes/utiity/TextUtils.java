package com.ianarbuckle.dublinbikes.utiity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ian Arbuckle on 11/12/2016.
 *
 */

public class TextUtils {

  public static String getDuration(long millis) {

    return new SimpleDateFormat("HH 'hours' mm 'mins ago'", Locale.ENGLISH).format(new Date(millis));
  }

  public static String formatNumber(double distance) {
    String unit = " m";
    if (distance < 1) {
      distance *= 1000;
      unit = " mm";
    } else if (distance > 1000) {
      distance /= 1000;
      unit = " km";
    }

    return String.format(Locale.ENGLISH, "%4.3f%s", distance, unit);
  }

}
