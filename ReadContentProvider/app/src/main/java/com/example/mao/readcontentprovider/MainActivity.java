package com.example.mao.readcontentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String curId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.insertser).setOnClickListener(this);
        findViewById(R.id.updateuser).setOnClickListener(this);
        findViewById(R.id.findUser).setOnClickListener(this);
        findViewById(R.id.deleteser).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.insertser:
                Uri IUri = Uri.parse("content://com.example.mao.onlineoroffline.provider/user");
                ContentValues values = new ContentValues();
                values.put("name","onther app");
                values.put("password","123123");
                values.put("time","2016-08-02 20:32:12");
                Uri newUri = getContentResolver().insert(IUri,values);
                Log.i("MainActivity"," : " + newUri);
                curId = newUri.getPathSegments().get(1);
                break;
            case R.id.updateuser:
                Uri UUri = Uri.parse("content://com.example.mao.onlineoroffline.provider/user/"+ curId);
                ContentValues UValues = new ContentValues();
                UValues.put("name","Other APP");
                UValues.put("password","pasword");
                UValues.put("time","2016-08-02 00:00:00");
                getContentResolver().update(UUri,UValues,null,null);
                break;
            case R.id.findUser:
                Uri FUri = Uri.parse("content://com.example.mao.onlineoroffline.provider/user");
                Cursor cursor = getContentResolver().query(FUri,new String[]{"id as _id","name","password","time"},"id = ?",new String[]{curId},null);
                if(cursor != null){
                    cursor.moveToFirst();
                    int id = cursor.getInt(cursor.getColumnIndex("_id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String password = cursor.getString(cursor.getColumnIndex("password"));
                    String time = cursor.getString(cursor.getColumnIndex("time"));
                    TextView tv = (TextView) findViewById(R.id.textView);
                    Log.i("MainActivity","id : " + id);
                    tv.setText("id : " + id + " name : " + name + " password : " + password + " time : " + time);
                }
                break;
            case R.id.deleteser:
                Uri DUri = Uri.parse("content://com.example.mao.onlineoroffline.provider/user/"+ curId);
                getContentResolver().delete(DUri,null,null);
                break;
        }
    }
}
