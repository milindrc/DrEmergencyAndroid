package com.matrixdev.dremergency;

import android.content.Context;

/**
 * Created by Milind on 7/23/2018.
 */

public class Application extends android.app.Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
