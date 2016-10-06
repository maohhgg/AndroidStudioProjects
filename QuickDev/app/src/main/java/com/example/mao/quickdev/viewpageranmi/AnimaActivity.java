package com.example.mao.quickdev.viewpageranmi;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.example.mao.quickdev.R;
import com.example.mao.quickdev.itemindex.ContentAdapter;
import com.example.mao.quickdev.itemindex.view.IndexableListView;

import java.util.ArrayList;
import java.util.List;


public class AnimaActivity  extends AppCompatActivity {

    private List<String> mItem;
    private IndexableListView mListView;
    private List<Class> classItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mItem = new ArrayList<>();
        classItem = new ArrayList<>();

        mItem.add("Depth");
        mItem.add("ZoomOut");
        classItem.add(DepthActivity.class);
        classItem.add(ZoomOutActivity.class);


        ContentAdapter adapter = new ContentAdapter(this, android.R.layout.simple_list_item_1, mItem);
        mListView = (IndexableListView) findViewById(R.id.mian_list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(AnimaActivity.this,classItem.get(position)));
            }
        });
        mListView.setFastScrollEnabled(true);
    }
}
