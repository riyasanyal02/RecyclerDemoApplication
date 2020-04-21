package com.example.altimetrikproject.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.altimetrikproject.R;
import com.example.altimetrikproject.common.Utils;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initProgressDialog();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.msg_loading));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * Show session expired alert
     *
     * @param dialogListener
     */
    public void showNoNetworkAlert(AlertDialog.AlertDialogListener dialogListener) {
        AlertDialog.showNonCancellebleAlertDialog(getSupportFragmentManager(),
                getResources().getString(R.string.DIALOG_ALERT), getString(R.string.dialog_title_network_error),
                getString(R.string.dialog_msg_no_network),
                AlertDialog.DialogType.TYPE_RETRY, dialogListener);
    }

    public void setProgressDialog(boolean state) {
        setProgressDialogVisibility(state);
    }


    public void setProgressDialogVisibility(boolean state) {
        try {
            if (state) {
                progressDialog.setMessage(getString(R.string.msg_loading));
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showRetryDialog(AlertDialog.AlertDialogListener alertDialogListener) {
        showRetryDialog(getString(R.string.dialog_msg_something_went_wrong), alertDialogListener);
    }

    public void showRetryDialog(final String message, final AlertDialog.AlertDialogListener alertDialogListener) {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(AlertDialog.EXTRA_TYPE, AlertDialog.DialogType.TYPE_YES_NO);
        bundle.putString(AlertDialog.EXTRA_MESSAGE, message);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.showAlertDialog(getSupportFragmentManager(), getResources().getString(R.string.DIALOG_CONFIRM), bundle,
                        new AlertDialog.AlertDialogListener() {
                            @Override
                            public void onPositiveButtonClicked() {
                                alertDialogListener.onPositiveButtonClicked();
                            }

                            @Override
                            public void onNegativeButtonClicked() {
                                alertDialogListener.onNegativeButtonClicked();
                            }
                        });
            }
        }, 300);
    }
}
