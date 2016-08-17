package com.example.mao.animation.other;

/**
 * Created by maohh on 2016/8/16.
 */

public class Product {
    private static final String BASE_URL = "http://222.222.222.200/drawable/";
    public static Product[] Product = new Product[]{
        new Product(0,"看着眼熟"),
        new Product(1,"油腻的无名"),
        new Product(2,"拉姆在上面 雷姆在下面"),
        new Product(3,"好评"),
        new Product(4,"一个雷姆"),
        new Product(5,"另一个雷姆"),
        new Product(6,"好评+1"),
        new Product(7,"还是雷姆"),
        new Product(8,"潜水性驱逐舰0_0"),
        new Product(9,"不是女主的女主"),
        new Product(10,"好平 好平"),
        new Product(11,"好评的摄影师"),
        new Product(12,"我上面说什么"),
        new Product(13,"拉姆 雷姆"),
        new Product(14,"你好 你贩剑么"),
        new Product(15,"0.0"),
        new Product(16,"这里面就你最大"),
        new Product(17,"营养跟不上了"),
        new Product(18,"还是无名"),
    };

    private int img;
    private String title;

    public Product(int img, String title) {
        this.img = img;
        this.title = title;
    }

    public String getImg() {
        return BASE_URL + "?id=" + img;
    }


    public String getBigImg() {
        return BASE_URL + "?id=" + img + "&size=big";
    }


    public String getTitle() {
        return title;
    }

}
