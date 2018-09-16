package com.matrixdev.dremergency.Models;

/**
 * Created on 26-Jan-17.
 */
public class CommonResponse {

    boolean success;
    String error;

    public  boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
