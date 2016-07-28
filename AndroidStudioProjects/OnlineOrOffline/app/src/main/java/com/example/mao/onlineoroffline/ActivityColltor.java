package com.example.mao.onlineoroffline;

import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAO on 2016/7/24.
 */
public class ActivityColltor {
    public static List<AppCompatActivity> activities = new ArrayList<AppCompatActivity>();

    public static void addActivity(AppCompatActivity activity){
        activities.add(activity);
    }

    public static void removeActivity(AppCompatActivity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for (AppCompatActivity activity :activities) {
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
