package com.example.mao.multiossyns.Service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.example.mao.multiossyns.Receiver.BatteryReceiver;
import com.example.mao.multiossyns.Receiver.SmsReceiver;

public class SynchService extends Service {
    public final static String TAG = "SynchService";
    private SmsReceiver smsReceiver;
    private BatteryReceiver batteryReceiver;


    public SynchService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK); //注册IntentFilter
        filter.setPriority(Integer.MAX_VALUE); //设置级别
        smsReceiver = new SmsReceiver();//本地服务
        batteryReceiver = new BatteryReceiver();
        registerReceiver(smsReceiver, filter);//注册广播
        registerReceiver(batteryReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        Log.i(TAG,"注册广播");
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.i(TAG,"启动服务");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceiver);
        unregisterReceiver(batteryReceiver);
        Log.i(TAG,"关闭服务 注销广播");
    }
}
