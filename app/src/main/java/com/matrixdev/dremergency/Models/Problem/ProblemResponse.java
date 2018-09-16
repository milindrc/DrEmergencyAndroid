package com.matrixdev.dremergency.Models.Problem;

import com.matrixdev.dremergency.Models.CommonResponse;

import java.util.ArrayList;

public class ProblemResponse extends CommonResponse {
    ArrayList<ProblemData> data;

    public ArrayList<ProblemData> getData() {
        return data;
    }

    public void setData(ArrayList<ProblemData> data) {
        this.data = data;
    }

}
