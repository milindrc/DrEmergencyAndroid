package com.matrixdev.dremergency.Models.Tips;

import com.matrixdev.dremergency.Models.CommonResponse;

import java.util.ArrayList;

public class TipResponse extends CommonResponse {
    ArrayList<TipData> data;

    public ArrayList<TipData> getData() {
        return data;
    }

    public void setData(ArrayList<TipData> data) {
        this.data = data;
    }
}
