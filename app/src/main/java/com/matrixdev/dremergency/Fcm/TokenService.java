package com.matrixdev.dremergency.Fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.matrixdev.dremergency.Application;
import com.matrixdev.dremergency.Helpers.SharedPreferencesHelper;
import com.matrixdev.dremergency.Models.Authentication.LoginData;
import com.matrixdev.dremergency.R;
import com.matrixdev.dremergency.Utils.Constants;
import com.matrixdev.dremergency.Utils.ToastHelper;
import com.matrixdev.dremergency.web.ApiManager;
import com.matrixdev.dremergency.web.ResponseHandler;
import com.matrixdev.dremergency.web.ServerResponseListener;

import java.util.HashMap;

/**
 * Created by Milind on 13-Jan-18.
 */

public class TokenService extends FirebaseInstanceIdService implements ServerResponseListener {
    private static final String DEVICE = "device";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("----", "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        if(SharedPreferencesHelper.getDeviceData()!=null && SharedPreferencesHelper.getDeviceData().equals(refreshedToken) )
            return;

        SharedPreferencesHelper.saveDeviceData(refreshedToken);

        if(SharedPreferencesHelper.getLoginData()==null)
            return;

        LoginData user = SharedPreferencesHelper.getLoginData();

        ApiManager apiManager = new ApiManager(Application.getContext(),this);

        HashMap<String,String> params = new HashMap<>();
        params.put(Constants.PARAM_USER_ID, String.valueOf(user.getId()));
        params.put(Constants.PARAM_DEVICE,refreshedToken);

        apiManager.getStringPostResponse(DEVICE,Constants.URL_UPDATE_DEVICE,params);
    }

    @Override
    public void positiveResponse(String TAG, String response) {

    }

    @Override
    public void positiveResponse(String TAG, Object responseObj) {
        ResponseHandler.handleResponse(responseObj, new ResponseHandler.ResponseCode() {
            @Override
            public void preformCode() {
                ToastHelper.toast("Device Registered");
            }
        });
    }

    @Override
    public void negativeResponse(String TAG, String errorResponse) {
        ToastHelper.toast(getString(R.string.negativeResponseMessage));
    }
}
