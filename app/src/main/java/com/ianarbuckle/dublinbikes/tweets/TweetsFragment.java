package com.ianarbuckle.dublinbikes.tweets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ianarbuckle.dublinbikes.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 23/11/2016.
 *
 */

public class TweetsFragment extends ListFragment {

  @BindView(R.id.swipe_layout)
  SwipeRefreshLayout swipeLayout;

  public static Fragment newInstance() {
    return new TweetsFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_tweets, container, false);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    final SearchTimeline searchTimeline = new SearchTimeline.Builder()
        .query("bike")
        .build();
    final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity())
        .setTimeline(searchTimeline)
        .build();

    setListAdapter(adapter);

    initRefresh(adapter);

  }

  private void initRefresh(final TweetTimelineListAdapter adapter) {
    if(swipeLayout == null) {
      return;
    }
    swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        swipeLayout.setRefreshing(true);
        adapter.refresh(new Callback<TimelineResult<Tweet>>() {
          @Override
          public void success(Result<TimelineResult<Tweet>> result) {
            swipeLayout.setRefreshing(false);
          }

          @Override
          public void failure(TwitterException exception) {
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
          }
        });
      }
    });
  }

}
