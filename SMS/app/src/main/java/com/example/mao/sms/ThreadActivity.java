package com.example.mao.sms;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ThreadActivity extends AppCompatActivity {
    private TextView tv;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            if(msg.what == 1){
                tv.setText("Life Is Wonderful");
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
                break;
            case R.id.action_unbind_server:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
