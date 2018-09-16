package com.matrixdev.dremergency.Models.DoctorRequest;

import com.matrixdev.dremergency.Models.CommonResponse;

import java.util.ArrayList;

public class DoctorRequestResponse extends CommonResponse {
    ArrayList<DoctorRequestData> data;

    public ArrayList<DoctorRequestData> getData() {
        return data;
    }

    public void setData(ArrayList<DoctorRequestData> data) {
        this.data = data;
    }
}
