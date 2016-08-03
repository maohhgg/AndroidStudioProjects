package com.example.mao.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsMessage;
import android.view.WindowManager;

/**
 * Created by MAO on 2016/8/2.
 */
public class MessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[pdus.length];
        for (int i = 0;i < messages.length;i++){
            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
        }

        String fullMessage = "";
        // 获取发送的号码
        String address = messages[0].getOriginatingAddress();
        for (SmsMessage message : messages) {
            fullMessage += message.getMessageBody();
        }

        AlertDialog.Builder DB = new AlertDialog.Builder(context,R.style.AlertDialogCustom);
        DB.setTitle(address);
        DB.setMessage(fullMessage);
        DB.setCancelable(false);
        DB.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog DBcard = DB.create();
        DBcard.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        DBcard.show();
        abortBroadcast();
    }
}
