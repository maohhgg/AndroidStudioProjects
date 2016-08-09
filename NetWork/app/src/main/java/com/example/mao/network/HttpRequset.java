package com.example.mao.network;

import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by maohh on 2016/8/9.
 */
public class HttpRequset {
    public static void sendRequestWhitHttpURLConnection(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL("http://222.222.222.200");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
//                    connection.setRequestMethod("POST");
//                    DataOutputStream DOS = new DataOutputStream(connection.getOutputStream());
//                    DOS.writeBytes("user=aaa&password=passwd");
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


    public static void sendRequestWhitHttpClient(final Handler handler) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient httpClient = new DefaultHttpClient();

//                HttpPost httpPost = new HttpPost("https://www.baidu.com");
//                List<NameValuePair> params = new ArrayList<NameValuePair>();
//
//                params.add(new BasicNameValuePair("user","mao"));
//                params.add(new BasicNameValuePair("password","password"));
//                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
//                httpPost.setEntity(entity);
//                HttpResponse httpResponse =  httpClient.execute(httpPost);

                    HttpGet httpGet = new HttpGet("http://222.222.222.200");
                    HttpResponse httpResponse  = httpClient.execute(httpGet);
                    if(httpResponse.getStatusLine().getStatusCode() == 200){
                        HttpEntity httpEntity = httpResponse.getEntity();
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = EntityUtils.toString(httpEntity,"UTF-8");
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
