package com.example.mao.citydata;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.mao.citydata.Adapter.RecyclerAdapter;
import com.example.mao.citydata.DB.CityDB;
import com.example.mao.citydata.DB.DBManager;
import com.example.mao.citydata.DB.Model.City;
import com.example.mao.citydata.Utils.Util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<City> cityList = new ArrayList<>();
    private SQLiteDatabase database = DBManager.getInstance().getDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.open_file).setOnClickListener(this);
//        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
//        adapter = new RecyclerAdapter(this,cityList);
//        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.open_file:
                Intent intent = new Intent();
                intent.setType("text/plain");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                InputStream is = cr.openInputStream(uri);
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader buffreader = new BufferedReader(isr);
                String line;
                while (( line = buffreader.readLine()) != null) {
                    List<City> list = Util.lineSave(cityList,line);
                    if (list != null){
                        cityList = list;
                    }
                }
                isr.close();
                is.close();
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
