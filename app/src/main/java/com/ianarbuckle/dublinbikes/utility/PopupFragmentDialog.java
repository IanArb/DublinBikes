package com.ianarbuckle.dublinbikes.utility;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ianarbuckle.dublinbikes.R;

import java.util.Locale;


/**
 * Created by Ian Arbuckle on 10/12/2016.
 *
 */

public class PopupFragmentDialog extends DialogFragment {

  String name;
  String address;
  String status;
  int slots;
  int available;
  long update;

  public PopupFragmentDialog() {
  }

  public static PopupFragmentDialog newInstance(String name, String address, String status, int slots, int available, long update) {
    PopupFragmentDialog popupFragmentDialog = new PopupFragmentDialog();
    Bundle args = new Bundle();
    args.putString(Constants.NAME_KEY, name);
    args.putString(Constants.ADDRESS_KEY, address);
    args.putString(Constants.STATUS_KEY, status);
    args.putInt(Constants.SLOTS_KEY, slots);
    args.putInt(Constants.BIKES_KEY, available);
    args.putLong(Constants.UPDATE_KEY, update);
    popupFragmentDialog.setArguments(args);
    return popupFragmentDialog;
  }

  @Override
  public void onStart() {
    super.onStart();
    if(getDialog().getWindow() != null) {
      getDialog().getWindow().setWindowAnimations(R.style.DialogAnimation);
      getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
      getDialog().getWindow().setGravity(Gravity.BOTTOM);
    }
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.card_map, container, false);
    name = getArguments().getString(Constants.NAME_KEY);
    address = getArguments().getString(Constants.ADDRESS_KEY);
    status = getArguments().getString(Constants.STATUS_KEY);
    slots = getArguments().getInt(Constants.SLOTS_KEY);
    available = getArguments().getInt(Constants.BIKES_KEY);
    update = getArguments().getLong(Constants.UPDATE_KEY);

    View nameTv = view.findViewById(R.id.nameTv);
    ((TextView) nameTv).setText(name);
    View addTv = view.findViewById(R.id.addressTv);
    ((TextView) addTv).setText(address);
    View statusTv = view.findViewById(R.id.statusTv);
    ((TextView) statusTv).setText(status);
    View slotsTv = view.findViewById(R.id.slotsTv);
    String format = String.format(Locale.ENGLISH, "%d", slots);
    ((TextView) slotsTv).setText(format);
    View availTv = view.findViewById(R.id.bikesTv);
    String availFormat = String.format(Locale.ENGLISH, "%d", available);
    ((TextView) availTv).setText(availFormat);
    View updateTv = view.findViewById(R.id.updateTv);
    String formatUpdate = TextUtils.getDuration(update);
    ((TextView) updateTv).setText(formatUpdate);

    if(status.equals("OPEN")) {
      ((TextView) statusTv).setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
    } else {
      ((TextView) statusTv).setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
    }

    ImageButton closeButton = (ImageButton) view.findViewById(R.id.close);
    closeButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getDialog().dismiss();
      }
    });

    return view;


  }

}
