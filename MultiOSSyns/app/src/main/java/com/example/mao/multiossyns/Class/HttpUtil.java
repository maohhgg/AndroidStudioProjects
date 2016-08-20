package com.example.mao.multiossyns.Class;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by maohh on 2016/8/19.
 */
public class HttpUtil {
    public static final int REQUEST_SUCCESS = 0;
    public static final int REQUEST_FAILED = 1;
    public static final String TAG = "HttpUtil";
    public static void Get(final String URLstr, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(URLstr);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(1000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder RS = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        RS.append(line);
                    }
                    Message msg = new Message();
                    msg.what = REQUEST_SUCCESS;
                    msg.obj = RS;
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

    public static void Post(final String URLstr, final Bundle bundle, final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i(TAG,"发送请求");
                    URL url = new URL(URLstr);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    DataOutputStream DOS = new DataOutputStream(connection.getOutputStream());
                    String temp = "";
                    for (String key: bundle.keySet()){
                        temp += key + "=" + bundle.getString(key) + "&";
                    }
                    Log.i(TAG,temp);
                    DOS.writeBytes(temp);
                    connection.setConnectTimeout(1000);
                    connection.setReadTimeout(1000);
                    InputStream in = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder RS = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        RS.append(line);
                    }
                    Message msg = new Message();
                    msg.what = REQUEST_SUCCESS;
                    msg.obj = RS.toString();
                    handler.sendMessage(msg);
                    Log.i(TAG,"handler接收数据");
                    connection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
