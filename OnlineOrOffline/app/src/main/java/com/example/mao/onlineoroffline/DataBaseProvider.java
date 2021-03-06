package com.example.mao.onlineoroffline;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;


public class DataBaseProvider extends ContentProvider {
    private static final int USER_DIR = 0;
    private static final int USER_ITEM = 1;

    private static final String AUTHORITY = "com.example.mao.onlineoroffline.provider";

    private static UriMatcher uriMatcher;
    private MyDatabaseHelper helper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"user",USER_DIR);
        uriMatcher.addURI(AUTHORITY,"user/#",USER_ITEM);
    }

    @Override
    public boolean onCreate() {
        helper = new MyDatabaseHelper(getContext(),"users.db",null,3);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case USER_DIR:
                cursor = db.query("users",strings,s,strings1,null,null,s1);
                break;
            case USER_ITEM:
                String userId = uri.getPathSegments().get(1);
                cursor = db.query("users",strings,"id = ?",new String[]{userId},null,null,s1);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case USER_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.mao.onlineoroffline.provider.users";
            case USER_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.mao.onlineoroffline.provider.users";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)){
            case USER_DIR:
            case USER_ITEM:
                long newId = db.insert("users",null,contentValues);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/user/" + newId);
                break;
        }
        return uriReturn;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase db = helper.getReadableDatabase();
        int deleteRow = 0;
        switch (uriMatcher.match(uri)){
            case USER_DIR:
                deleteRow = db.delete("users",s,strings);
                break;
            case USER_ITEM:
                String userId = uri.getPathSegments().get(1);
                deleteRow = db.delete("users","id = ?",new String[]{userId});
                break;
        }
        return deleteRow;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        SQLiteDatabase db = helper.getReadableDatabase();
        int updataRow = 0;
        switch (uriMatcher.match(uri)) {
            case USER_DIR:
                updataRow = db.update("users", contentValues, s, strings);
                break;
            case USER_ITEM:
                String userId = uri.getPathSegments().get(1);
                updataRow = db.update("users", contentValues, "id = ?", new String[]{userId});
                break;

        }
        return updataRow;
    }
}
