package com.example.mao.cat;

/**
 * Created by maohh on 2016/8/28.
 */
public class Dot {
    private int x,y;
    private int status;
    public final static int STATUS_ON = 0;
    public final static int STATUS_OFF = 1;
    public final static int STATUS_IN = 2;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
        status = STATUS_ON;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setXY(int x,int y) {
        this.x = x;
        this.y = y;
    }
}
