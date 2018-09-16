package com.matrixdev.dremergency.Utils;

import android.widget.Toast;

import com.matrixdev.dremergency.Application;

/**
 * Created on 24-Mar-17.
 */

public class ToastHelper {
    public static void toast(String message)
    {
        Toast.makeText(Application.getContext(),message, Toast.LENGTH_LONG).show();
    }
}
