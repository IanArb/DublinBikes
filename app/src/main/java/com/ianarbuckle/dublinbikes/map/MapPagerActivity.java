package com.ianarbuckle.dublinbikes.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.ianarbuckle.dublinbikes.BaseActivity;
import com.ianarbuckle.dublinbikes.R;
import com.ianarbuckle.dublinbikes.stations.StationsFragment;
import com.ianarbuckle.dublinbikes.utiity.SmartFragmentStatePagerAdapter;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 15/11/2016.
 *
 */

public class MapPagerActivity extends BaseActivity {


  @BindView(R.id.tabs)
  TabLayout tabLayout;

  @BindView(R.id.viewpager)
  ViewPager viewPager;

  MapPagerAdapter mapPagerAdapter;

  public static Intent newIntent(Context context) {
    return new Intent(context, MapPagerActivity.class);
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.activity_nav_drawer);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initTabLayout();

    initPager();
  }

  private void initTabLayout() {
    tabLayout.addTab(tabLayout.newTab().setText(R.string.map_title));
    tabLayout.addTab(tabLayout.newTab().setText("Stations"));
//    tabLayout.addTab(tabLayout.newTab().setText(R.string.bookmarks_title));
  }

  @Override
  protected void onResume() {
    super.onResume();
    if(mapPagerAdapter != null) {
      mapPagerAdapter.notifyDataSetChanged();
    }
  }

  private void initPager() {
    final MapPagerAdapter adapter = new MapPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
    viewPager.setAdapter(adapter);
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

  private class MapPagerAdapter extends SmartFragmentStatePagerAdapter {
    int numTabs;

    MapPagerAdapter(FragmentManager fragmentManager, int numTabs) {
      super(fragmentManager);
      this.numTabs = numTabs;
    }

    @Override
    public int getCount() {
      return numTabs;
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0 :
          return MapFragment.newInstance();
        case 1 :
          return StationsFragment.newInstance();
        default:
          return null;
      }
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        if(drawerLayout != null) {
          drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

}
