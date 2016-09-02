package com.example.mao.twozerofoureight;

import java.util.ArrayList;

public class AnimationGrid {
    private final ArrayList<AnimationCell>[][] field;

    public AnimationGrid(int sizeX, int sizeY) {
        field = new ArrayList[sizeX][sizeY];

        for (int xx = 0; xx < sizeX; xx++) {
            for (int yy = 0; yy < sizeY; yy++) {
                field[xx][yy] = new ArrayList<>();
            }
        }
    }

    public ArrayList<AnimationCell> getAnimationCell(int x, int y) {
        return field[x][y];
    }
}
