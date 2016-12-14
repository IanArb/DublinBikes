package com.ianarbuckle.dublinbikes.stations;

/**
 * Created by Ian Arbuckle on 14/12/2016.
 * Ref - http://stackoverflow.com/questions/24471109/recyclerview-onclick
 */

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
  private OnItemClickListener mListener;

  public interface OnItemClickListener {
    void onItemClick(View view, int position);

    void onLongItemClick(View view, int position);
  }

  GestureDetector mGestureDetector;

  public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
    mListener = listener;
    mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
      @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
      @Override
      public boolean onSingleTapUp(MotionEvent event) {
        return true;
      }

      @Override
      public void onLongPress(MotionEvent event) {
        View child = recyclerView.findChildViewUnder(event.getX(), event.getY());
        if (child != null && mListener != null) {
          mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
        }
      }
    });
  }

  @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent event) {
    View childView = view.findChildViewUnder(event.getX(), event.getY());
    if (childView != null && mListener != null && mGestureDetector.onTouchEvent(event)) {
      mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
      return true;
    }
    return false;
  }

  @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {

  }

  @Override
  public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
}