package com.example.mao.onlineoroffline;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private MyDatabaseHelper sql;
    private RecyclerView recyclerView;
    private SQLiteDatabase DB;
    private RecycleAdapter recycleAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sql = new MyDatabaseHelper(this,"users.db",null,3);
        DB = sql.getWritableDatabase();

        Cursor cursor = DB.query("users",null,null,null,null,null,null,null);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recycleAdapter = new RecycleAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recycleAdapter.setCursor(cursor);
        recyclerView.setAdapter(recycleAdapter);


        findViewById(R.id.logout).setOnClickListener(this);

        findViewById(R.id.insert_SQL).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logout:
                sendBroadcast(new Intent("com.example.mao.onlineoroffline.FORCE_OFFLINE"));
                break;
            case R.id.insert_SQL:
                ContentValues values = new ContentValues();
                EditText inputUserName = (EditText) findViewById(R.id.input_user_name);
                EditText inputUserPasswd = (EditText) findViewById(R.id.input_user_passwd);
                values.put("name",inputUserName.getText().toString());
                values.put("password",inputUserPasswd.getText().toString());
                Calendar calendar = Calendar.getInstance();
                values.put("time",new SimpleDateFormat("yyyy-MM-dd H:m:s").format(calendar.getTime()));
                DB.insert("users",null,values);
                break;
        }
    }
}

