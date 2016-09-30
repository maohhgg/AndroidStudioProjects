package com.example.mao.quickdev.progressbar;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mao.quickdev.R;
import com.example.mao.quickdev.progressbar.view.HorizontalProgressBar;
import com.example.mao.quickdev.progressbar.view.RoundProgressBar;

public class ProgressActivity extends AppCompatActivity {
    private static final int MSG_UPDATE = 0x110;

    private HorizontalProgressBar mProgressbar;
    private RoundProgressBar mRoundBar;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress = mProgressbar.getProgress();
            mProgressbar.setProgress(++progress);
            if (progress >= 100){
                mHandler.removeMessages(MSG_UPDATE);
            }
            mHandler.sendEmptyMessageDelayed(MSG_UPDATE, 100);
        }
    };
    private Handler mRoundHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress = mRoundBar.getProgress();
            mRoundBar.setProgress(++progress);
            if (progress >= 100){
                mRoundHandler.removeMessages(MSG_UPDATE);
            }
            mRoundHandler.sendEmptyMessageDelayed(MSG_UPDATE, 100);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        mProgressbar = (HorizontalProgressBar) findViewById(R.id.id_progress);
        mRoundBar = (RoundProgressBar) findViewById(R.id.id_round);
        mHandler.sendEmptyMessage(MSG_UPDATE);
        mRoundHandler.sendEmptyMessage(MSG_UPDATE);
    }
}
