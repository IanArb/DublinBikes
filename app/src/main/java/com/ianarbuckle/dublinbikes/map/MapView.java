package com.ianarbuckle.dublinbikes.map;

import android.content.Context;

import com.ianarbuckle.dublinbikes.models.Contract;

import java.util.List;

import retrofit2.Response;

/**
 * Created by Ian Arbuckle on 07/12/2016.
 *
 */

public interface MapView {

  Context getContext();

  void showContractResponse(Response<List<Contract>> response);

  void onFailureMessage(Throwable throwable);

  void showProgressDialog();

  void hideProgressDialog();
}
