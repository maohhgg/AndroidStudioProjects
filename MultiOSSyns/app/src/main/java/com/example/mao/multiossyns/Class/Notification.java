package com.example.mao.multiossyns.Class;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


/**
 * Created by maohh on 2016/8/19.
 */
public class Notification {
    private static final String  BASE_URL = "http://222.222.222.200/";
    private static final String  TAG = "Notification";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HttpUtil.REQUEST_SUCCESS:
                    Log.i(TAG,msg.obj.toString());
                    break;
                case HttpUtil.REQUEST_FAILED:
                    break;
            }
        }
    };

    public void sendNotification(Bundle data){
        HttpUtil.Post(BASE_URL,data,handler);
    }
}
