package com.example.mao.example;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn,btn0,btn1,delete;
    private Button btnsend,btnlogout;
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MainActivity",this.toString());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);

        btn0 = (Button) findViewById(R.id.btn0);
        btn0.setOnClickListener(this);

        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);

        btnsend = (Button) findViewById(R.id.btnsend);
        btnsend.setOnClickListener(this);

        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);

        btnlogout = (Button) findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(this);

        float x = getResources().getDisplayMetrics().xdpi;
        float y = getResources().getDisplayMetrics().ydpi;
        Log.i("MainActivity","xdpi is " + x);
        Log.i("MainActivity","ydpi is " + y);

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
               // Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:10086"));
//                startActivity(intent);
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                break;
            case R.id.btn0:
                startActivity(new Intent(MainActivity.this,Main3Activity.class));
                break;
            case R.id.btn1:
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
                break;
            case R.id.delete:
                if(btn.getVisibility() == View.VISIBLE) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                    ad.setTitle("警告");
                    ad.setMessage("确认要删除？");
                    ad.setCancelable(false);
                    ad.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            btn.setVisibility(View.GONE);
                            btn0.setVisibility(View.GONE);
                            btn1.setVisibility(View.GONE);
                            btnsend.setVisibility(View.GONE);
                            delete.setText("恢复之前状态");
                        }
                    });
                    ad.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    ad.show();
                } else {
                    btn.setVisibility(View.VISIBLE);
                    btn0.setVisibility(View.VISIBLE);
                    btn1.setVisibility(View.VISIBLE);
                    btnsend.setVisibility(View.VISIBLE);
                    delete.setText("删除上面的按钮");
                }
                break;
            case R.id.btnsend:
                Log.i("MainActivity","sendBroadcast");
                sendBroadcast(new Intent("com.example.mao.example.MY_BROADCAST"));
                break;
            case R.id.btnlogout:
                sendBroadcast(new Intent("com.example.mao.onlineoroffline.FORCE_OFFLINE"));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
}
