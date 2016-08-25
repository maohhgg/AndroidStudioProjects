package com.example.mao.citydata;

import android.app.Application;
import android.content.Context;

/**
 * Created by maohh on 2016/8/22.
 */
public class BaseApplication extends Application {
    public static Context mAppContext = null;

    public static Context getmAppContext() {
        return mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
    }
}
