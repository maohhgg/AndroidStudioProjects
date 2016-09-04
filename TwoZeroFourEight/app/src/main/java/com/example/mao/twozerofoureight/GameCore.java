package com.example.mao.twozerofoureight;

import android.content.Context;
import android.util.Log;

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

    public final Context mContext;
    public final GameView mView;

    public int gameStatus;
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

        spawnCard(new Card(0,0,2));
        spawnCard(new Card(1,0,4));
        spawnCard(new Card(2,0,8));
        spawnCard(new Card(3,0,16));
        spawnCard(new Card(0,1,2));
        spawnCard(new Card(1,1,64));
        spawnCard(new Card(2,1,128));
        spawnCard(new Card(3,1,256));
        spawnCard(new Card(0,2,512));
        spawnCard(new Card(1,2,1024));
        spawnCard(new Card(2,2,2048));
        spawnCard(new Card(3,2,4096));
        spawnCard(new Card(0,3,8192));
        spawnCard(new Card(1,3,8192));
        spawnCard(new Card(2,3,8192));
        addRandomCard();

        aGrid = new AnimationGrid(squaresX,squaresY);
        if (highScore < score){
            highScore = score;
        }
        score = 0;
        gameStatus = GAME_NORMAL;
    }

    // 随机生成一个（90%几率出现2 10%几率出现4）的card
    private void addRandomCard() {
        if (grid.isCellsAvailable()) {
            int value = Math.random() < 0.9 ? 2 : 4;
            Card card = new Card(grid.randomAvailableCell(), value);
            spawnCard(card);
        }
    }

    // 添加一个card到游戏card数组中
    private void spawnCard(Card card) {
        grid.insertCard(card);
    }

    public void move(int direction){

        Cell vector = getVector(direction);
        for (int yy = 0; yy < squaresY; yy++){
            for (int xx = 0; xx < squaresX ; xx++){
                Cell cell = new Cell(xx, yy);
                Card card = grid.getCellContent(cell);
                if (card != null){
                    Cell[] postion = findFarthestPosition(cell, vector);
                }

            }
        }
        mView.invalidate();
    }

    private Cell getVector(int direction) {
        Cell[] map = new Cell[7];
        map[MOVE_UP] = new Cell(0, -1);
        map[MOVE_RIGHT] = new Cell(1, 0);
        map[MOVE_DOWN] = new Cell(0, 1);
        map[MOVE_LEFT] = new Cell(-1, 0);
        return map[direction];
    }

    private Cell[] findFarthestPosition(Cell cell, Cell vector) {
        Cell previous;
        Cell nextCell = new Cell(cell.getX(), cell.getY());
        do {
            previous = nextCell;
            nextCell = new Cell(previous.getX() + vector.getX(),
                    previous.getY() + vector.getY());
        } while (grid.isCellWithinBounds(nextCell) && grid.isCellAvailable(nextCell));

        return new Cell[]{previous, nextCell};
    }

}
