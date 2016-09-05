package com.example.mao.twozerofoureight;

public class Card extends Cell {
    private int value;
    private Card[] mergedFrom;

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

    public void updatePosition(Cell cell){
        updatePosition(cell.getX(),cell.getY());
    }

    public void updatePosition(int x,int y){
        this.setX(x);
        this.setY(y);
    }

    public Card[] getMergedFrom() {
        return mergedFrom;
    }

    public void setMergedFrom(Card[] tile) {
        mergedFrom = tile;
    }
}
