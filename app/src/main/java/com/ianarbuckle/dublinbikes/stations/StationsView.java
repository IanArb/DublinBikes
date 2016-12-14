package com.ianarbuckle.dublinbikes.stations;

import android.content.Context;

/**
 * Created by Ian Arbuckle on 12/12/2016.
 *
 */

public interface StationsView {

  interface ViewHolder {
    void setText(String text);
  }

  Context getContext();

  void showProgressDialog();

  void hideProgressDialog();

  void onFailureMessage(Throwable throwable);

}
