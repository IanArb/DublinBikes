package com.ianarbuckle.dublinbikes.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.ianarbuckle.dublinbikes.BaseActivity;
import com.ianarbuckle.dublinbikes.BaseFragment;
import com.ianarbuckle.dublinbikes.R;

/**
 * Created by Ian Arbuckle on 15/11/2016.
 *
 */

public class HomeActivity extends BaseActivity {

  public static final String TAG_HOME_FRAGMENT = "homeFragment";

  public static Intent newIntent(Context context) {
    return new Intent(context, HomeActivity.class);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initFragment();
  }

  private void initFragment() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    if(fragmentManager.findFragmentByTag(TAG_HOME_FRAGMENT) != null) {
      return;
    }

    BaseFragment.switchFragment(getSupportFragmentManager(), HomeFragment.newInstance(), TAG_HOME_FRAGMENT, false);
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.activity_container);
  }

}
