package com.matrixdev.dremergency.Models.Problem;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProblemData implements Parcelable{
    @SerializedName("updated_at")
    String updatedAt;
    String profile;
    String name;
    String description;
    @SerializedName("created_at")
    String createdAt;
    int id;
    boolean selected;

    protected ProblemData(Parcel in) {
        updatedAt = in.readString();
        profile = in.readString();
        name = in.readString();
        description = in.readString();
        createdAt = in.readString();
        id = in.readInt();
        selected = in.readByte() != 0;
    }

    public static final Creator<ProblemData> CREATOR = new Creator<ProblemData>() {
        @Override
        public ProblemData createFromParcel(Parcel in) {
            return new ProblemData(in);
        }

        @Override
        public ProblemData[] newArray(int size) {
            return new ProblemData[size];
        }
    };

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(updatedAt);
        parcel.writeString(profile);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(createdAt);
        parcel.writeInt(id);
        parcel.writeByte((byte) (selected ? 1 : 0));
    }
}
