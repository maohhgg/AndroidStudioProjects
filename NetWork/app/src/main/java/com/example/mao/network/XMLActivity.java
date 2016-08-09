package com.example.mao.network;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Xml;
import android.view.View;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

public class XMLActivity extends AppCompatActivity implements View.OnClickListener {


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
                HttpRequset.sendRequestWhitHttpURLConnection("http://222.222.222.200/data/?xml",new Handler(){
                    public void handleMessage(Message msg){
                    if (msg.what == 1){
                        String mes = parseXMLWithPull( (String)msg.obj);
                        HttpRequset.AlertDialogShow(XMLActivity.this,mes);
                    }
                }});
                break;

            case R.id.xml_sax:
                HttpRequset.sendRequestWhitHttpURLConnection("http://222.222.222.200/data/?xml",new Handler(){
                    public void handleMessage(Message msg){
                    if (msg.what == 1){
                        String mes = parseXMLWithSAX( (String)msg.obj);
                        HttpRequset.AlertDialogShow(XMLActivity.this,mes);
                    }
                }});
                break;
        }
    }

    public String parseXMLWithPull(String XmlData){
        String response = "";
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(XmlData));

            int eventType = parser.getEventType();  //获取当前行的标识  END_DOCUMENT START_DOCUMENT START_TAG END_TAG TEXT

            // 读取每一行 标签内容单独为一行
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();   // 获取标签名
                if(name != null){   //  排除当前行的标识为TEXT的标签名
                    if("item".equals(name) && eventType == XmlPullParser.START_TAG){  // 判断是否开始了每一类
                        response += "--------------------------\n"; // 在每一类前（每一个item前） 打上标记
                    } else if(!"item".equals(name) && eventType == XmlPullParser.START_TAG){  // 判断是否是实际内容
                        response += parser.getName() +  " --- " + parser.nextText() + "\n";   // 拼接实际内容
                    }
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String parseXMLWithSAX(String XmlData){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader reader = factory.newSAXParser().getXMLReader();
            ContentHandler handler = new ContentHandler();
            reader.setContentHandler(handler);
            reader.parse(new InputSource(new StringReader(XmlData)));
            return handler.getResponse();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
