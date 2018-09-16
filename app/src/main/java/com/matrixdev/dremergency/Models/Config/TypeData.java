package com.matrixdev.dremergency.Models.Config;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Milind on 7/13/2018.
 */

public class TypeData {

    public static final String KEY_TEXT = "text";
    public static final String KEY_COMPANY = "company";
    public static final String KEY_LANDLINE = "landline";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_WHATSAPP = "whatsapp";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_FACEBOOK = "facebook";
    public static final String KEY_LINKEDIN = "linked_in";
    public static final String KEY_INSTAGRAM = "instagram";
    public static final String KEY_SNAPCHAT = "snapchat";
    public static final String KEY_WEBSITE = "website";
    public static final String KEY_FILE = "file";
    public static final String KEY_GPS = "gps";

    @SerializedName("updated_at")
    String updatedAt;
    String name;
    @SerializedName("created_at")
    String createdAt;
    int id;
    String key;

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return name;
    }

    public static int findTypeById(ArrayList<TypeData> types, int id) {
        TypeData data = null;
        for (TypeData t : types) {
            if (t.getId() == id) {
                data = t;
            }
        }
        return types.indexOf(data);
    }
    public static TypeData findTypeByKey(ArrayList<TypeData> types, String key) {
        TypeData data = null;
        for (TypeData t : types) {
            if (t.getKey().equals(key)) {
                data = t;
            }
        }
        return data;
    }
}
