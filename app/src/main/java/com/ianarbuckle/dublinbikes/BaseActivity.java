package com.ianarbuckle.dublinbikes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ianarbuckle.dublinbikes.map.MapPagerActivity;
import com.ianarbuckle.dublinbikes.utiity.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ian Arbuckle on 15/11/2016.
 *
 */

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

  @Nullable
  @BindView(R.id.toolbar)
  protected Toolbar toolbar;

  @Nullable
  @BindView(R.id.nav_view)
  NavigationView navigationView;

  @Nullable
  @BindView(R.id.drawer_layout)
  protected DrawerLayout drawerLayout;

  Unbinder unbinder;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initLayout();

    ButterKnife.bind(this);

    butterKnifeUnBinder();

    initToolbar();

    if(navigationView != null) {
      navigationView.setNavigationItemSelectedListener(this);
    }
  }

  protected abstract void initLayout();

  private void butterKnifeUnBinder() {
    unbinder = ButterKnife.bind(this);
  }

  protected void initToolbar() {
    if (toolbar != null) {
      UiUtils.customiseToolbar(toolbar);
      UiUtils.colourAndStyleActionBar(toolbar);
      setSupportActionBar(toolbar);
      if (getSupportActionBar() != null) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(UiUtils.colourAndStyleActionBar(toolbar));
      }
    }
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    int itemId = item.getItemId();

    switch(itemId) {
      case R.id.nav_home:
        startActivity(MapPagerActivity.newIntent(this));
        break;
      case R.id.nav_profile:
        break;
      case R.id.nav_signout:
        finish();
        break;
      case R.id.nav_invite:
        break;
    }

    if(drawerLayout != null) {
      drawerLayout.closeDrawer(GravityCompat.START);
    }

    return true;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

}
