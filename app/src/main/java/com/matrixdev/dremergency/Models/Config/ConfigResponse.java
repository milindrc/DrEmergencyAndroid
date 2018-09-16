package com.matrixdev.dremergency.Models.Config;


import com.matrixdev.dremergency.Models.CommonResponse;

/**
 * Created by Milind on 7/13/2018.
 */

public class ConfigResponse extends CommonResponse {
    ConfigData data;

    public ConfigData getData() {
        return data;
    }

    public void setData(ConfigData data) {
        this.data = data;
    }
}
