package com.ianarbuckle.dublinbikes.authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.ianarbuckle.dublinbikes.BaseActivity;
import com.ianarbuckle.dublinbikes.R;
import com.ianarbuckle.dublinbikes.utility.SmartFragmentStatePagerAdapter;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 15/12/2016.
 *
 */

public class AuthPagerActivity extends BaseActivity {

  public static Intent newIntent(Context context) {
    return new Intent(context, AuthPagerActivity.class);
  }

  @BindView(R.id.viewpager)
  ViewPager viewPager;

  @BindView(R.id.tabs)
  TabLayout tabLayout;

  @Override
  protected void initLayout() {
    setContentView(R.layout.layout_tabs);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initTabLayout();
    initPager();
  }

  private void initTabLayout() {
    tabLayout.addTab(tabLayout.newTab().setText("Login"));
    tabLayout.addTab(tabLayout.newTab().setText("Register"));
  }

  private void initPager() {
    final AuthPagerAdapter adapter = new AuthPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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

  private class AuthPagerAdapter extends SmartFragmentStatePagerAdapter {

    int numTabs;

    public AuthPagerAdapter(FragmentManager fragmentManager, int numTabs) {
      super(fragmentManager);
      this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
      switch(position) {
        case 0 :
          return LoginFragment.newInstance();
        case 1 :
          return RegisterFragment.newInstance();
        default :
          return null;
      }
    }

    @Override
    public int getCount() {
      return numTabs;
    }

  }

}
