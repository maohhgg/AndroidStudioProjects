package com.example.mao.animation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;

import com.example.mao.animation.Adapter.MasonryAdapter;
import com.example.mao.animation.Other.Product;
import com.example.mao.animation.Other.SpacesItemDecoration;

public class DataActivity extends AppCompatActivity implements MasonryAdapter.MyItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        MasonryAdapter adapter = new MasonryAdapter();
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        SpacesItemDecoration decoration = new SpacesItemDecoration(-16);
        recyclerView.addItemDecoration(decoration);
    }

    @Override
    public void onItemClick(View view, Product product) {

        Intent intent = new Intent("com.example.mao.animation.DetailTabActivity");
        Bundle bundle = new Bundle();
        bundle.putString("img",product.getBigImg());
        intent.putExtras(bundle);
        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(this,
                        Pair.create(view.findViewById(R.id.masonry_item_img), "img"));
        startActivity(intent,options.toBundle());
        startActivity(intent);
    }
}
