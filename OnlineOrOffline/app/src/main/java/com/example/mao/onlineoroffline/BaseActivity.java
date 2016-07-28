package com.example.mao.onlineoroffline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by MAO on 2016/7/24.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityColltor.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityColltor.addActivity(this);
    }
}
