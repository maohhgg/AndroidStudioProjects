package com.example.mao.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "MainActivity";
    private Button openURL;
    private TextView response;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if (msg.what == 1){
                response.setText((String)msg.obj);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        openURL = (Button) findViewById(R.id.open_url);
        response = (TextView) findViewById(R.id.response);

        openURL.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.open_url:
//                sendRequestWhitHttpURLConnection();
                sendRequestWhitHttpClient();
                break;
        }
    }

    private void sendRequestWhitHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL("https://www.baidu.com");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
//                    connection.setRequestMethod("POST");
//                    DataOutputStream DOS = new DataOutputStream(connection.getOutputStream());
//                    DOS.writeBytes("user=aaa&password=123123");
                    connection.setConnectTimeout(1000);
                    connection.setReadTimeout(1000);
                    InputStream in = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder RS = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        RS.append(line);
                    }
                    Log.i(TAG,RS.toString());
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = RS.toString();
                    handler.sendMessage(msg);
                    connection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    private void sendRequestWhitHttpClient(){
        HttpClient httpClient = new DefaultHttpClient();
    }
}
