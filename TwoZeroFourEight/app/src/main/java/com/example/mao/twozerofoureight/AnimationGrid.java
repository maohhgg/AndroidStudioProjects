package com.example.mao.twozerofoureight;

import java.util.ArrayList;

public class AnimationGrid {
    private final ArrayList<AnimationCell>[][] field;
    public final ArrayList<AnimationCell> globalAnimation = new ArrayList<>();
    private int activeAnimations = 0;

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

    public void startAnimation(int x, int y, int animationType, long length, long delay, int[] extras) {
        AnimationCell animationToAdd = new AnimationCell(x, y, animationType, length, delay, extras);
        if (x == -1 && y == -1) {
            globalAnimation.add(animationToAdd);
        } else {
            field[x][y].add(animationToAdd);
        }
        activeAnimations = activeAnimations + 1;
    }
}
