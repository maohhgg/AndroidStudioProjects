package com.example.mao.quickdev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.mao.quickdev.itemindex.ContentAdapter;
import com.example.mao.quickdev.itemindex.ItemIndexActivity;
import com.example.mao.quickdev.itemindex.view.IndexableListView;
import com.example.mao.quickdev.progressbar.ProgressActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> mItem;
    private IndexableListView mListView;
    private List<Class> classItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mItem = new ArrayList<>();
        classItem = new ArrayList<>();
        classItem.add(ItemIndexActivity.class);
        mItem.add("ItemIndex");
        classItem.add(ProgressActivity.class);
        mItem.add("ProgressBar");
        Collections.sort(mItem);

        ContentAdapter adapter = new ContentAdapter(this, android.R.layout.simple_list_item_1, mItem);
        mListView = (IndexableListView) findViewById(R.id.mian_list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this,classItem.get(position)));
            }
        });
        mListView.setFastScrollEnabled(true);
    }
}
