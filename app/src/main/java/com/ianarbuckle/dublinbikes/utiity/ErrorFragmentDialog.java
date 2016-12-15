package com.ianarbuckle.dublinbikes.utiity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ianarbuckle.dublinbikes.R;

/**
 * Created by Ian Arbuckle on 11/12/2016.
 *
 */

public class ErrorFragmentDialog extends DialogFragment {

  private static final String MESSAGE_KEY = "message";

  String message;

  public ErrorFragmentDialog() {

  }

  public static ErrorFragmentDialog newInstance(int message) {
    ErrorFragmentDialog errorDialogFragment = new ErrorFragmentDialog();
    Bundle args = new Bundle();
    args.putInt(MESSAGE_KEY, message);
    errorDialogFragment.setArguments(args);
    return errorDialogFragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_error_dialog, container, false);
    message = getArguments().getString(MESSAGE_KEY);

    View textView = view.findViewById(R.id.errorMessage);
    ((TextView) textView).setText(message);

    Button button = (Button) view.findViewById(R.id.errorButton);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getDialog().dismiss();
      }
    });

    return view;
  }
}
