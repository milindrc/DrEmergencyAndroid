package com.matrixdev.dremergency.Controllers.Problem;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matrixdev.dremergency.Models.Problem.ProblemData;
import com.matrixdev.dremergency.Models.Tips.TipData;
import com.matrixdev.dremergency.Models.Tips.TipResponse;
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
public class TipsFragment extends Fragment implements ServerResponseListener {


    private static final String TIPS = "tips";
    private ProblemActivity activity;
    private RecyclerView tipList;
    public ProblemData problemData;
    private ArrayList<TipData> tips;
    private TipsAdapter adapter;

    public TipsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ProblemActivity) getActivity();
        problemData = activity.getProblemData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tips, container, false);
        initUI(view);

        return view;
    }

    private void initUI(View view) {
        tipList = (RecyclerView) view.findViewById(R.id.tip_list);

        getTips();
    }

    private void getTips() {
        ApiManager apiManager = new ApiManager(activity,this);
        apiManager.doJsonParsing(true);
        apiManager.setClassTypeForJson(TipResponse.class);

        HashMap<String,String> param = new HashMap<>();
        param.put(Constants.PARAM_USER_ID, String.valueOf(activity.getLoginData().getId()));
        param.put(Constants.PARAM_PROBLEM_ID, String.valueOf(problemData.getId()));

        apiManager.getStringPostResponse(TIPS,Constants.URL_TIPS,param);
        activity.startLoader();
    }

    @Override
    public void positiveResponse(String TAG, String response) {

    }

    @Override
    public void positiveResponse(String TAG, final Object responseObj) {
        activity.stopLoader();
        if(TAG.equals(TIPS))
        {
            ResponseHandler.handleResponse(responseObj, new ResponseHandler.ResponseCode() {
                @Override
                public void preformCode() {
                    TipResponse response = (TipResponse) responseObj;
                    tips = response.getData();
                    showTips();
                }
            });
        }
    }

    private void showTips() {
        adapter = new TipsAdapter(tips,activity,this);
        tipList.setLayoutManager(new LinearLayoutManager(activity));
        tipList.setAdapter(adapter);
    }

    @Override
    public void negativeResponse(String TAG, String errorResponse) {

    }
}
