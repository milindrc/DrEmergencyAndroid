package com.matrixdev.dremergency.Controllers.Dashboard.Doctor;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.matrixdev.dremergency.Controllers.Dashboard.DoctorDashboard;
import com.matrixdev.dremergency.Models.Problem.ProblemData;
import com.matrixdev.dremergency.Models.Problem.ProblemResponse;
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
public class SubscriptionsFragment extends Fragment implements ServerResponseListener {


    public static final String PROBLEMS = "problems";
    private DoctorDashboard activity;
    private ArrayList<ProblemData> problems;
    private ListView problemList;
    private ProblemAdapter adapter;

    public SubscriptionsFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_subscriptions, container, false);
        initUI(rootView);
        return rootView;
    }

    private void initUI(View rootView) {

        problemList = (ListView) rootView.findViewById(R.id.problem_list);

        fetchProblems();
    }

    private void fetchProblems() {
        ApiManager apiManager = new ApiManager(activity,this);
        apiManager.doJsonParsing(true);
        apiManager.setClassTypeForJson(ProblemResponse.class);

        HashMap<String,String> params = new HashMap<>();
        params.put(Constants.PARAM_USER_ID, String.valueOf(activity.getLoginData().getId()));

        apiManager.getStringPostResponse(PROBLEMS,Constants.URL_DOCTOR_PROBLEMS,params);
        activity.startLoader();
    }

    @Override
    public void positiveResponse(String TAG, String response) {

    }

    @Override
    public void positiveResponse(String TAG, final Object responseObj) {
        activity.stopLoader();
        if(TAG.equals(PROBLEMS))
        {
            ResponseHandler.handleResponse(responseObj, new ResponseHandler.ResponseCode() {
                @Override
                public void preformCode() {
                    ProblemResponse response = (ProblemResponse) responseObj;
                    problems = response.getData();
                    showProblems();
                }
            });
        }

    }

    private void showProblems() {
        adapter = new ProblemAdapter(problems,activity,this);
        problemList.setAdapter(adapter);
    }

    @Override
    public void negativeResponse(String TAG, String errorResponse) {

    }
}
