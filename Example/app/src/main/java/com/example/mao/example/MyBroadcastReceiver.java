package com.example.mao.example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by MAO on 2016/7/24.
 */
public class MyBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"receiver in MyBroadcastReceiver",Toast.LENGTH_SHORT).show();
    }
}
