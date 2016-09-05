package com.example.mao.twozerofoureight;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameCore {
    private final static String TAG = GameCore.class.getSimpleName();

    private final static int GAME_NORMAL = 0;
    private final static int GAME_WIN = 1;
    private final static int GAME_LOSE = 2;

    private static final int MAXVALUE = 2048;

    public final static int MOVE_UP = 3;
    public final static int MOVE_LEFT = 7;
    public final static int MOVE_DOWN = 2;
    public final static int MOVE_RIGHT = 5;

    public static final int FADE_GLOBAL_ANIMATION = 0;
    private static final long MOVE_ANIMATION_TIME = GameView.BASE_ANIMATION_TIME;
    private static final long SPAWN_ANIMATION_TIME = GameView.BASE_ANIMATION_TIME;
    private static final long NOTIFICATION_DELAY_TIME = MOVE_ANIMATION_TIME + SPAWN_ANIMATION_TIME;
    private static final long NOTIFICATION_ANIMATION_TIME = GameView.BASE_ANIMATION_TIME * 5;

    public static final int SPAWN_ANIMATION = -1;
    public static final int MOVE_ANIMATION = 0;
    public static final int MERGE_ANIMATION = 1;


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

        addRandomCard();
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
        grid.prepareTiles();

        Cell vector = getVector(direction);
        List<Integer> traversalsX = buildTraversalsX(vector);
        List<Integer> traversalsY = buildTraversalsY(vector);
        boolean moved = false;

        for (int xx : traversalsX) {
            for (int yy : traversalsY) {
                Cell cell = new Cell(xx, yy);
                Card card = grid.getCellContent(cell);
                if (card != null){
                    Cell[] positions = findFarthestPosition(cell, vector);
                    Card next = grid.getCellContent(positions[1]);
                    if (next != null && next.getValue() == card.getValue() && next.getMergedFrom() == null){
                        Card merged = new Card(positions[1], card.getValue() * 2);
                        Card[] temp = {card, next};
                        merged.setMergedFrom(temp);


                        grid.insertCard(merged);
                        grid.removeCard(card);

                        // Converge the two tiles' positions
                        card.updatePosition(positions[1]);

                        int[] extras = {xx, yy};

                        aGrid.startAnimation(merged.getX(), merged.getY(), MOVE_ANIMATION,
                                MOVE_ANIMATION_TIME, 0, extras); //Direction: 0 = MOVING MERGED
                        aGrid.startAnimation(merged.getX(), merged.getY(), MERGE_ANIMATION,
                                SPAWN_ANIMATION_TIME, MOVE_ANIMATION_TIME, null);

                        // Update the score
                        score = score + merged.getValue();
                        highScore = Math.max(score, highScore);

                        // The mighty 2048 tile
                        if (merged.getValue() >= winValue() && !gameWon()) {
                            gameStatus = gameStatus + GAME_WIN; // Set win state
                            aGrid.startAnimation(positions[0].getX(), positions[0].getY(), MOVE_ANIMATION, MOVE_ANIMATION_TIME, 0, extras); //Direction: 1 = MOVING NO MERGE
                            endGame();
                        }
                    } else {
                        moveTile(card,positions[0]);
                        int[] extras = {xx, yy, 0};
                    }

                    if (!positionsEqual(cell, card)) {
                        moved = true;
                    }
                }

            }
        }
        if (moved) {
            addRandomCard();
            checkLose();
        }
        mView.invalidate();
    }


    private List<Integer> buildTraversalsX(Cell vector) {
        List<Integer> traversals = new ArrayList<>();

        for (int xx = 0; xx < squaresX; xx++) {
            traversals.add(xx);
        }
        if (vector.getX() == 1) {
            Collections.reverse(traversals);
        }

        return traversals;
    }

    private List<Integer> buildTraversalsY(Cell vector) {
        List<Integer> traversals = new ArrayList<>();

        for (int xx = 0; xx < squaresY; xx++) {
            traversals.add(xx);
        }
        if (vector.getY() == 1) {
            Collections.reverse(traversals);
        }

        return traversals;
    }

    private void checkLose() {
        if (!movesAvailable() && !gameWon()) {
            gameStatus = GAME_LOSE;
            endGame();
        }
    }
    public boolean gameLost() {
        return (gameStatus == GAME_LOSE);
    }

    public boolean isActive() {
        return !(gameWon() || gameLost());
    }

    private boolean movesAvailable() {
        return grid.isCellsAvailable() || cardMatchesAvailable();
    }

    private boolean cardMatchesAvailable() {
        Card card;

        for (int xx = 0; xx < squaresX; xx++) {
            for (int yy = 0; yy < squaresY; yy++) {
                card = grid.getCellContent(new Cell(xx, yy));

                if (card != null) {
                    for (Cell vector : getVector()) {
                        Cell cell = new Cell(xx + vector.getX(), yy + vector.getY());

                        Card other = grid.getCellContent(cell);

                        if (other != null && other.getValue() == card.getValue()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean positionsEqual(Cell cell, Card card) {
        return (cell.getX() == card.getX() && cell.getY() == card.getY());
    }


    private void moveTile(Card card, Cell cell) {
        grid.setField(card,null);
        grid.setField(cell,card);
        card.updatePosition(cell);
    }

    public boolean gameWon() {
        return (gameStatus > 0 && gameStatus % 2 != 0);
    }

    private int winValue() {
        return MAXVALUE;
    }
    private void endGame() {
        aGrid.startAnimation(-1, -1, FADE_GLOBAL_ANIMATION, NOTIFICATION_ANIMATION_TIME, NOTIFICATION_DELAY_TIME, null);
        if (score >= highScore) {
            highScore = score;
        }
    }

    private Cell getVector(int direction) {
        switch (direction){
            case MOVE_UP:
                return new Cell(0, -1);
            case MOVE_DOWN:
                return new Cell(0, 1);
            case MOVE_LEFT:
                return new Cell(-1, 0);
            case MOVE_RIGHT:
                return new Cell(1, 0);
        }
        return null;
    }
    private Cell[] getVector(){
        return new Cell[]{
            new Cell(0, -1),
            new Cell(0, 1),
            new Cell(-1, 0),
            new Cell(1, 0),
        };
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
