package com.matrixdev.dremergency.Controllers.Problem;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matrixdev.dremergency.Models.Authentication.LoginData;
import com.matrixdev.dremergency.Models.Doctor.DoctorResponse;
import com.matrixdev.dremergency.Models.DoctorRequest.DoctorRequestResponse;
import com.matrixdev.dremergency.Models.Problem.ProblemData;
import com.matrixdev.dremergency.R;
import com.matrixdev.dremergency.Utils.Constants;
import com.matrixdev.dremergency.Utils.PermissionHandler;
import com.matrixdev.dremergency.web.ApiManager;
import com.matrixdev.dremergency.web.ResponseHandler;
import com.matrixdev.dremergency.web.ServerResponseListener;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorFragment extends Fragment implements ServerResponseListener {


    private static final String DOCTORS = "doctors";
    private ProblemActivity activity;
    private RecyclerView doctorList;
    private SwipeRefreshLayout doctorRefresh;
    private ArrayList<LoginData> doctors;
    private DoctorAdapter adapter;
    boolean wait = false;

    public DoctorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ProblemActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_doctor, container, false);
        initUI(rootView);
        setListeners();

        return rootView;
    }

    private void setListeners() {
        doctorRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLocation();
            }
        });
    }

    private void initUI(View rootView) {
        doctorList = (RecyclerView) rootView.findViewById(R.id.doctor_list);
        doctorList.setLayoutManager(new LinearLayoutManager(activity));
        doctorRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.doctor_refresh);

        fetchDoctors("28.4941550,77.0826574");
        if (PermissionHandler.isLocationPermissionGranted(activity)) {
            getLocation();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!wait)
                        return;

                    final LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    Location locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (locationGps != null) {
                        fetchDoctors(locationGps.getLatitude()+","+locationGps.getLongitude());
                    }

                    else if (locationNetwork != null) {
                        fetchDoctors(locationNetwork.getLatitude()+","+locationNetwork.getLongitude());
                    }
                }
            }, 3000);
        }
    }

    private void getLocation() {
        final LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        try {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria, true), 10, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        wait = false;
                        fetchDoctors(location.getLatitude() + "," + location.getLongitude());
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void fetchDoctors(String latLng) {
        ApiManager apiManager = new ApiManager(activity, this);

        apiManager.doJsonParsing(true);
        apiManager.setClassTypeForJson(DoctorResponse.class);

        HashMap<String, String> param = new HashMap<>();
        param.put(Constants.PARAM_USER_ID, String.valueOf(activity.getLoginData().getId()));
        param.put(Constants.PARAM_PROBLEM_ID, String.valueOf(activity.getProblemData().getId()));
        param.put(Constants.PARAM_LOCATION, latLng);

        apiManager.getStringPostResponse(DOCTORS, Constants.URL_PROBLEM_DOCTORS, param);
        activity.startLoader();
    }

    @Override
    public void positiveResponse(String TAG, String response) {

    }

    @Override
    public void positiveResponse(String TAG, final Object responseObj) {
        activity.stopLoader();
        doctorRefresh.setRefreshing(false);
        ResponseHandler.handleResponse(responseObj, new ResponseHandler.ResponseCode() {
            @Override
            public void preformCode() {
                DoctorResponse response = (DoctorResponse) responseObj;
                doctors = response.getData();
                showDoctors();
            }
        });
    }

    private void showDoctors() {
        adapter = new DoctorAdapter(doctors, activity, this);
        doctorList.setLayoutManager(new LinearLayoutManager(activity));
        doctorList.setAdapter(adapter);
    }

    @Override
    public void negativeResponse(String TAG, String errorResponse) {

    }
}
