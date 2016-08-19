package com.example.mao.multiossyns.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.mao.multiossyns.Class.Notification;

public class SmsReceiver extends BroadcastReceiver {
    public final static String TAG = "SmsReceiver";

    public SmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Log.i(TAG,"收到了短信");
            Bundle returnBundle = new Bundle();
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                Object[] puds = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[puds.length];
                for (int i = 0; i < puds.length; i++ ) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) puds[i]);
                }

                for (SmsMessage message : messages){
                    returnBundle.putString("Receive","SMS");
                    returnBundle.putString("from",message.getDisplayOriginatingAddress());
                    returnBundle.putString("content",message.getDisplayMessageBody());
                }
                Notification notification = new Notification();
                notification.sendNotification(returnBundle);
            }
        }
    }

}
