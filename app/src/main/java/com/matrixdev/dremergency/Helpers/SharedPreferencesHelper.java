package com.matrixdev.dremergency.Helpers;


import android.content.Context;

import com.google.gson.Gson;
import com.matrixdev.dremergency.Application;
import com.matrixdev.dremergency.Models.Authentication.LoginData;
import com.matrixdev.dremergency.Models.Config.ConfigData;

import java.util.ArrayList;


public class SharedPreferencesHelper {
    public static final String LOGIN_DATA = "login_data";
    private static final String DEVICE_DATA = "device";
    private static final String FILE_NAME = "bvicam_pref";
    private static final String ITEM_DATA = "item_data";
    private static final String PROFILE = "profile";
    private static final String CARD = "card";
    private static final String LOGO = "logo";
    private static final String CONFIG = "config";


    public static void saveDeviceData(String deviceToken) {
        Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(DEVICE_DATA, deviceToken).commit();
    }

    public static String getDeviceData() {
        String stringDeviceData = Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(DEVICE_DATA, "");
        if (stringDeviceData.equals(""))
            return null;
        return stringDeviceData;
    }

    public static void saveLoginData(LoginData checkinData) {
        Gson gson = new Gson();
        String stringLoginData = gson.toJson(checkinData);
        Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(LOGIN_DATA, stringLoginData).commit();
    }

    public static LoginData getLoginData() {
        Gson gson = new Gson();
        String stringLoginData = Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(LOGIN_DATA, "");
        if (stringLoginData.equals(""))
            return null;
        return gson.fromJson(stringLoginData, LoginData.class);
    }

    public static void saveConfig(ConfigData config) {
        Gson gson = new Gson();
        String stringConfig = gson.toJson(config);
        Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(CONFIG, stringConfig).commit();
    }

    public static ConfigData getConfigData() {
        Gson gson = new Gson();
        String stringConfigData = Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(CONFIG, "");
        if (stringConfigData.equals(""))
            return null;
        return gson.fromJson(stringConfigData, ConfigData.class);
    }


//    public static void saveProfile(String profile,CardData cardData) {
//        Gson gson = new Gson();
//        Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(PROFILE+cardData.getTimestamp(), profile).commit();
//    }
//
//    public static String getProfile(CardData cardData) {
//        String image = Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(PROFILE+cardData.getTimestamp(), "");
//        return image;
//    }


//    public static void addCardItem(CardItem item,CardData cardData) {
//        String stringCartItems = Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(ITEM_DATA+cardData.getTimestamp(), "");
//        if (stringCartItems.equals("")) {
//            ArrayList<CardItem> items = new ArrayList<>();
//            items.add(item);
//
//            Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(ITEM_DATA+cardData.getTimestamp(), (new Gson().toJson(items))).commit();
//        } else {
//            ArrayList<CardItem> items = new ArrayList<CardItem>();
//            items = new Gson().fromJson(stringCartItems, new TypeToken<ArrayList<CardItem>>() {
//            }.getType());
//
//            items.add(item);
//
//            Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(ITEM_DATA+cardData.getTimestamp(), (new Gson().toJson(items))).apply();
//        }
//    }
//
//    public static void addCardItem(CardItem item, int pos,CardData cardData) {
//        String stringCartItems = Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(ITEM_DATA+cardData.getTimestamp(), "");
//        if (stringCartItems.equals("")) {
//            ArrayList<CardItem> items = new ArrayList<>();
//            items.add(item);
//
//            Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(ITEM_DATA+cardData.getTimestamp(), (new Gson().toJson(items))).commit();
//        } else {
//            ArrayList<CardItem> items = new ArrayList<CardItem>();
//            items = new Gson().fromJson(stringCartItems, new TypeToken<ArrayList<CardItem>>() {
//            }.getType());
//
//            items.add(pos, item);
//
//            Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(ITEM_DATA+cardData.getTimestamp(), (new Gson().toJson(items))).apply();
//        }
//    }
//
//    public static void removeCardItem(CardItem item,CardData cardData) {
//        String stringCartItems = Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(ITEM_DATA+cardData.getTimestamp(), "");
//        if (!stringCartItems.equals("")) {
//            ArrayList<CardItem> items = new ArrayList<CardItem>();
//            items = new Gson().fromJson(stringCartItems, new TypeToken<ArrayList<CardItem>>() {
//            }.getType());
//
//            items.remove(item);
//
//            Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(ITEM_DATA+cardData.getTimestamp(), (new Gson().toJson(items))).apply();
//        }
//    }
//
//    public static ArrayList<CardItem> getCardItems(CardData cardData) {
//        String stringCartItems = Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(ITEM_DATA+cardData.getTimestamp(), "");
//        if (!stringCartItems.equals("")) {
//            ArrayList<CardItem> items = new ArrayList<CardItem>();
//            items = new Gson().fromJson(stringCartItems, new TypeToken<ArrayList<CardItem>>() {
//            }.getType());
//            return items;
//        }
//        return null;
//    }
//
//    public static ArrayList<CardData> getCards() {
//        String stringCartItems = Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(CARD, "");
//        if (!stringCartItems.equals("")) {
//            ArrayList<CardData> items = new ArrayList<CardData>();
//            items = new Gson().fromJson(stringCartItems, new TypeToken<ArrayList<CardData>>() {
//            }.getType());
//            return items;
//        }
//        return null;
//    }
//
//
//    public static void addCard(CardData item) {
//        String stringCartItems = Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(CARD, "");
//        if (stringCartItems.equals("")) {
//            ArrayList<CardData> items = new ArrayList<>();
//            items.add(item);
//
//            Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(CARD, (new Gson().toJson(items))).commit();
//        } else {
//            ArrayList<CardData> items = new ArrayList<CardData>();
//            items = new Gson().fromJson(stringCartItems, new TypeToken<ArrayList<CardData>>() {
//            }.getType());
//
//            items.add(item);
//
//            Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(CARD, (new Gson().toJson(items))).apply();
//        }
//    }
//
//    public static void removeCardItem(CardData item) {
//        String stringCartItems = Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(CARD, "");
//        if (!stringCartItems.equals("")) {
//            ArrayList<CardData> items = new ArrayList<CardData>();
//            items = new Gson().fromJson(stringCartItems, new TypeToken<ArrayList<CardData>>() {
//            }.getType());
//
//            items.remove(item);
//
//            Application.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(CARD, (new Gson().toJson(items))).apply();
//        }
//    }




}
