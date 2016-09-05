package com.example.mao.twozerofoureight;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class GameView extends View {
    private final static String TAG = GameView.class.getSimpleName();
    public GameCore core;

    public static final int BASE_ANIMATION_TIME = 100000000;
    private static final float MERGING_ACCELERATION = (float) -0.5;
    private static final float INITIAL_VELOCITY = (1 - MERGING_ACCELERATION) / 4;

    private Bitmap background = null;
    private Resources resources;
    private Context mContext;
    private int numCellTypes = 21;

    private final BitmapDrawable[] bitmapCell = new BitmapDrawable[numCellTypes];
    private final Paint paint = new Paint();

    public int startingX;
    public int startingY;
    public int endingX;
    public int endingY;


    private int gridWidth;
    private int cellSize;
    private float textSize;
    private float cellTextSize;



    public GameView(Context context) {
        super(context);
        core = new GameCore(context,this);
        resources = context.getResources();
        mContext = context;

        Typeface font = Typeface.createFromAsset(resources.getAssets(), "ClearSans-Bold.ttf");
        paint.setTypeface(font);

        this.setBackgroundColor(ContextCompat.getColor(context, R.color.background));

        setOnTouchListener(new InputListener(this));
        core.newGame();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.e(TAG,"onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        getLayout(w,h);
        createBackgroundBitmap(w, h);
        createBitmapCells();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG,"onDraw");
        if (!core.isActive()) {
            drawEndGameState();
        }

        canvas.drawBitmap(background, 0, 0, paint);
//        drawHeader(canvas);
        drawBackground(canvas);
        drawBackgroundGrid(canvas);
        drawCell(canvas);
    }

    private void drawEndGameState() {
        if (core.gameWon()) {
            Log.e(TAG,"YOU WIN");
        } else if (core.gameLost()){
            Log.e(TAG,"Game Over");
        }
    }


    // 绘制所有的Card对象
    private void drawCell(Canvas canvas){
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);

        for (int xx = 0; xx < core.squaresX; xx++){
            for (int yy = 0; yy < core.squaresY; yy++){
                // 计算当前Card的在屏幕位置
                int sX = startingX + gridWidth + (cellSize + gridWidth) * xx;
                int eX = sX + cellSize;
                int sY = startingY + gridWidth + (cellSize + gridWidth) * yy;
                int eY = sY + cellSize;


                // 从全局Card数组中得到当前的Card对象
                Card currentCard = core.grid.getCellContent(xx,yy);
                if (currentCard != null){
                    int value = currentCard.getValue();
                    int index = log2(value);

                    //Check for any active animations
                    ArrayList<AnimationCell> aArray = core.aGrid.getAnimationCell(xx, yy);
                    boolean animated = false;
                    for (int i = aArray.size() - 1; i >= 0; i--) {
                        AnimationCell aCell = aArray.get(i);
                        //If this animation is not active, skip it
                        if (aCell.getAnimationType() == GameCore.SPAWN_ANIMATION) {
                            animated = true;
                        }
                        if (!aCell.isActive()) {
                            continue;
                        }

                        if (aCell.getAnimationType() == GameCore.SPAWN_ANIMATION) { // Spawning animation
                            double percentDone = aCell.getPercentageDone();
                            float textScaleSize = (float) (percentDone);
                            paint.setTextSize(textSize * textScaleSize);

                            float cellScaleSize = cellSize / 2 * (1 - textScaleSize);
                            bitmapCell[index].setBounds((int) (sX + cellScaleSize), (int) (sY + cellScaleSize), (int) (eX - cellScaleSize), (int) (eY - cellScaleSize));
                            bitmapCell[index].draw(canvas);
                        } else if (aCell.getAnimationType() == GameCore.MERGE_ANIMATION) { // Merging Animation
                            double percentDone = aCell.getPercentageDone();
                            float textScaleSize = (float) (1 + INITIAL_VELOCITY * percentDone
                                    + MERGING_ACCELERATION * percentDone * percentDone / 2);
                            paint.setTextSize(textSize * textScaleSize);

                            float cellScaleSize = cellSize / 2 * (1 - textScaleSize);
                            bitmapCell[index].setBounds((int) (sX + cellScaleSize), (int) (sY + cellScaleSize), (int) (eX - cellScaleSize), (int) (eY - cellScaleSize));
                            bitmapCell[index].draw(canvas);
                        } else if (aCell.getAnimationType() == GameCore.MOVE_ANIMATION) {  // Moving animation
                            double percentDone = aCell.getPercentageDone();
                            int tempIndex = index;
                            if (aArray.size() >= 2) {
                                tempIndex = tempIndex - 1;
                            }
                            int previousX = aCell.extras[0];
                            int previousY = aCell.extras[1];
                            int currentX = currentCard.getX();
                            int currentY = currentCard.getY();
                            int dX = (int) ((currentX - previousX) * (cellSize + gridWidth) * (percentDone - 1) * 1.0);
                            int dY = (int) ((currentY - previousY) * (cellSize + gridWidth) * (percentDone - 1) * 1.0);
                            bitmapCell[tempIndex].setBounds(sX + dX, sY + dY, eX + dX, eY + dY);
                            bitmapCell[tempIndex].draw(canvas);
                        }
                        animated = true;
                    }

                    //No active animations? Just draw the cell
                    if (!animated) {
                        bitmapCell[index].setBounds(sX, sY, eX, eY);
                        bitmapCell[index].draw(canvas);
                    }

                }

            }
        }

    }

    // 根据屏幕初始化字体和card大小
    private void getLayout(int width, int height) {
        int screenMiddleX = width / 2;
        int screenMiddleY = height / 2;

        cellSize = Math.min(width / (core.squaresX + 1), height / (core.squaresY + 1));
        gridWidth = cellSize / 7;

        int gridBackgroudWidth = cellSize * core.squaresX + gridWidth * (core.squaresX + 1);
        int gridBackgroudHeight = cellSize * core.squaresY + gridWidth * (core.squaresY + 1);

        int halfNumSquaresX = gridBackgroudWidth / 2;
        int halfNumSquaresY = gridBackgroudHeight / 2;
        int surplusY = height - gridBackgroudHeight;
        int surplusX = width - gridBackgroudWidth;

        paint.setTextSize(cellSize);
        textSize = cellSize * cellSize / Math.max(cellSize, paint.measureText("0000"));

        cellTextSize = textSize;

        if (width > height){
            startingX = (int) (surplusX * 0.8);
            endingX = startingX + gridBackgroudHeight;
            startingY = screenMiddleY - halfNumSquaresY;
            endingY = screenMiddleY + halfNumSquaresY;

        } else {
            startingX = screenMiddleX - halfNumSquaresX;
            endingX = screenMiddleX + halfNumSquaresX;
            startingY = (int) (surplusY * 0.5);
            endingY = startingY + gridBackgroudHeight;
        }

    }




    // 创建背景
    private void drawBackground(Canvas canvas) {
        Drawable backgroundRectangle = ContextCompat.getDrawable(mContext,R.drawable.background_rectangle);
        drawDrawable(canvas, backgroundRectangle, startingX, startingY, endingX, endingY);
    }

    // 创建背景网格
    private void drawBackgroundGrid(Canvas canvas) {
        Drawable backgroundCell = ContextCompat.getDrawable(mContext,R.drawable.cell_rectangle);

        for (int xx = 0; xx < core.squaresX; xx++) {
            for (int yy = 0; yy < core.squaresY; yy++) {
                int sX = startingX + gridWidth + (cellSize + gridWidth) * xx;
                int eX = sX + cellSize;
                int sY = startingY + gridWidth + (cellSize + gridWidth) * yy;
                int eY = sY + cellSize;

                drawDrawable(canvas, backgroundCell, sX, sY, eX, eY);
            }
        }
    }

    // 创建背景
    private void createBackgroundBitmap(int width, int height) {
        background = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
    }


    // 创建方块并为方块绑定背景色和文本
    private void createBitmapCells() {
        int[] cellRectangleIds = getCellRectangleIds();
        paint.setTextAlign(Paint.Align.CENTER);
        for (int xx = 1; xx < bitmapCell.length; xx++) {
            int value = (int) Math.pow(2, xx);

            // 动态调整文本字体大小
            paint.setTextSize(cellTextSize);
            float tempTextSize = cellTextSize * cellSize * 0.9f / Math.max(cellSize * 0.9f, paint.measureText(String.valueOf(value)));
            paint.setTextSize(tempTextSize);

            Bitmap bitmap = Bitmap.createBitmap(cellSize, cellSize, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            //  创建不同颜色的方块并绑定文本
            drawDrawable(canvas, ContextCompat.getDrawable(mContext,cellRectangleIds[xx]), 0, 0, cellSize, cellSize);
            drawCellText(canvas, value);
            // 21种方块存入数组中 不同的样式有13种 2的11到21重复
            bitmapCell[xx] = new BitmapDrawable(resources, bitmap);
        }
    }


    //  设定方块的文本
    private void drawCellText(Canvas canvas, int value) {
        int textShiftY = centerText();
        if (value >= 8) {
            paint.setColor(ContextCompat.getColor(mContext,R.color.text_white));
        } else {
            paint.setColor(ContextCompat.getColor(mContext,R.color.text_black));
        }
        canvas.drawText("" + value, cellSize / 2, cellSize / 2 - textShiftY, paint);
    }


    private void drawDrawable(Canvas canvas, Drawable draw, int startX, int startY, int endX, int endY){
        draw.setBounds(startX,startY,endX,endY);
        draw.draw(canvas);
    }

    //  定义背景色数组
    private int[] getCellRectangleIds() {
        int[] cellRectangleIds = new int[numCellTypes];
        cellRectangleIds[0] = R.drawable.cell_rectangle;
        cellRectangleIds[1] = R.drawable.cell_rectangle_2;
        cellRectangleIds[2] = R.drawable.cell_rectangle_4;
        cellRectangleIds[3] = R.drawable.cell_rectangle_8;
        cellRectangleIds[4] = R.drawable.cell_rectangle_16;
        cellRectangleIds[5] = R.drawable.cell_rectangle_32;
        cellRectangleIds[6] = R.drawable.cell_rectangle_64;
        cellRectangleIds[7] = R.drawable.cell_rectangle_128;
        cellRectangleIds[8] = R.drawable.cell_rectangle_256;
        cellRectangleIds[9] = R.drawable.cell_rectangle_512;
        cellRectangleIds[10] = R.drawable.cell_rectangle_1024;
        cellRectangleIds[11] = R.drawable.cell_rectangle_2048;
        for (int xx = 12; xx < cellRectangleIds.length; xx++) {
            cellRectangleIds[xx] = R.drawable.cell_rectangle_4096;
        }
        return cellRectangleIds;
    }

    // log2函数
    private static int log2(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        return 31 - Integer.numberOfLeadingZeros(n);
    }

    private int centerText() {
        return (int) ((paint.descent() + paint.ascent()) / 2);
    }
}
