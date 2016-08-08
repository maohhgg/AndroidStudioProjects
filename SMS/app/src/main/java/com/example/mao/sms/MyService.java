package com.example.mao.sms;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyService extends Service {
    private final static String TAG = "MainActivity";
    private String i = "";
    private boolean isRun = true;
    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder{
        public void startDown(){
            Log.i(TAG,"开始下载");
        }

        public String getProgress(){
            Log.i(TAG,"当前下载进程" + i);
            return i;
        }
    }

    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"绑定服务");
        return mBinder;
    }

    @Override
    public void onCreate() {
        Log.i(TAG,"服务创建");
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //Ticker是状态栏显示的提示
        //builder.setTicker("登录成功");
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle("状态");
        //第二行内容 通常是通知正文
        builder.setContentText("后台运行中");
        //第三行内容 通常是内容摘要什么的 在低版本机器上不一定显示
        //builder.setSubText("这里显示的是通知第三行内容！");
        //ContentInfo 在通知的右侧 时间的下面 用来展示一些其他信息
        //builder.setContentInfo("2");
        //number设计用来显示同种通知的数量和ContentInfo的位置一样，如果设置了ContentInfo则number会被隐藏
        //builder.setNumber(1);
        //可以点击通知栏的删除按钮删除
        builder.setAutoCancel(true);
        //系统状态栏显示的小图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //下拉显示的大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        //通知默认的声音 震动 呼吸灯
        //builder.setDefaults(NotificationCompat.DEFAULT_LIGHTS);
        Notification notification = builder.build();
        manager.notify(1,notification);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"Start MyServer");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (isRun){
//                    Calendar calendar = Calendar.getInstance();
//                    Date date = calendar.getTime();
//                    i = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
//                    Log.i(TAG,"Thread :" + i);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,"date: " + new Date().toString());
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int time = 10 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + time;
        Intent i = new Intent(this,AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"服务销毁");
        super.onDestroy();
        this.isRun = false;
    }
}
