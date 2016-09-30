package com.example.mao.quickdev.itemindex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mao.quickdev.R;
import com.example.mao.quickdev.itemindex.view.IndexableListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemIndexActivity extends AppCompatActivity {
    private List<String> mItem;
    private IndexableListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_index);
        mItem = new ArrayList<>();
        mItem.add("Multi-window Support");
        mItem.add("Notifications");
        mItem.add("JIT/AOT Compilation");
        mItem.add("Quick Path to App Install");
        mItem.add("Doze on the Go");
        mItem.add("Background Optimizations");
        mItem.add("SurfaceView");
        mItem.add("Data Saver");
        mItem.add("Vulkan API");
        mItem.add("Quick Settings Tile API");
        mItem.add("Number Blocking");
        mItem.add("Call Screening");
        mItem.add("Locales and Languages");
        mItem.add("New Emojis");
        mItem.add("ICU4J APIs in Android");
        mItem.add("WebView");
        mItem.add("OpenGL ES 3.2 API");
        mItem.add("Android TV Recording");
        mItem.add("Android for Work");
        mItem.add("Accessibility");
        mItem.add("Direct Boot");
        mItem.add("Key Attestation");
        mItem.add("Network Security Config");
        mItem.add("Default Trusted CA");
        mItem.add("APK Signature Scheme v2");
        mItem.add("Scoped Directory Access");
        mItem.add("Keyboard Shortcuts Helper");
        mItem.add("Custom Pointer API");
        mItem.add("Sustained Performance API");
        mItem.add("VR Support");
        mItem.add("Print Service Enhancements");
        mItem.add("Virtual Files");
        mItem.add("Frame Metrics API");

        Collections.sort(mItem);

        ContentAdapter adapter = new ContentAdapter(this,android.R.layout.simple_list_item_1,mItem);
        mListView = (IndexableListView) findViewById(R.id.list_view);
        mListView.setAdapter(adapter);
        mListView.setFastScrollEnabled(true);

    }


}
