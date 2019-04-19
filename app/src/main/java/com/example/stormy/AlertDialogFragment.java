package com.example.stormy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

// Author Ryan Dsouza 
// Contact - authenticryanis@gmail.com  

public class AlertDialogFragment extends DialogFragment {

    private String errorTitle;
    private String errorMessage;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Context context = getActivity();

//      Get activity gives us the context through which this class was called
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(errorTitle)
                .setMessage(errorMessage)
                .setPositiveButton(getString(R.string.error_ok),null);

        return builder.create();
    }

    public void setErrorTitle(String errorTitle) {
        this.errorTitle = errorTitle;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
