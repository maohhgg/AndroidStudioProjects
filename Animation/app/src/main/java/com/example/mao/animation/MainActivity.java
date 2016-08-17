package com.example.mao.animation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mao.animation.Adapter.MasonryAdapter;
import com.example.mao.animation.other.Product;
import com.example.mao.animation.other.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements MasonryAdapter.MyItemClickListener {
    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        MasonryAdapter adapter = new MasonryAdapter(productList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        SpacesItemDecoration decoration = new SpacesItemDecoration(-16);
        recyclerView.addItemDecoration(decoration);
    }



    @Override
    public void onItemClick(View view, Product product) {
        Log.i("AAAA"," "+product.getBigImg());
        Intent intent = new Intent("com.example.mao.animation.DetailActivity");
        Bundle bundle = new Bundle();
        bundle.putString("title",product.getTitle());
        bundle.putString("img",product.getBigImg());
        intent.putExtras(bundle);
        ActivityOptions options = ActivityOptions
            .makeSceneTransitionAnimation(this,
                Pair.create(view.findViewById(R.id.masonry_item_img), "img"),
                Pair.create(view.findViewById(R.id.masonry_item_title), "title"));
        startActivity(intent,options.toBundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_scroll:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
