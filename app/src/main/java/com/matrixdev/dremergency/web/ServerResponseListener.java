package com.matrixdev.dremergency.web;

/**
 * Created on 01-Dec-15.
 */
public interface ServerResponseListener {

    public void positiveResponse(String TAG, String response);
    public void positiveResponse(String TAG, Object responseObj);
    public void negativeResponse(String TAG, String errorResponse);
}
