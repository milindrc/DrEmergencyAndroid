package com.matrixdev.dremergency.Utils;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;


import com.matrixdev.dremergency.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Strings {
    public static final String EMPTY = "";

    public static String nullSafeString(String s) {
        return s == null ? EMPTY : s;
    }

    public static boolean isEmpty(String string) {
        return (string == null || string.trim().equals(EMPTY));
    }

    public static String removeCommasFromPrice(String string) {
        return (string.replaceAll("[,]", EMPTY));
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    public static String readAssetFile(String assetPath) {
        try {
            InputStream assetInputStream = Application.getContext().getAssets().open(assetPath);
            return inputStreamToString(assetInputStream);
        } catch (IOException ioe) {
            Log.d("----","Strings -> read asset file -> catch ioe");
            ioe.printStackTrace();
            return EMPTY;
        }
    }

    public static String inputStreamToString(InputStream inputStream) {
        InputStreamReader isr = new InputStreamReader(inputStream);    // Input stream that translates bytes to characters
        BufferedReader br = new BufferedReader(isr);                    // Buffered input character stream
        String str;
        StringBuilder output = new StringBuilder();
        try {
            while ((str = br.readLine()) != null) {
                output.append(str);
            }

            return output.toString();
        } catch (IOException e) {
            e.printStackTrace();

            return EMPTY;
        } finally {
            try {
                isr.close();
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getTextFromEditText(EditText editText) {
        String text = editText.getText().toString();
        return (text != null) ? text.trim() : Strings.EMPTY;
    }

    public static String getFormattedPrice(Activity activity, String string) {
        String decimalRemovedString = string.split("\\.")[0];
        int value = getIntFromString(decimalRemovedString);
        return "Rs" + " " + String.format("%,d", value);
    }

    public static String getFormattedText(String text) {
        if (!(text.trim().substring(0, 1).equals("*") || text.trim().equals(EMPTY) || text.trim().equals("&nbsp;"))) {
            text = "&#8226;" + text;
        }
        return text;
    }

    public static String convertDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d = format.parse(date);
            SimpleDateFormat serverFormat = new SimpleDateFormat("MMM dd, yyyy");
            return serverFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EMPTY;
    }

    public static int getIntFromString(String numberString) {

        String numString = Strings.nullSafeString(numberString);

        try {
            int numberInt = Integer.parseInt(numString);

            return numberInt;
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();

            return 0;
        }
    }
}
