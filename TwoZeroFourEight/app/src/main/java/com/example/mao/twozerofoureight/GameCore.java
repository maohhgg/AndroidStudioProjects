package com.example.mao.twozerofoureight;

import android.content.Context;
import android.util.Log;

/**
 * Created by maohh on 2016/9/1.
 */
public class GameCore {
    private final static String TAG = GameCore.class.getSimpleName();

    private final static int GAME_NORMAL = 0;
    private final static int GAME_WIN = 1;
    private final static int GAME_LOSE = 2;

    public final static int MOVE_UP = 3;
    public final static int MOVE_LEFT = 7;
    public final static int MOVE_DOWN = 2;
    public final static int MOVE_RIGHT = 5;

    public final int squaresX = 4;
    public final int squaresY = 4;

    private final Context mContext;
    private final GameView mView;

    private int gameStatus;
    private long score = 0;
    private long highScore = 0;

    public Grid grid = null;
    public AnimationGrid aGrid;

    public GameCore(Context mContext, GameView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    public void newGame(){

        if (grid == null){
            grid = new Grid(squaresX,squaresY);
        } else {
            grid.clearGrid();
        }
        aGrid = new AnimationGrid(squaresX,squaresY);
        if (highScore < score){
            highScore = score;
        }
        score = 0;
        gameStatus = GAME_NORMAL;
    }

    public void move(int direction){
        switch (direction){
            case MOVE_UP:
                Log.e(TAG,"⬆");
                break;
            case MOVE_DOWN:
                Log.e(TAG,"⬇");
                break;
            case MOVE_LEFT:
                Log.e(TAG,"⬅");
                break;
            case MOVE_RIGHT:
                Log.e(TAG,"➡");
                break;
        }
    }
}
