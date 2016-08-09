package com.example.mao.network;

import java.util.List;

/**
 * Created by maohh on 2016/8/9.
 */
public class Category {
    public int id;
    public String name;
    public List<Category> children;

    public int getId() {
        return id;
    }

    public List<Category> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}