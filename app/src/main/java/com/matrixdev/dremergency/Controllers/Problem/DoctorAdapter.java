package com.matrixdev.dremergency.Controllers.Problem;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.matrixdev.dremergency.Helpers.SosHelper;
import com.matrixdev.dremergency.Models.Authentication.LoginData;
import com.matrixdev.dremergency.Models.CommonResponse;
import com.matrixdev.dremergency.Models.Tips.TipData;
import com.matrixdev.dremergency.R;
import com.matrixdev.dremergency.web.ApiManager;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.VH> {
    ArrayList<LoginData> data;
    ProblemActivity activity;
    DoctorFragment fragment;

    public DoctorAdapter(ArrayList<LoginData> data, ProblemActivity activity, DoctorFragment fragment) {
        this.data = data;
        this.activity = activity;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item, null, false);
        return new VH(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.setup(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView doctorName;
        private final TextView doctorDesignation;
        private final TextView qualifications;
        private final Button requestBtn;
        private final View doctorVerified;

        public VH(View itemView) {
            super(itemView);
            view = itemView;
            doctorName = (TextView) itemView.findViewById(R.id.name);
            doctorDesignation = (TextView) itemView.findViewById(R.id.designation);
            qualifications = (TextView) itemView.findViewById(R.id.qualification);
            requestBtn = (Button) itemView.findViewById(R.id.doctor_request);
            doctorVerified = itemView.findViewById(R.id.doctor_verified);
        }


        public void setup(final LoginData data) {
            if(data.getVerified()==0)
                doctorVerified.setVisibility(View.GONE);
            else
                doctorVerified.setVisibility(View.VISIBLE);
            doctorName.setText(data.getName());
            doctorDesignation.setText(data.getDesignation());
            qualifications.setText(data.getQualifications());
            requestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SosHelper.sendDoctor(activity,activity.getLoginData(),data.getId());
                }
            });
        }
    }


}
