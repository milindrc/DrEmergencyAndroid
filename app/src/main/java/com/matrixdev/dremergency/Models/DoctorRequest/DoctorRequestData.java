package com.matrixdev.dremergency.Models.DoctorRequest;

import com.google.gson.annotations.SerializedName;
import com.matrixdev.dremergency.Models.RequestData.RequestData;

public class DoctorRequestData {
    @SerializedName("doctor_id")
    int doctorId;
    RequestData request;
    @SerializedName("updated_at")
    String updatedAt;
    int accepted;
    @SerializedName("created_at")
    String createdAt;
    int id;
    @SerializedName("request_id")
    int requestId;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public RequestData getRequest() {
        return request;
    }

    public void setRequest(RequestData request) {
        this.request = request;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}
