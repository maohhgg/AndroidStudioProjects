package com.example.mao.twozerofoureight;

/**
 * Created by maohh on 2016/9/1.
 */
public class Card extends Cell {
    private final int value;
    private Card[] mergedFrom = null;

    public Card(int x, int y, int value) {
        super(x, y);
        this.value = value;
    }
    public Card(Cell cell,int value){
        super(cell.getX(),cell.getY());
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Card[] getMergedFrom() {
        return mergedFrom;
    }

    public void updatePosition(Cell cell) {
        this.setX(cell.getX());
        this.setY(cell.getY());
    }

    public void setMergedFrom(Card[] cards) {
        mergedFrom = cards;
    }
}
