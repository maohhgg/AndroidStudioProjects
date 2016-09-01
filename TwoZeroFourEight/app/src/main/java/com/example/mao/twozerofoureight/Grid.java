package com.example.mao.twozerofoureight;

/**
 * Created by maohh on 2016/9/1.
 */
public class Grid {
    private final Card[][] field;

    public Grid(int sizeX, int szieY) {
        field = new Card[sizeX][szieY];
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
}
