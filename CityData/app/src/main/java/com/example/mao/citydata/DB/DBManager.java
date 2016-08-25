package com.example.mao.citydata.DB;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.mao.citydata.BaseApplication;
import com.example.mao.citydata.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by maohh on 2016/8/24.
 */
public class DBManager {
    private static String TAG = DBManager.class.getSimpleName();
    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "citydata.db"; //数据库名字
    public static final String PACKAGE_NAME = "com.example.mao.citydata";
    public static final String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME;
    private SQLiteDatabase database;
    private static DBManager db;

    public static DBManager getInstance() {
        if (db == null){
            db = new DBManager();
        }
        return db;
    }
    private DBManager(){
        this.database = this.setDatabase();
    }

    private SQLiteDatabase setDatabase() {
        String dbFile = DB_PATH + "/" + DB_NAME;
        try {
            if (!(new File(dbFile).exists())) {
                InputStream is = BaseApplication.getmAppContext().getResources().openRawResource(R.raw.citydata);
                FileOutputStream fos = new FileOutputStream(dbFile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SQLiteDatabase.openOrCreateDatabase(dbFile, null);
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}
