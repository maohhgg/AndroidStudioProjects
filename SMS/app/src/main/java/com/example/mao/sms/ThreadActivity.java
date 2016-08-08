package com.example.mao.sms;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ThreadActivity extends AppCompatActivity {
    private TextView tv;
    private MyService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (MyService.DownloadBinder) iBinder;
            downloadBinder.startDown();

            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("date",downloadBinder.getProgress());
            msg.setData(bundle);
            msg.what = 1;
            handler.sendMessage(msg);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            if(msg.what == 1){
                Bundle bundle = msg.getData();
                tv.setText(bundle.getString("date"));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ThreadToolbar);
        setSupportActionBar(toolbar);


        tv = (TextView) findViewById(R.id.beChangeView);
        findViewById(R.id.changeText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("date","HA HA HA HA");
                        msg.setData(bundle);
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }
                }).start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thread, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.action_start_server:
                startService(new Intent(this,MyService.class));
                break;
            case R.id.action_stop_server:
                stopService(new Intent(this,MyService.class));
                break;
            case R.id.action_bind_server:
                bindService(new Intent(this,MyService.class),connection,BIND_AUTO_CREATE);
                break;
            case R.id.action_unbind_server:
                unbindService(connection);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
