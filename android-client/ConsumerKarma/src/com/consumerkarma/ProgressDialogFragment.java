package com.consumerkarma;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;

public class ProgressDialogFragment extends DialogFragment {
    private boolean mIsCancelable = false;
    private OnCancelListener mCancelListener;
    private String mMessage;
    
    /**
     * Sets whether or not the dialog is cancelable or not.
     */
    public void setCancelable(boolean val) {
        mIsCancelable = val;
    }
    
    /**
     * Sets the cancel listener to this dialog
     * @param listener
     */
    public void setOnCancelListener(OnCancelListener listener) {
        mCancelListener = listener;
    }
    
    /**
     * Sets the message of this progress dialog
     * @param message
     */
    public void setMessage(String message) {
        mMessage = message;
    }
    
    @Override
    public void show(FragmentManager manager, String tag) {
        if (manager.findFragmentByTag(tag) == null) {
            super.show(manager, tag);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage(mMessage);
        dialog.setIndeterminate(true);
        dialog.setCancelable(mIsCancelable);
        return dialog;
    }

}
