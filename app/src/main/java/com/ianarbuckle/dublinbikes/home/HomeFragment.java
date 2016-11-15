package com.ianarbuckle.dublinbikes.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.dublinbikes.BaseFragment;
import com.ianarbuckle.dublinbikes.R;

/**
 * Created by Ian Arbuckle on 15/11/2016.
 *
 */

public class HomeFragment extends BaseFragment {

  public static Fragment newInstance() {
    return new HomeFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.homefragment, container, false);
  }

  @Override
  protected void initPresenter() {

  }


}
