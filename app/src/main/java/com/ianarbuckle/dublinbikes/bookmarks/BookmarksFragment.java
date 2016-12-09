package com.ianarbuckle.dublinbikes.bookmarks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.dublinbikes.BaseFragment;
import com.ianarbuckle.dublinbikes.R;

/**
 * Created by Ian Arbuckle on 23/11/2016.
 *
 */

public class BookmarksFragment extends BaseFragment {

  public static Fragment newInstance() {
    return new BookmarksFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_bookmarks, container, false);
  }

  @Override
  protected void initPresenter() {

  }
}
