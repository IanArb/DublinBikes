package com.ianarbuckle.dublinbikes.authentication.firebase;

/**
 * Created by Ian Arbuckle on 01/12/2016.
 * Reference - https://github.com/filbabic/FirebaseChatApp
 */

public interface RequestListener {

  void onSucessRequest();

  void onFailureRequest();
}
