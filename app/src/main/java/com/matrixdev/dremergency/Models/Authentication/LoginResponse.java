package com.matrixdev.dremergency.Models.Authentication;


import com.matrixdev.dremergency.Models.CommonResponse;

/**
 * Created by Vagish Dixit on 3/10/2018.
 */

public class LoginResponse extends CommonResponse {
    LoginData data;

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }
}
