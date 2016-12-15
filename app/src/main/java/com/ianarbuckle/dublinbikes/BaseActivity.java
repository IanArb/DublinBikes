package com.ianarbuckle.dublinbikes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ianarbuckle.dublinbikes.authentication.AuthPagerActivity;
import com.ianarbuckle.dublinbikes.map.MapFragment;
import com.ianarbuckle.dublinbikes.utiity.CircleTransform;
import com.ianarbuckle.dublinbikes.utiity.Constants;
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

  GoogleApiClient googleApiClient;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initLayout();

    ButterKnife.bind(this);

    butterKnifeUnBinder();

    initToolbar();

    initNavView();
  }

  private void initNavView() {
    if(navigationView != null) {
      navigationView.setNavigationItemSelectedListener(this);
      View headerView = navigationView.getHeaderView(0);
      TextView userTv = (TextView) headerView.findViewById(R.id.name);
      TextView emailTv = (TextView) headerView.findViewById(R.id.email);


      String username = getIntent().getExtras().getString(Constants.USERNAME_KEY);
      String photo = getIntent().getExtras().getString(Constants.PHOTO_KEY);
      String email = getIntent().getExtras().getString(Constants.EMAIL_KEY);

      userTv.setText(username);
      emailTv.setText(email);

      ImageView imageView = (ImageView) headerView.findViewById(R.id.img_profile);

      Glide.with(getApplicationContext()).load(photo)
          .crossFade()
          .thumbnail(0.5f)
          .bitmapTransform(new CircleTransform(getApplicationContext()))
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .into(imageView);

      ImageView bgImage = (ImageView) headerView.findViewById(R.id.img_header_bg);

      Glide.with(getApplicationContext()).load(Constants.HEADER_URL)
          .crossFade()
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .into(bgImage);

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
        MapFragment.newInstance();
        break;
      case R.id.nav_invite:
        sendShareIntent();
        break;
      case R.id.nav_signout:
        startActivity(AuthPagerActivity.newIntent(getApplicationContext()));
        break;
    }

    if(drawerLayout != null) {
      drawerLayout.closeDrawer(GravityCompat.START);
    }

    if (item.isChecked()) {
      item.setChecked(false);
    } else {
      item.setChecked(true);
    }
    item.setChecked(true);

    return true;
  }

  private void sendShareIntent() {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_TEXT, "Check out Dublin Bikes Android app!");
    startActivity(intent);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    if(drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
        drawerLayout.closeDrawers();
    }
  }
}
