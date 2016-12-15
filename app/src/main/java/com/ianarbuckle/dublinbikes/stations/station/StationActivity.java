package com.ianarbuckle.dublinbikes.stations.station;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.ianarbuckle.dublinbikes.BaseActivity;
import com.ianarbuckle.dublinbikes.BaseFragment;
import com.ianarbuckle.dublinbikes.R;
import com.ianarbuckle.dublinbikes.utiity.Constants;
import com.ianarbuckle.dublinbikes.utiity.UiUtils;

/**
 * Created by Ian Arbuckle on 14/12/2016.
 *
 */

public class StationActivity extends BaseActivity {

  public static Intent newIntent(Context context) {
    return new Intent(context, StationActivity.class);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initFragment();
  }

  @Override
  protected void initToolbar() {
    super.initToolbar();
    String name = getIntent().getStringExtra(Constants.NAME_KEY);
    if (toolbar != null) {
      UiUtils.customiseToolbar(toolbar);
      UiUtils.backStyleActionBar(toolbar);
      toolbar.setTitle(name);
      setSupportActionBar(toolbar);
      if (getSupportActionBar() != null) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(UiUtils.backStyleActionBar(toolbar));
      }
    }
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.activity_container);
  }

  private void initFragment() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    if(fragmentManager.findFragmentByTag(Constants.TAG_STATION) != null) {
      return;
    }

    BaseFragment.switchFragment(getSupportFragmentManager(), StationFragment.newInstance(), Constants.TAG_STATION, false);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
