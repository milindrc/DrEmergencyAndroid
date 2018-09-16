package com.matrixdev.dremergency.Controllers.Dashboard.Doctor;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matrixdev.dremergency.Controllers.Dashboard.DoctorDashboard;
import com.matrixdev.dremergency.Models.DoctorRequest.DoctorRequestData;
import com.matrixdev.dremergency.Models.DoctorRequest.DoctorRequestResponse;
import com.matrixdev.dremergency.R;
import com.matrixdev.dremergency.Utils.Constants;
import com.matrixdev.dremergency.web.ApiManager;
import com.matrixdev.dremergency.web.ResponseHandler;
import com.matrixdev.dremergency.web.ServerResponseListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcceptFragment extends Fragment implements ServerResponseListener {
    private static final String DOCTOR_REQUESTS = "doctor_requests";
    public static final String ACCEPT = "accept";
    private DoctorDashboard activity;
    private RecyclerView requestList;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<DoctorRequestData> requests;
    private DoctorAcceptAdapter adapter;

    public AcceptFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (DoctorDashboard) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_accept, container, false);
        initUI(rootView);
        setListeners();
        return rootView;
    }

    private void setListeners() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchRequests();
            }
        });
    }

    private void initUI(View rootView) {
        requestList = (RecyclerView) rootView.findViewById(R.id.doctor_request_list);
        requestList.setLayoutManager(new LinearLayoutManager(activity));
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.doctor_request_refresh);

        fetchRequests();
    }

    private void fetchRequests() {
        ApiManager apiManager = new ApiManager(activity, this);

        apiManager.doJsonParsing(true);
        apiManager.setClassTypeForJson(DoctorRequestResponse.class);

        HashMap<String, String> param = new HashMap<>();
        param.put(Constants.PARAM_USER_ID, String.valueOf(activity.getLoginData().getId()));

        apiManager.getStringPostResponse(DOCTOR_REQUESTS, Constants.URL_DOCTOR_REQUESTS_HISTORY, param);
        activity.startLoader();
    }

    @Override
    public void positiveResponse(String TAG, String response) {

    }

    @Override
    public void positiveResponse(String TAG, final Object responseObj) {
        activity.stopLoader();
        refreshLayout.setRefreshing(false);
        if (TAG.equals(DOCTOR_REQUESTS)) {
            ResponseHandler.handleResponse(responseObj, new ResponseHandler.ResponseCode() {
                @Override
                public void preformCode() {
                    DoctorRequestResponse response = (DoctorRequestResponse) responseObj;
                    requests = response.getData();
                    showRequests();
                }
            });
        }
        if(TAG.equals(ACCEPT)){
            ResponseHandler.handleResponse(responseObj, new ResponseHandler.ResponseCode() {
                @Override
                public void preformCode() {
                    DoctorRequestResponse response = (DoctorRequestResponse) responseObj;
                    requests = response.getData();
                    showRequests();
                }
            });
        }
    }

    private void showRequests() {
        requestList.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new DoctorAcceptAdapter(requests, activity, this);
        requestList.setAdapter(adapter);
    }

    @Override
    public void negativeResponse(String TAG, String errorResponse) {

    }


}
