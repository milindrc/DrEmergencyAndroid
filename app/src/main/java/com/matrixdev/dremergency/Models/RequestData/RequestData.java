package com.matrixdev.dremergency.Models.RequestData;

import com.google.gson.annotations.SerializedName;
import com.matrixdev.dremergency.Models.Authentication.LoginData;

public class RequestData {
    String note;
    @SerializedName("lat_lng")
    String latLng;
    String address;
    @SerializedName("updated_at")
    String updatedAt;
    @SerializedName("user_id")
    int userId;
    String name;
    String mobile;
    int active;
    @SerializedName("created_at")
    String createdAt;
    int id;
    LoginData user;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
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

    public LoginData getUser() {
        return user;
    }

    public void setUser(LoginData user) {
        this.user = user;
    }
}
