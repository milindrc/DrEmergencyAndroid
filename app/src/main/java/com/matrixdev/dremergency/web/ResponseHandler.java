package com.matrixdev.dremergency.web;


import com.matrixdev.dremergency.Models.CommonResponse;
import com.matrixdev.dremergency.Utils.ToastHelper;

/**
 * Created on 25-Mar-17.
 */

public class ResponseHandler {

    public static void handleResponse(Object responseObj, ResponseCode responseCode)
    {
            CommonResponse topicResponse = (CommonResponse) responseObj;
            if(topicResponse.isSuccess())
            {
                responseCode.preformCode();
            }else{
                ToastHelper.toast(topicResponse.getError());
            }
    }

    public abstract static class ResponseCode{
        public abstract void preformCode();
    }

}
