package com.matrixdev.dremergency.Controllers.Dashboard.Doctor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.matrixdev.dremergency.Controllers.Dashboard.DoctorDashboard;
import com.matrixdev.dremergency.Models.Problem.ProblemData;
import com.matrixdev.dremergency.Models.Problem.ProblemResponse;
import com.matrixdev.dremergency.R;
import com.matrixdev.dremergency.Utils.Constants;
import com.matrixdev.dremergency.web.ApiManager;

import java.util.ArrayList;
import java.util.HashMap;

public class ProblemAdapter extends BaseAdapter {
    ArrayList<ProblemData> data;
    DoctorDashboard activity;
    SubscriptionsFragment fragment;

    public ProblemAdapter(ArrayList<ProblemData> data, DoctorDashboard dashboard, SubscriptionsFragment fragment) {
        this.data = data;
        this.activity = dashboard;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.problem_item,null,false);

        setup(v,data.get(i));

        return v;
    }

    private void setup(View v, final ProblemData problemData) {
        TextView problemName = (TextView) v.findViewById(R.id.problem_name);
        final CheckBox problemCheckbox = (CheckBox) v.findViewById(R.id.problem_checkbox);

        problemName.setText(problemData.getName());
        problemCheckbox.setChecked(problemData.isSelected());
        problemCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiManager apiManager = new ApiManager(activity,fragment);
                apiManager.doJsonParsing(true);
                apiManager.setClassTypeForJson(ProblemResponse.class);

                HashMap<String,String> param = new HashMap<>();
                param.put(Constants.PARAM_USER_ID, String.valueOf(activity.getLoginData().getId()));
                param.put(Constants.PARAM_PROBLEM_ID, String.valueOf(problemData.getId()));

                apiManager.getStringPostResponse(fragment.PROBLEMS,(problemCheckbox.isChecked())?Constants.URL_DOCTOR_PROBLEM_SELECT:Constants.URL_DOCTOR_PROBLEMS_REMOVE,param);
            }
        });
        problemCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            }
        });
    }
}
