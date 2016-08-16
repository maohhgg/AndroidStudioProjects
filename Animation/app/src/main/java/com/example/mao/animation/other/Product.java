package com.example.mao.animation.other;

/**
 * Created by maohh on 2016/8/16.
 */

public class Product {
    private static final String BASE_URL = "http://222.222.222.200/drawable/";
    public static Product[] Product = new Product[]{
        new Product(0,"11111111"),
        new Product(1,"3245"),
        new Product(2,"3452345"),
        new Product(3,"345234"),
        new Product(4,"3453245"),
        new Product(5,"123123"),
        new Product(6,"3435"),
        new Product(7,"拉姆雷姆"),
        new Product(8,"123123"),
        new Product(9,"雷姆"),
        new Product(10,"123123"),
        new Product(11,"123123"),
        new Product(12,"123123"),
        new Product(13,"123123"),
        new Product(14,"123123"),
        new Product(15,"123123"),
        new Product(16,"123123"),
        new Product(17,"123123"),
        new Product(18,"123123"),
        new Product(19,"123123"),
        new Product(20,"123123"),
        new Product(21,"123123"),
        new Product(22,"123123"),
        new Product(23,"123123"),
        new Product(24,"123123"),
        new Product(25,"123123")
    };

    private int img;
    private String title;

    public Product(int img, String title) {
        this.img = img;
        this.title = title;
    }

    public String getImg() {
        return BASE_URL + "?id=" + img + "&size=small";
    }


    public String getBigImg() {
        return BASE_URL + "?id=" + img;
    }


    public String getTitle() {
        return title;
    }

}
