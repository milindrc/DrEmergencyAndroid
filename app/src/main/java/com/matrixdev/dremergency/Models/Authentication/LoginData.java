package com.matrixdev.dremergency.Models.Authentication;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vagish Dixit on 3/10/2018.
 */

public class LoginData implements Parcelable {

    @SerializedName("blood_group_id")
    int bloodGroupId;
    String address;
    String gender;
    String profile;
    String mobile;
    int verified;
    int blacklist;
    int active;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("email_verified_at")
    String emailVerifiedAt;
    String qualifications;
    @SerializedName("lat_lng")
    String latLng;
    @SerializedName("updated_at")
    String updatedAt;
    @SerializedName("role_id")
    int roleId;
    String name;
    int id;
    String designation;
    String device;
    int age;
    String email;

    public LoginData() {
    }

    protected LoginData(Parcel in) {
        bloodGroupId = in.readInt();
        address = in.readString();
        gender = in.readString();
        profile = in.readString();
        mobile = in.readString();
        verified = in.readInt();
        blacklist = in.readInt();
        active = in.readInt();
        createdAt = in.readString();
        emailVerifiedAt = in.readString();
        qualifications = in.readString();
        latLng = in.readString();
        updatedAt = in.readString();
        roleId = in.readInt();
        name = in.readString();
        id = in.readInt();
        designation = in.readString();
        device = in.readString();
        age = in.readInt();
        email = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bloodGroupId);
        dest.writeString(address);
        dest.writeString(gender);
        dest.writeString(profile);
        dest.writeString(mobile);
        dest.writeInt(verified);
        dest.writeInt(blacklist);
        dest.writeInt(active);
        dest.writeString(createdAt);
        dest.writeString(emailVerifiedAt);
        dest.writeString(qualifications);
        dest.writeString(latLng);
        dest.writeString(updatedAt);
        dest.writeInt(roleId);
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(designation);
        dest.writeString(device);
        dest.writeInt(age);
        dest.writeString(email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginData> CREATOR = new Creator<LoginData>() {
        @Override
        public LoginData createFromParcel(Parcel in) {
            return new LoginData(in);
        }

        @Override
        public LoginData[] newArray(int size) {
            return new LoginData[size];
        }
    };

    public int getBloodGroupId() {
        return bloodGroupId;
    }

    public void setBloodGroupId(int bloodGroupId) {
        this.bloodGroupId = bloodGroupId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public int getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(int blacklist) {
        this.blacklist = blacklist;
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

    public String getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(String emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
