package com.example.mao.network;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by maohh on 2016/8/9.
 */
public class ContentHandler extends DefaultHandler {
    private String nodeName;
    private String response = "";

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        nodeName = localName;
        if ("item".equals(nodeName)){
            response += "--------------------------------\n";
        }
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        response += nodeName + "----" + String.valueOf(ch).substring(start, length) + "\n";
    }

    public String getResponse() {
        return response;
    }
}
