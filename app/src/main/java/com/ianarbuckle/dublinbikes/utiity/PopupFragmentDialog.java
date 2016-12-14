package com.ianarbuckle.dublinbikes.utiity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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


  private static final String NAME_KEY = "name";
  private static final String ADDRESS_KEY = "address";
  private static final String STATUS_KEY = "status";
  private static final String SLOTS_KEY = "slots";
  private static final String AVAIL_KEY = "available";
  private static final String UPDATE_KEY = "update";

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
    args.putString(NAME_KEY, name);
    args.putString(ADDRESS_KEY, address);
    args.putString(STATUS_KEY, status);
    args.putInt(SLOTS_KEY, slots);
    args.putInt(AVAIL_KEY, available);
    args.putLong(UPDATE_KEY, update);
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
    name = getArguments().getString(NAME_KEY);
    address = getArguments().getString(ADDRESS_KEY);
    status = getArguments().getString(STATUS_KEY);
    slots = getArguments().getInt(SLOTS_KEY);
    available = getArguments().getInt(AVAIL_KEY);
    update = getArguments().getLong(UPDATE_KEY);

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
