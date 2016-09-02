package com.example.mao.twozerofoureight;


import java.util.ArrayList;

public class Grid {
    private final Card[][] field;

    public Grid(int sizeX, int szieY) {
        field = new Card[sizeX][szieY];
        clearGrid();
    }

    public Card getCellContent(Cell cell) {
        if (cell != null){
            return getCellContent(cell.getX(),cell.getY());
        }
        return null;
    }

    public Card getCellContent(int x, int y) {
        if (isCellWithinBounds(x, y)) {
            return field[x][y];
        } else {
            return null;
        }
    }

    private boolean isCellWithinBounds(int x, int y) {
        return 0 <= x && x < field.length
                && 0 <= y && y < field[0].length;
    }

    public void clearGrid() {
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                field[xx][yy] = null;
            }
        }
    }
    public int length(){
        return field[0].length;
    }

    public void insertTile(Card card) {
        field[card.getX()][card.getY()] = card;
    }

    public void removeTile(Card card) {
        field[card.getX()][card.getY()] = null;
    }

    public Cell randomAvailableCell() {
        ArrayList<Cell> availableCells = getAvailableCells();
        if (availableCells.size() >= 1) {
            return availableCells.get((int) Math.floor(Math.random() * availableCells.size()));
        }
        return null;
    }

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
}
