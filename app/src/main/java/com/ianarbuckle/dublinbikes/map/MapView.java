package com.ianarbuckle.dublinbikes.map;

import android.content.Context;

/**
 * Created by Ian Arbuckle on 07/12/2016.
 *
 */

interface MapView {

  Context getContext();

  void onFailureMessage(Throwable throwable);

  void showProgressDialog();

  void hideProgressDialog();
}
