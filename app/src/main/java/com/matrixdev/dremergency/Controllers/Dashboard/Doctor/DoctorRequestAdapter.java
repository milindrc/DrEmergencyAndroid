package com.matrixdev.dremergency.Controllers.Dashboard.Doctor;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.matrixdev.dremergency.Controllers.Dashboard.DoctorDashboard;
import com.matrixdev.dremergency.Models.DoctorRequest.DoctorRequestData;
import com.matrixdev.dremergency.Models.DoctorRequest.DoctorRequestResponse;
import com.matrixdev.dremergency.R;
import com.matrixdev.dremergency.Utils.Constants;
import com.matrixdev.dremergency.web.ApiManager;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorRequestAdapter extends RecyclerView.Adapter<DoctorRequestAdapter.VH> {
    ArrayList<DoctorRequestData> data;
    DoctorDashboard activity;
    RequestsFragment fragment;

    public DoctorRequestAdapter(ArrayList<DoctorRequestData> data, DoctorDashboard activity, RequestsFragment fragment) {
        this.data = data;
        this.activity = activity;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_request_item,null,false);
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
        private final TextView requestName;
        private final TextView requestNote;
        private final Button requestCall;
        private final Button requestLocation;
        private final Button requestAccept;

        public VH(View itemView) {
            super(itemView);
            view = itemView;
            requestName = (TextView) itemView.findViewById(R.id.request_name);
            requestNote = (TextView) itemView.findViewById(R.id.request_note);
            requestCall = (Button) itemView.findViewById(R.id.request_call);
            requestLocation = (Button) itemView.findViewById(R.id.request_location);
            requestAccept = (Button) itemView.findViewById(R.id.request_accept);
        }


        public void setup(final DoctorRequestData data) {
            requestName.setText(data.getRequest().getName());
            requestNote.setText(data.getRequest().getNote());
            requestCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + data.getRequest().getMobile()));
                    activity.startActivity(intent);

                }
            });
            requestLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getLocation(data.getRequest().getLatLng().split(",")[0],data.getRequest().getLatLng().split(",")[1],"Patient");
                }
            });
            requestAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    accept(data);
                }
            });

        }
    }

    private void accept(DoctorRequestData data) {
        ApiManager manager = new ApiManager(activity,fragment);

        HashMap<String,String> param = new HashMap<>();
        param.put(Constants.PARAM_USER_ID, String.valueOf(activity.getLoginData().getId()));
        param.put(Constants.PARAM_DOCTOR_REQUEST_ID, String.valueOf(data.getId()));

        manager.doJsonParsing(true);
        manager.setClassTypeForJson(DoctorRequestResponse.class);

        manager.getStringPostResponse(fragment.ACCEPT,Constants.URL_DOCTOR_REQUEST_ACCEPT,param);
    }

    private void getLocation(String latitude, String longitude, String label) {
        String strUri = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + label + ")";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
        activity.startActivity(intent);
    }

}
