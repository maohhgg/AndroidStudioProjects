package com.example.mao.citydata.DB;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.mao.citydata.DB.Model.City;

/**
 * Created by maohh on 2016/8/24.
 */
public class CityDB {
    private static final String ZONE = "zone_data";
    private static final String CITY = "city_data";
    private static SQLiteDatabase database = DBManager.getInstance().getDatabase();

    public static long insertZone(City city){
        ContentValues value = new ContentValues();
        value.put("CityKey",city.CityKey);
        value.put("CityID",city.CityID);
        value.put("ZoneName",city.ZoneName);

        return database.insert(ZONE,null,value);
    }

    public static long insertCity(City city){
        ContentValues value = new ContentValues();
        value.put("ProID",city.CityID);
        value.put("CityName",city.ZoneName);

        return database.insert(CITY,null,value);
    }

}
