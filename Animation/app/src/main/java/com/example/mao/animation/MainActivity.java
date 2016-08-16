package com.example.mao.animation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pushProduct();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        MasonryAdapter adapter = new MasonryAdapter(productList);
        recyclerView.setAdapter(adapter);

        SpacesItemDecoration decoration = new SpacesItemDecoration(-16);
        recyclerView.addItemDecoration(decoration);
    }

    private void pushProduct(){
        String img = "";
        String bigimg = "";
        for (int i = 0;i < 10 ;i++){
            if (i < 10){
                img = "img_0" + i + "_s";
                bigimg = "img_0" + i;
            } else {
                img = "img_" + i + "_s";
                bigimg = "img_" + i ;
            }
            productList.add(
                    new Product(getResources().getIdentifier(img,"drawable","com.example.mao.animation"),
                            getResources().getIdentifier(bigimg,"drawable","com.example.mao.animation") ,bigimg));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_test) {
            startActivity(new Intent(MainActivity.this,TestActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
