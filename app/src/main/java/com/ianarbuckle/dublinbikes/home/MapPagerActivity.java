package com.ianarbuckle.dublinbikes.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.ianarbuckle.dublinbikes.BaseActivity;
import com.ianarbuckle.dublinbikes.R;
import com.ianarbuckle.dublinbikes.bookmarks.BookmarksFragment;
import com.ianarbuckle.dublinbikes.map.MapFragment;
import com.ianarbuckle.dublinbikes.tweets.TweetsFragment;

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
    tabLayout.addTab(tabLayout.newTab().setText(R.string.bookmarks_title));
    tabLayout.addTab(tabLayout.newTab().setText(R.string.tweets_title));
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

  private class MapPagerAdapter extends FragmentStatePagerAdapter {
    int numTabs;

    MapPagerAdapter(FragmentManager fragmentManager, int numTabs) {
      super(fragmentManager);
      this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0 :
          return MapFragment.newInstance();
        case 1 :
          return BookmarksFragment.newInstance();
        case 2:
          return TweetsFragment.newInstance();
        default:
          return null;
      }
    }

    @Override
    public int getCount() {
      return numTabs;
    }
  }

}
