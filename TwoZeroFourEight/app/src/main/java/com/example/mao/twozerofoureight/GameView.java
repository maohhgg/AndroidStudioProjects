package com.example.mao.twozerofoureight;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

public class GameView extends View {
    private final static String TAG = GameView.class.getSimpleName();
    public GameCore core;

    private Bitmap background = null;
    private Resources resources;
    private int numCellTypes = 21;

    private final BitmapDrawable[] bitmapCell = new BitmapDrawable[numCellTypes];
    private final Paint paint = new Paint();
    private Canvas canvas;

    public int startingX;
    public int startingY;
    public int endingX;
    public int endingY;

    private int gridWidth;
    private int cellSize;
    private float textSize;
    private float cellTextSize;
    private float headerTextSize;

    public GameView(Context context) {
        super(context);
        core = new GameCore(context,this);
        resources = context.getResources();

        Typeface font = Typeface.createFromAsset(resources.getAssets(), "ClearSans-Bold.ttf");
        paint.setTypeface(font);

        this.setBackgroundColor(resources.getColor(R.color.background));

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
        canvas.drawBitmap(background, 0, 0, paint);
        drawHeader(canvas);
        drawBackground(canvas);
        drawBackgroundGrid(canvas);
        drawCell(canvas);
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
                    boolean animated = false;
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


        if (width > height){
            startingX = (int) (surplusX * 0.618);
            endingX = startingX + gridBackgroudHeight;
            startingY = screenMiddleY - halfNumSquaresY;
            endingY = screenMiddleY + halfNumSquaresY;
        } else {
            startingX = screenMiddleX - halfNumSquaresX;
            endingX = screenMiddleX + halfNumSquaresX;
            startingY = (int) (surplusY * 0.618);
            endingY = startingY + gridBackgroudHeight;
        }


        paint.setTextSize(cellSize);
        textSize = cellSize * cellSize / Math.max(cellSize, paint.measureText("0000"));
        headerTextSize = textSize * 2;
        cellTextSize = textSize;
    }

    private void drawHeader(Canvas canvas) {
        paint.setTextSize(headerTextSize);
        paint.setColor(getResources().getColor(R.color.text_black));
        paint.setTextAlign(Paint.Align.LEFT);
        int textShiftY = centerText() * 2;
        int headerStartY = (int) ((startingY - cellSize * 1.5) - textShiftY);
        canvas.drawText(getResources().getString(R.string.header), startingX, headerStartY, paint);
    }

    // 创建背景
    private void drawBackground(Canvas canvas) {
        Drawable backgroundRectangle = resources.getDrawable(R.drawable.background_rectangle);
        drawDrawable(canvas, backgroundRectangle, startingX, startingY, endingX, endingY);
    }

    // 创建背景网格
    private void drawBackgroundGrid(Canvas canvas) {
        Drawable backgroundCell = resources.getDrawable(R.drawable.cell_rectangle);

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
            drawDrawable(canvas, resources.getDrawable(cellRectangleIds[xx]), 0, 0, cellSize, cellSize);
            drawCellText(canvas, value);
            // 21种方块存入数组中 不同的样式有13种 2的11到21重复
            bitmapCell[xx] = new BitmapDrawable(resources, bitmap);
        }
    }


    //  设定方块的文本
    private void drawCellText(Canvas canvas, int value) {
        int textShiftY = centerText();
        if (value >= 8) {
            paint.setColor(getResources().getColor(R.color.text_white));
        } else {
            paint.setColor(getResources().getColor(R.color.text_black));
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
