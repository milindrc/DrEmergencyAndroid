package com.matrixdev.dremergency.Models.Doctor;

import com.matrixdev.dremergency.Models.Authentication.LoginData;
import com.matrixdev.dremergency.Models.CommonResponse;

import java.util.ArrayList;

public class DoctorResponse extends CommonResponse {
    ArrayList<LoginData> data;

    public ArrayList<LoginData> getData() {
        return data;
    }

    public void setData(ArrayList<LoginData> data) {
        this.data = data;
    }
}
