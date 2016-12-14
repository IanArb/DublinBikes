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

}
