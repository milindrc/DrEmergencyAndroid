package com.matrixdev.dremergency.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.matrixdev.dremergency.Helpers.DialogHelper;
import com.matrixdev.dremergency.Helpers.FragmentSwitcher;
import com.matrixdev.dremergency.Helpers.SharedPreferencesHelper;
import com.matrixdev.dremergency.Models.Authentication.LoginData;
import com.matrixdev.dremergency.Models.Config.ConfigData;
import com.matrixdev.dremergency.R;

import static com.schibstedspain.leku.LocationPickerActivityKt.ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.LATITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.LOCATION_ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.LONGITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.TRANSITION_BUNDLE;
import static com.schibstedspain.leku.LocationPickerActivityKt.ZIPCODE;

public class BaseActivity extends AppCompatActivity {
    public static final int MAP_BUTTON_REQUEST_CODE = 2;
    public static final int ATTACH_FILE = 5;
    public static Bitmap card;
    public static int bottom = 0;
    public static LoginData loginData;
    public static ConfigData configData;
    public FragmentSwitcher fragmentSwitcher;
    private AlertDialog dialog;
    public static OnLocationRecieveListener onLocationRecieveListener;
    public EditText currentSosLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public static int getBottom() {
        return bottom;
    }

    public static void setBottom(int bottom) {
        BaseActivity.bottom = bottom;
    }

    public FragmentSwitcher getFragmentSwitcher() {
        return fragmentSwitcher;
    }

    public void setFragmentSwitcher(FragmentSwitcher fragmentSwitcher) {
        this.fragmentSwitcher = fragmentSwitcher;
    }

    public void startLoader() {
        if (dialog != null && !dialog.isShowing())
            dialog = DialogHelper.with(this).startIndicator();
        else if (dialog == null)
            dialog = DialogHelper.with(this).startIndicator();
    }

    public void stopLoader() {
        DialogHelper.with(this).stopIndicator(dialog);
    }

    public static LoginData getLoginData() {
        if(loginData==null)
            loginData = SharedPreferencesHelper.getLoginData();
        return loginData;
    }

    public static ConfigData getConfigData() {
        if(configData==null)
            configData = SharedPreferencesHelper.getConfigData();
        return configData;
    }

    public static void setConfigData(ConfigData configData) {
        BaseActivity.configData = configData;
    }

    interface OnLocationRecieveListener{
      void onGetLocation(String location);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Log.d("RESULT****", "OK");
            if (requestCode == MAP_BUTTON_REQUEST_CODE) {
                double latitude = data.getDoubleExtra(LATITUDE, 0.0);
                Log.d("LATITUDE****", String.valueOf(latitude));
                double longitude = data.getDoubleExtra(LONGITUDE, 0.0);
                Log.d("LONGITUDE****", String.valueOf(longitude));
                String address = data.getStringExtra(LOCATION_ADDRESS);
                Log.d("ADDRESS****", address);
                String postalcode = data.getStringExtra(ZIPCODE);
                Log.d("POSTALCODE****", postalcode);
                Bundle bundle = data.getBundleExtra(TRANSITION_BUNDLE);
                String fullAddress = data.getParcelableExtra(ADDRESS).toString();
                if (fullAddress != null) {
                    Log.d("FULL ADDRESS****", fullAddress.toString());
                }
                if (currentSosLocation != null)
                    currentSosLocation.setText(String.format("%s,%s", latitude, longitude));
            }
        }

    }
}
