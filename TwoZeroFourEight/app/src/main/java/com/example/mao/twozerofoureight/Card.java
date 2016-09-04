package com.example.mao.twozerofoureight;

public class Card extends Cell {
    private int value;

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

    public void setValue(int value) {
        this.value = value;
    }

    public void updatePosition(int x,int y){
        this.setX(x);
        this.setY(y);
    }
}
