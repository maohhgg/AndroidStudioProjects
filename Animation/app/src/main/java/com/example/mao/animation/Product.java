package com.example.mao.animation;

/**
 * Created by maohh on 2016/8/16.
 */

public class Product {
    private int img;
    private int bigImg;
    private String title;

    public Product(int img,int big, String title) {
        this.img = img;
        this.bigImg = big;
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getBigImg() {
        return bigImg;
    }

    public void setBigImg(int bigImg) {
        this.bigImg = bigImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
