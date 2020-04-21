/**
 * AlertDialog.java
 * <p>
 * A common alert dialog for the application.
 *
 * @category Global Analytics
 * @package com.globalanalytics.oyeloanindia.ui.dialog
 * @version 1.0
 * @author Rajkumar.N
 * @copyright Copyright (C) 2016 Global Analytics. All rights reserved.
 */
package com.example.altimetrikproject.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.altimetrikproject.R;

import static android.nfc.NfcAdapter.EXTRA_ID;

/**
 * A common alert dialog for the application.
 */
public class AlertDialog extends DialogFragment {
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";
    public static final String EXTRA_HINT = "hint";
    public static final String EXTRA_POSITIVE_BUTTON = "positive_button";
    public static final String EXTRA_NEGATIVE_BUTTON = "negative_button";
    private static final String TAG = AlertDialog.class.getSimpleName();
    private AlertDialogListener alertDialogListener;


    private String title;
    private String message;

    /**
     * New instance that create the alert dialog fragment.
     *
     * @param arguments refers the dialog fragment bundle.
     * @return the alert dialog fragment
     */
    public static AlertDialog newInstance(Bundle arguments) {
        AlertDialog alertDialogFragment = new AlertDialog();
        alertDialogFragment.setArguments(arguments);
        return alertDialogFragment;
    }

    /**
     * New instance that create the alert dialog fragment.
     *
     * @param id      the id
     * @param title   the title
     * @param message the message
     * @param type    the type
     * @return the alert dialog fragment
     */
    public static AlertDialog newInstance(String id, String title, String message,
                                          String hint, String positiveButton, String negativeButton,
                                          DialogType type) {
        AlertDialog alertDialogFragment = new AlertDialog();
        Bundle args = new Bundle();
        args.putString(EXTRA_ID, id);
        args.putString(EXTRA_TITLE, title);
        args.putString(EXTRA_MESSAGE, message);
        args.putString(EXTRA_HINT, hint);
        args.putString(EXTRA_POSITIVE_BUTTON, positiveButton);
        args.putString(EXTRA_NEGATIVE_BUTTON, negativeButton);
        args.putSerializable(EXTRA_TYPE, type);
        alertDialogFragment.setArguments(args);
        return alertDialogFragment;
    }


    /**
     * Show the alert dialog.
     *
     * @param manager the manager
     * @param id      the id
     * @param type    the type
     */
    public static void showAlertDialog(FragmentManager manager, String id, DialogType type, AlertDialogListener alertDialogListener) {
        AlertDialog dialog = AlertDialog.newInstance(id, null, null, null, null, null, type);
        dialog.setAlertDialogListener(alertDialogListener);
        dialog.show(manager, id);
    }

    /**
     * Show the alert dialog.
     *
     * @param manager the manager
     * @param id      the id
     * @param title   the title
     * @param message the message
     * @param type    the type
     */
    public static void showAlertDialog(FragmentManager manager, String id, String title, String message, DialogType type) {
        AlertDialog.newInstance(id, title, message, null, null, null, type).show(manager, id);
    }

    /**
     * Show the alert dialog.
     *
     * @param manager        the manager
     * @param id             the id
     * @param title          the title
     * @param message        the message
     * @param type           the type
     * @param dialogListener the dialog listener
     */
    public static void showAlertDialog(FragmentManager manager, String id, String title, String message,
                                       String positiveButton, String negativeButton,
                                       DialogType type, AlertDialog.AlertDialogListener dialogListener) {
        final AlertDialog dialog = AlertDialog.newInstance(id, title, message,
                null, positiveButton, negativeButton, type);
        dialog.setAlertDialogListener(dialogListener);
        dialog.show(manager, id);
    }

    /**
     * Show the alert dialog which is not cancellable.
     *
     * @param manager        the manager
     * @param id             the id
     * @param title          the title
     * @param message        the message
     * @param type           the type
     * @param dialogListener the dialog listener
     */
    public static void showNonCancellebleAlertDialog(FragmentManager manager, String id, String title, String message, DialogType type, AlertDialog.AlertDialogListener dialogListener) {
        final AlertDialog dialog = AlertDialog.newInstance(id, title, message, null, null, null, type);
        dialog.setAlertDialogListener(dialogListener);
        dialog.setCancelable(false);
        dialog.show(manager, id);
    }

    public static void showFailureAlertDialog(FragmentManager manager, String id, String title, String message, DialogType type, AlertDialogListener dialogListener) {
        final AlertDialog dialog = AlertDialog.newInstance(id, title, message, null, null, null, type);
        dialog.setAlertDialogListener(dialogListener);
        dialog.setCancelable(false);
        dialog.show(manager, id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setCancelable(false);
    }

    /**
     * workaround for issue #17423
     */
    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setOnDismissListener(null);
        super.onDestroyView();
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        this.title = bundle.getString(EXTRA_TITLE);
        this.message = bundle.getString(EXTRA_MESSAGE);
        DialogType type = (DialogType) bundle.getSerializable(EXTRA_TYPE);

        Dialog dialog = null;
        switch (type) {

            case TYPE_YES_NO:
                dialog = getOkCancelDialog(getString(R.string.alert_dialog_retry), getString(R.string.alert_dialog_cancel));
                break;

            case TYPE_RETRY:
                dialog = getRetryDialog();
                break;

            default:
                break;
        }
        return dialog != null ? dialog : super.onCreateDialog(savedInstanceState);
    }

    /**
     * Get ok cancel dialog dialog.
     *
     * @return the dialog
     */
    private Dialog getRetryDialog() {
        return new androidx.appcompat.app.AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.alert_dialog_retry,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (alertDialogListener != null) {
                                    alertDialogListener.onPositiveButtonClicked();
                                }
                            }
                        }
                ).create();
    }

    /**
     * Sets the alert dialog listener.
     *
     * @param alertDialogListener the alert dialog listener
     */
    public void setAlertDialogListener(AlertDialogListener alertDialogListener) {
        this.alertDialogListener = alertDialogListener;
    }


    /**
     * Get ok cancel dialog dialog.
     *
     * @param positiveButton the String refers the positive button's content.
     * @param negativeButton the String refers the negative button's content.
     * @return the dialog
     */
    public Dialog getOkCancelDialog(String positiveButton, String negativeButton) {
        if (positiveButton == null) {
            positiveButton = getString(R.string.alert_dialog_retry);
        }

        if (negativeButton == null) {
            negativeButton = getString(R.string.alert_dialog_cancel);
        }

        return new androidx.appcompat.app.AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveButton,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (alertDialogListener != null) {
                                    alertDialogListener.onPositiveButtonClicked();
                                }
                            }
                        }
                )
                .setNegativeButton(negativeButton,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (alertDialogListener != null) {
                                    alertDialogListener.onNegativeButtonClicked();
                                }
                            }
                        }
                )
                .create();
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Enum Dialog type.
     */
    public enum DialogType {
        TYPE_YES_NO, TYPE_RETRY
    }


    /**
     * The interface for Alert dialog listener.
     */
    public interface AlertDialogListener {
        /**
         * A callback method triggered when ok button clicked.
         */
        void onPositiveButtonClicked();

        /**
         * A callback method triggered when cancel button clicked.
         */
        void onNegativeButtonClicked();
    }


}
