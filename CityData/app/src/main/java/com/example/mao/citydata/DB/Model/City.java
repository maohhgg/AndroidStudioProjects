package com.example.mao.citydata.DB.Model;

import java.io.Serializable;

/**
 * Created by maohh on 2016/8/25.
 */
public class City implements Serializable {
    public int CityKey;
    public String ZoneName;
    public int CityID;

    public City(int key,String name,int id){
        CityID = id;
        ZoneName = name;
        CityKey = key;
    }
}
