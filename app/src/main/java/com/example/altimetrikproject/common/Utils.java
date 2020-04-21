package com.example.altimetrikproject.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import com.example.altimetrikproject.ui.AlertDialog;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Utils {

    public static int response_code;

    /**
     * Show the alert dialog.
     *
     * @param manager        the manager
     * @param tag            the string refers the dialog fragment tag
     * @param arguments      the arguments refers the bundle to dialog fragment.
     * @param dialogListener the dialog listener
     */
    public static void showAlertDialog(FragmentManager manager, String tag, Bundle arguments, AlertDialog.AlertDialogListener dialogListener) {
        final AlertDialog dialog = AlertDialog.newInstance(arguments);
        dialog.setAlertDialogListener(dialogListener);
        dialog.show(manager, tag);
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            return info != null && info.isConnected();
        }
        return false;
    }

    public static String GET(OkHttpClient client, HttpUrl url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        response_code = response.code();
        return response.body().string();
    }
}
