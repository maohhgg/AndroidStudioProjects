package com.example.mao.multiossyns.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mao.multiossyns.Class.Notification;

public class BatteryReceiver extends BroadcastReceiver {
    public BatteryReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BATTERY_CHANGED")){
            Bundle bundle = intent.getExtras();
            Bundle returnBundle = new Bundle();
            returnBundle.putString("Receive","Battery");
            returnBundle.putString("level",""+bundle.getInt("level"));
            Notification.sendNotification(returnBundle);
        }

    }
}
