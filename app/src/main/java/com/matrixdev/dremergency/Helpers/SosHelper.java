package com.matrixdev.dremergency.Helpers;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.matrixdev.dremergency.Controllers.BaseActivity;
import com.matrixdev.dremergency.Models.Authentication.LoginData;
import com.matrixdev.dremergency.Models.CommonResponse;
import com.matrixdev.dremergency.R;
import com.matrixdev.dremergency.Utils.Constants;
import com.matrixdev.dremergency.web.ApiManager;
import com.matrixdev.dremergency.web.ResponseHandler;
import com.matrixdev.dremergency.web.ServerResponseListener;
import com.schibstedspain.leku.LocationPickerActivity;

import java.util.HashMap;

import static com.matrixdev.dremergency.Controllers.BaseActivity.MAP_BUTTON_REQUEST_CODE;

public class SosHelper {
    private static final String SOS = "sos";

    public static void sendSOS(final BaseActivity activity, final LoginData loginData) {
        DialogHelper.with(activity).showCustomDialogCancelable(R.layout.sos_layout, new DialogHelper.ViewHandler() {
            public View sosSubmit;
            public EditText sosNote;
            public EditText sosLocation;
            public EditText sosAddress;
            public EditText sosMobile;
            public EditText sosName;

            @Override
            public void init(AlertDialog dialog) {
                sosName = (EditText) dialog.findViewById(R.id.sos_name);
                sosMobile = (EditText) dialog.findViewById(R.id.sos_mobile);
                sosAddress = (EditText) dialog.findViewById(R.id.sos_address);
                sosLocation = (EditText) dialog.findViewById(R.id.sos_location);
                activity.currentSosLocation = sosLocation;
                sosNote = (EditText) dialog.findViewById(R.id.sos_note);
                sosSubmit = dialog.findViewById(R.id.sos_submit);

                if (loginData != null) {
                    sosName.setText(loginData.getName());
                    sosMobile.setText(loginData.getMobile());
                    sosAddress.setText(loginData.getAddress());
                    sosLocation.setText(loginData.getLatLng());
                }

            }

            @Override
            public void setListeners(final AlertDialog dialog) {
                sosSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ApiManager apiManager = new ApiManager(activity, new ServerResponseListener() {
                            @Override
                            public void positiveResponse(String TAG, String response) {

                            }

                            @Override
                            public void positiveResponse(String TAG, Object responseObj) {
                                activity.stopLoader();
                                ResponseHandler.handleResponse(responseObj, new ResponseHandler.ResponseCode() {
                                    @Override
                                    public void preformCode() {
                                    }
                                });
                            }

                            @Override
                            public void negativeResponse(String TAG, String errorResponse) {

                            }
                        });
                        HashMap<String, String> param = new HashMap<>();
                        param.put(Constants.PARAM_NAME, sosName.getText().toString());
                        param.put(Constants.PARAM_MOBILE, sosMobile.getText().toString());
                        param.put(Constants.PARAM_ADDRESS, sosAddress.getText().toString());
                        param.put(Constants.PARAM_LOCATION, sosLocation.getText().toString());
                        param.put(Constants.PARAM_NOTE, sosNote.getText().toString());

                        apiManager.doJsonParsing(true);
                        apiManager.setClassTypeForJson(CommonResponse.class);

                        apiManager.getStringPostResponse(SOS, Constants.URL_SOS, param);
                        activity.startLoader();
                        dialog.dismiss();
                    }
                });
                sosLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent locationPickerIntent = new LocationPickerActivity.Builder()
                                .withLocation(41.4036299, 2.1743558)
                                .withGeolocApiKey("AIzaSyDdZpCHuJOxF1GM-6cONvyw98Gb3OCKUMI")
                                .shouldReturnOkOnBackPressed()
                                .withStreetHidden()
                                .withCityHidden()
                                .withZipCodeHidden()
                                .withSatelliteViewHidden()
                                .withGooglePlacesEnabled()
                                .withVoiceSearchHidden()
                                .build(activity);

                        activity.startActivityForResult(locationPickerIntent, MAP_BUTTON_REQUEST_CODE);

                    }
                });
            }
        });

    }
    public static void sendDoctor(final BaseActivity activity, final LoginData loginData, final int docId) {
        DialogHelper.with(activity).showCustomDialogCancelable(R.layout.sos_layout, new DialogHelper.ViewHandler() {
            public View sosSubmit;
            public EditText sosNote;
            public EditText sosLocation;
            public EditText sosAddress;
            public EditText sosMobile;
            public EditText sosName;

            @Override
            public void init(AlertDialog dialog) {
                sosName = (EditText) dialog.findViewById(R.id.sos_name);
                sosMobile = (EditText) dialog.findViewById(R.id.sos_mobile);
                sosAddress = (EditText) dialog.findViewById(R.id.sos_address);
                sosLocation = (EditText) dialog.findViewById(R.id.sos_location);
                activity.currentSosLocation = sosLocation;
                sosNote = (EditText) dialog.findViewById(R.id.sos_note);
                sosSubmit = dialog.findViewById(R.id.sos_submit);

                if (loginData != null) {
                    sosName.setText(loginData.getName());
                    sosMobile.setText(loginData.getMobile());
                    sosAddress.setText(loginData.getAddress());
                    sosLocation.setText(loginData.getLatLng());
                }

            }

            @Override
            public void setListeners(final AlertDialog dialog) {
                sosSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ApiManager apiManager = new ApiManager(activity, new ServerResponseListener() {
                            @Override
                            public void positiveResponse(String TAG, String response) {

                            }

                            @Override
                            public void positiveResponse(String TAG, Object responseObj) {
                                activity.stopLoader();
                                ResponseHandler.handleResponse(responseObj, new ResponseHandler.ResponseCode() {
                                    @Override
                                    public void preformCode() {
                                    }
                                });
                            }

                            @Override
                            public void negativeResponse(String TAG, String errorResponse) {

                            }
                        });
                        HashMap<String, String> param = new HashMap<>();
                        param.put(Constants.PARAM_NAME, sosName.getText().toString());
                        param.put(Constants.PARAM_MOBILE, sosMobile.getText().toString());
                        param.put(Constants.PARAM_ADDRESS, sosAddress.getText().toString());
                        param.put(Constants.PARAM_LOCATION, sosLocation.getText().toString());
                        param.put(Constants.PARAM_NOTE, sosNote.getText().toString());
                        param.put(Constants.PARAM_DOCTOR_ID, String.valueOf(docId));
                        param.put(Constants.PARAM_USER_ID, String.valueOf(loginData.getId()));

                        apiManager.doJsonParsing(true);
                        apiManager.setClassTypeForJson(CommonResponse.class);

                        apiManager.getStringPostResponse(SOS, Constants.URL_DOC_SOS, param);
                        activity.startLoader();
                        dialog.dismiss();
                    }
                });
                sosLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent locationPickerIntent = new LocationPickerActivity.Builder()
                                .withLocation(41.4036299, 2.1743558)
                                .withGeolocApiKey("AIzaSyDdZpCHuJOxF1GM-6cONvyw98Gb3OCKUMI")
                                .shouldReturnOkOnBackPressed()
                                .withStreetHidden()
                                .withCityHidden()
                                .withZipCodeHidden()
                                .withSatelliteViewHidden()
                                .withGooglePlacesEnabled()
                                .withVoiceSearchHidden()
                                .build(activity);

                        activity.startActivityForResult(locationPickerIntent, MAP_BUTTON_REQUEST_CODE);

                    }
                });
            }
        });

    }
}
