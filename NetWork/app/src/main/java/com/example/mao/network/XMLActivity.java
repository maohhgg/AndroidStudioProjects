package com.example.mao.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

public class XMLActivity extends AppCompatActivity implements View.OnClickListener {
    private Handler pullHandler = new Handler(){
        public void handleMessage(Message msg){
            if (msg.what == 1){
                String mes = parseXMLWithPull( (String)msg.obj);
                alertDiogShow(mes);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.xml_pull).setOnClickListener(this);
        findViewById(R.id.xml_sax).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.xml_pull:
                break;

            case R.id.xml_sax:
                break;
        }
    }

    public String parseXMLWithPull(String XmlData){
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(XmlData));
            int eventType = parser.getEventType();
            String response = "";

            while (eventType != parser.END_DOCUMENT) {

            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public void alertDiogShow(String message){

    }
}
