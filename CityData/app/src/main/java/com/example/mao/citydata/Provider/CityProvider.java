package com.example.mao.citydata.Provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.mao.citydata.DB.DBManager;

public class CityProvider extends ContentProvider {
    private SQLiteDatabase database;
    private static final UriMatcher MATCHER = new UriMatcher(
            UriMatcher.NO_MATCH);
    private static final int CITYS = 1;
    private static final int CITY = 2;
    private static final int PROINCES = 3;
    private static final int PROINCE = 4;
    static {
        MATCHER.addURI("com.example.mao.citydata.cityProvider", "city", CITYS);
        MATCHER.addURI("com.example.mao.citydata.cityProvider", "city/#", CITY);
        MATCHER.addURI("com.example.mao.citydata.cityProvider", "province", PROINCES);
        MATCHER.addURI("com.example.mao.citydata.cityProvider", "province/#", PROINCE);
    }

    public CityProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (MATCHER.match(uri)) {
            case CITYS:
                return database.delete("city_data", selection, selectionArgs);

            case CITY:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = database.delete("city_data", where, selectionArgs);
                return count;

            case PROINCES:
                return database.delete("province",  selection, selectionArgs);

            case PROINCE:
                long p_id = ContentUris.parseId(uri);
                String p_where = "_id=" + p_id;
                if (selection != null && !"".equals(selection)) {
                    p_where = selection + " and " + p_where;
                }
                count = database.delete("province",  p_where, selectionArgs);
                return count;

            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }


    }

    @Override
    public String getType(Uri uri) {
        switch (MATCHER.match(uri)) {
            case CITYS:
                return "vnd.android.cursor.dir/city";

            case CITY:
                return "vnd.android.cursor.item/city";

            case PROINCES:
                return "vnd.android.cursor.dir/province";

            case PROINCE:
                return "vnd.android.cursor.item/province";

            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (MATCHER.match(uri)) {
            case CITYS:
                long cityID = database.insert("city_data", "CityID", values);
                Uri cityInsertUri = ContentUris.withAppendedId(uri, cityID);
                this.getContext().getContentResolver().notifyChange(uri, null);
                return cityInsertUri;

            case PROINCES:
                long proID = database.insert("province", null, values);
                Uri proInsertUri = ContentUris.withAppendedId(uri, proID);
                this.getContext().getContentResolver().notifyChange(uri, null);
                return proInsertUri;

            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public boolean onCreate() {
        database = DBManager.getInstance().getDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        switch (MATCHER.match(uri)) {
            case CITYS:
                return database.query("city_data", projection, selection, selectionArgs, null, null, sortOrder);

            case CITY:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                return database.query("city_data", projection, where, selectionArgs, null, null, sortOrder);

            case PROINCES:
                return database.query("province", projection, selection, selectionArgs, null, null, sortOrder);

            case PROINCE:
                long p_id = ContentUris.parseId(uri);
                String p_where = "_id=" + p_id;
                if (selection != null && !"".equals(selection)) {
                    p_where = selection + " and " + p_where;
                }
                return database.query("province", projection, p_where, selectionArgs, null, null, sortOrder);

            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int count = 0;
        switch (MATCHER.match(uri)) {
            case CITYS:
                count = database.update("city_data", values, selection, selectionArgs);
                return count;
            case CITY:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = database.update("person", values, where, selectionArgs);
                return count;

            case PROINCES:
                return database.update("province", values, selection, selectionArgs);

            case PROINCE:
                long p_id = ContentUris.parseId(uri);
                String p_where = "_id=" + p_id;
                if (selection != null && !"".equals(selection)) {
                    p_where = selection + " and " + p_where;
                }
                count = database.update("province", values , p_where, selectionArgs);
                return count;

            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }
}
