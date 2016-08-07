package com.example.mao.sms;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


}
