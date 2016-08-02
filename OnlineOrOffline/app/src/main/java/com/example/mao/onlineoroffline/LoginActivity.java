package com.example.mao.onlineoroffline;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by MAO on 2016/7/24.
 */
public class LoginActivity extends BaseActivity {
    private EditText username,passwd;
    private CheckBox check;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        passwd = (EditText) findViewById(R.id.passwd);
        check = (CheckBox) findViewById(R.id.check);

        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isRemember = pref.getBoolean("remember",false);

        if(isRemember){
            username.setText(pref.getString("username",""));
            passwd.setText(pref.getString("password",""));
            check.setChecked(true);
        }

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un = username.getText().toString();
                String pw = passwd.getText().toString();
                SharedPreferences.Editor editor = pref.edit();
                if(check.isChecked()){
                    editor.putBoolean("remember",true);
                    editor.putString("username",un);
                    editor.putString("password",pw);
                    editor.commit();
                } else {
                    editor.clear();
                }
                Log.i("MainActivity","username is " + un + " Password is " + pw);
                MyDatabaseHelper sql = new MyDatabaseHelper(LoginActivity.this,"users.db",null,3);
                SQLiteDatabase DB = sql.getReadableDatabase();
                Cursor user = DB.query("users", new String[]{"id", "name", "password"}, "name=?", new String[]{un}, null, null, null, null);
                Log.i("MainActivity", String.valueOf(user.getCount()));
                if(user.getCount() == 0){
                    Toast.makeText(LoginActivity.this,"用户名不存在！",Toast.LENGTH_LONG).show();
                } else {
                    user.moveToFirst();
                    if(pw.equals(user.getString(user.getColumnIndex("password")))){
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        simpleNotify();
                    } else {
                        Toast.makeText(LoginActivity.this,"密码错误！",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    private void simpleNotify(){
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //Ticker是状态栏显示的提示
        builder.setTicker("登录成功");
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle("状态");
        //第二行内容 通常是通知正文
        builder.setContentText("你已经登录了");
        //第三行内容 通常是内容摘要什么的 在低版本机器上不一定显示
//        builder.setSubText("这里显示的是通知第三行内容！");
        //ContentInfo 在通知的右侧 时间的下面 用来展示一些其他信息
        builder.setContentInfo("2");
        //number设计用来显示同种通知的数量和ContentInfo的位置一样，如果设置了ContentInfo则number会被隐藏
        builder.setNumber(1);
        //可以点击通知栏的删除按钮删除
        builder.setAutoCancel(true);
        //系统状态栏显示的小图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //下拉显示的大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.login));
        //通知默认的声音 震动 呼吸灯
        builder.setDefaults(NotificationCompat.DEFAULT_LIGHTS);
        Notification notification = builder.build();
        manager.notify(1,notification);
    }
}
