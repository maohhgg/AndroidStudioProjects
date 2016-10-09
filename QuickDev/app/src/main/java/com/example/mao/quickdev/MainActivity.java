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
import com.example.mao.quickdev.viewpage.ViewPageActivity;
import com.example.mao.quickdev.viewpageranmi.AnimaActivity;

import java.util.ArrayList;
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

        mItem.add("ItemIndex");
        mItem.add("ProgressBar");
        mItem.add("Custom ViewPager");
        mItem.add("ViewPager Animator");
        classItem.add(ItemIndexActivity.class);
        classItem.add(ProgressActivity.class);
        classItem.add(ViewPageActivity.class);
        classItem.add(AnimaActivity.class);


        ContentAdapter adapter = new ContentAdapter(this, android.R.layout.simple_list_item_1, mItem);
        mListView = (IndexableListView) findViewById(R.id.mian_list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this, classItem.get(position)));
            }
        });
        mListView.setFastScrollEnabled(true);
    }
}
