package com.example.mao.citydata.Utils;

import android.util.Log;

import com.example.mao.citydata.DB.CityDB;
import com.example.mao.citydata.DB.Model.City;

import java.util.List;

/**
 * Created by maohh on 2016/8/25.
 */
public class Util {
    private static final String TAG = Util.class.getSimpleName();
    private static int currKey = 0;
    private static int currCityID = 0;

    public static List lineSave(List list, String string){
        if (string != null && string.length() > 0){
            String[] info = string.split("=");
            City city = new City(Integer.parseInt(info[0]),info[1],currCityID);
            list.add(city);
            int temp = city.CityKey / 10000;

            Log.i(TAG,temp+"");
            if (temp < 10104){
                if (currKey == 0 || temp != currKey){
                    currKey = temp;
                    currCityID = (int) CityDB.insertCity(new City(0,info[1]+"市",0));
                }
            } else if (temp > 10104){
                temp = city.CityKey / 100;
                if (temp != currKey && city.CityKey%100 == 1){
                    currKey = temp;
                    currCityID = (int) CityDB.insertCity(new City(0,info[1]+"市",0));
                }
            }
            city.CityID = currCityID;
            CityDB.insertZone(city);
            Log.e(TAG,Integer.parseInt(info[0])+"----"+info[1]+"----"+currCityID);
        }
        return list;
    }
}
