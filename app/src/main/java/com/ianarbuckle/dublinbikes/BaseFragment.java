package com.ianarbuckle.dublinbikes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ian Arbuckle on 15/11/2016.
 *
 */

public abstract class BaseFragment extends Fragment {

  Unbinder unbinder;

  protected ProgressDialog progressDialog;

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ButterKnife.bind(this, view);

    butterKnifeUnbinder(view);

    initPresenter();
  }

  private void butterKnifeUnbinder(View view) {
    unbinder = ButterKnife.bind(this, view);
  }

  protected abstract void initPresenter();

  public static void switchFragment(FragmentManager fragmentManager, Fragment fragment, String tag, boolean addToBackStack) {
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.vgContentFrame, fragment);
    if(addToBackStack) {
      fragmentTransaction.addToBackStack(tag);
    }
    fragmentTransaction.commit();
  }

  public void showProgressDialog() {
    if(progressDialog == null) {
      progressDialog = new ProgressDialog(getContext());
      progressDialog.setMessage("Loading");
    }
    progressDialog.show();
  }

  public void hideProgressDialog() {
    if(progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  public boolean onBackPressed() {
    return false;
  }
}
