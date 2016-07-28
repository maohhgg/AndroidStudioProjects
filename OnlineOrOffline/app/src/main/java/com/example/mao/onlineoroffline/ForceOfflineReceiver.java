package com.example.mao.onlineoroffline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

/**
 * Created by MAO on 2016/7/24.
 */
public class ForceOfflineReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(final Context context, Intent intent) {
        AlertDialog.Builder DB = new AlertDialog.Builder(context,R.style.AlertDialogCustom);
        DB.setTitle("下线通知");
        DB.setMessage("你在其他位置登陆，请重新登陆或退出");
        DB.setCancelable(false);
        DB.setPositiveButton("重新登陆", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityColltor.finishAll();
                Intent intent = new Intent(context,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        DB.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityColltor.finishAll();
            }
        });
//        DB.show();
        AlertDialog DBcard = DB.create();
        DBcard.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        DBcard.show();
    }
}
