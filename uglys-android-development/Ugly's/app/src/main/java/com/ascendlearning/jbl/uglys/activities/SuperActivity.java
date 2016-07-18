package com.ascendlearning.jbl.uglys.activities;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.ascendlearning.jbl.uglys.UglysApplication;
import com.ascendlearning.jbl.uglys.utils.AppLog;

/**
 * Created by sonal.agarwal on 11/22/2015.
 */
public class SuperActivity extends AppCompatActivity {
    protected Toast mToast;
    protected int mToastDuration;
    protected CharSequence mToastMessage;
    protected ProgressDialog mProgressBar = null;
    private String mProgressMessage;
    private String mDialogMessage;
    private AlertDialog mAlert;
    UglysApplication application;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (UglysApplication) getApplication();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    protected void showToast(String msg, int duration) {
        if (AppLog.isEnabled()) {
            mToastMessage = msg;
            mToastDuration = duration;
            runOnUiThread(mShowToast);
        }
    }

    private Runnable mShowToast = new Runnable() {

        @Override
        public void run() {
            if (mToast == null) {
                mToast = Toast.makeText(SuperActivity.this, "", mToastDuration);
            }
            mToast.setDuration(mToastDuration);
            mToast.setText(mToastMessage);
            mToast.show();
        }
    };

    public void showProgressBarNonCancellable(String progressMessage) {
        mProgressMessage = progressMessage;
        SuperActivity.this.runOnUiThread(mShowProgressNonCancellableRunnable);
    }

    public void showProgressBarCancellable(String progressMessage) {
        mProgressMessage = progressMessage;
        SuperActivity.this.runOnUiThread(mShowProgressCancellableRunnable);
    }

    public void hideProgressBar() {
        if (mHideProgressRunnable != null) {
            SuperActivity.this.runOnUiThread(mHideProgressRunnable);
        }
    }

    protected Runnable mShowProgressNonCancellableRunnable = new Runnable() {

        @Override
        public void run() {
            show(false);
        }
    };

    protected Runnable mShowProgressCancellableRunnable = new Runnable() {

        @Override
        public void run() {
            show(true);
        }
    };

    private Runnable mHideProgressRunnable = new Runnable() {

        @Override
        public void run() {
            hide();
        }
    };

    private void show(boolean cancellable) {

        hide();
        mProgressBar = new ProgressDialog(SuperActivity.this);
        mProgressBar.setCancelable(cancellable);
        mProgressBar.setTitle(mProgressMessage);
        //mProgressBar.setMessage("Loading...");
        mProgressBar.show();

    }

    private void hide() {
        if (mProgressBar != null) {
            mProgressBar.dismiss();
            mProgressBar = null;
        }
    }


    private Runnable mErrorRunnable = new Runnable() {

        @Override
        public void run() {
            showDialog();
        }
    };

    private void showDialog() {
        if (mAlert != null) {
            mAlert.dismiss();
            mAlert = null;
        }
        mAlert = getAlertDialog(this, "Error", mDialogMessage, "OK", null, null, null, null, null);
        try {
            mAlert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected AlertDialog getAlertDialog(Context context, String title, String message, String posMessage, DialogInterface.OnClickListener posListener, String negMessage,
                                         DialogInterface.OnClickListener negListener, DialogInterface.OnCancelListener cancelListener, View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //alertDialog.setTitle(title);

        if (!TextUtils.isEmpty(message)) {
            alertDialog.setMessage(message);
        }

        if (posListener != null || !TextUtils.isEmpty(posMessage)) {
            alertDialog.setPositiveButton(posMessage, posListener);
        }

        if (negListener != null || !TextUtils.isEmpty(negMessage)) {
            alertDialog.setNegativeButton(negMessage, negListener);
        }

        if (cancelListener != null) {
            alertDialog.setOnCancelListener(cancelListener);
        }

        if (view != null) {
            alertDialog.setView(view);
        }

        AlertDialog alert = alertDialog.create();

        context = null;

        return alert;

    }

    protected void showErrorDialog(String message) {
        mDialogMessage = message;
        runOnUiThread(mErrorRunnable);
    }

}