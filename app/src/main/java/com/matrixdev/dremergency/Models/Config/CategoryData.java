package com.matrixdev.dremergency.Models.Config;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Milind on 7/13/2018.
 */

public class CategoryData {
    @SerializedName("updated_at")
    String updatedAt;
    String name;
    @SerializedName("created_at")
    String createdAt;
    int id;

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public String toString() {
        return name;
    }
    public static CategoryData findCardByName(ArrayList<CategoryData> categories, String key) {
        CategoryData data = null;
        for (CategoryData t : categories) {
            if (t.getName().equals(key)) {
                data = t;
            }
        }
        return data;
    }

}
