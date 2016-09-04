package com.example.mao.twozerofoureight;


import java.util.ArrayList;

public class Grid {
    private final Card[][] field;

    public Grid(int sizeX, int szieY) {
        field = new Card[sizeX][szieY];
        clearGrid();
    }

    // 根据Cell对象（一般是Card对象，Card继承Cell）得到Card
    public Card getCellContent(Cell cell) {
        if (cell != null){
            return getCellContent(cell.getX(),cell.getY());
        }
        return null;
    }

    // 得到具体位置的Card
    public Card getCellContent(int x, int y) {
        if (isCellWithinBounds(x, y)) {
            return field[x][y];
        } else {
            return null;
        }
    }

    // 判断是否在数组范围内
    public boolean isCellWithinBounds(Cell cell) {
        return isCellWithinBounds(cell.getX(),cell.getY());
    }

    private boolean isCellWithinBounds(int x, int y) {
        return 0 <= x && x < field.length
                && 0 <= y && y < field[0].length;
    }

    // 清除数组内容
    public void clearGrid() {
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                field[xx][yy] = null;
            }
        }
    }

    public void insertCard(Card card) {
        field[card.getX()][card.getY()] = card;
    }

    public void removeCard(Card card) {
        field[card.getX()][card.getY()] = null;
    }


    // 从 getAvailableCells生成的Cell池中随机得到一个Cell对象
    public Cell randomAvailableCell() {
        ArrayList<Cell> availableCells = getAvailableCells();
        if (availableCells.size() >= 1) {
            return availableCells.get((int) Math.floor(Math.random() * availableCells.size()));
        }
        return null;
    }



    // 初始化一个Cell池 Cell池大小 = 游戏Card数组总大小 - 当前Card数组大小
    private ArrayList<Cell> getAvailableCells() {
        ArrayList<Cell> availableCells = new ArrayList<>();
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                if (field[xx][yy] == null) {
                    availableCells.add(new Cell(xx, yy));
                }
            }
        }
        return availableCells;
    }

    public boolean isCellsAvailable() {
        return (getAvailableCells().size() >= 1);
    }

    public boolean isCellAvailable(Cell cell) {
        return !isCellOccupied(cell);
    }

    public boolean isCellOccupied(Cell cell) {
        return (getCellContent(cell) != null);
    }
}
