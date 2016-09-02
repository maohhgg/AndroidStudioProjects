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

    private int gridWidth = 0;
    private int cellSize = 0;
    private float textSize = 0;
    private float cellTextSize = 0;

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
        super.onSizeChanged(w, h, oldw, oldh);
        getLayout(w,h);
        createBitmapCells();
        createBackgroundBitmap(w, h);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG,"onDraw");

        canvas.drawBitmap(background, 0, 0, paint);
        drawCell(canvas);
    }

    private void drawCell(Canvas canvas){
        paint.setTextSize(textSize);
        paint.setTextAlign(Paint.Align.CENTER);

        for (int xx = 0; xx < core.squaresX; xx++){
            for (int yy = 0; yy < core.squaresY; yy++){
                int sX = startingX + gridWidth + (cellSize + gridWidth) * xx;
                int eX = sX + cellSize;
                int sY = startingY + gridWidth + (cellSize + gridWidth) * yy;
                int eY = sY + cellSize;

                Card currentCard = core.grid.getCellContent(xx,yy);
                if (currentCard != null){
                    int value = currentCard.getValue();
                    int index = log2(value);
                    boolean animated = false;
                    Log.e(TAG,"------"+index);
                    if (!animated) {
                        bitmapCell[index].setBounds(sX, sY, eX, eY);
                        bitmapCell[index].draw(canvas);
                    }
                }

            }
        }

    }

    private void getLayout(int width, int height) {
        cellSize = Math.min(width / (core.squaresX + 1), height / (core.squaresY + 3));
        gridWidth = cellSize / 7;

        paint.setTextSize(cellSize);
        textSize = cellSize * cellSize / Math.max(cellSize, paint.measureText("0000"));

        cellTextSize = textSize;
    }

    private void drawBackgroundGrid(Canvas canvas) {

    }

    // 创建背景
    private void createBackgroundBitmap(int width, int height) {
        background = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8);
        Canvas canvas = new Canvas();
        drawBackgroundGrid(canvas);
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
