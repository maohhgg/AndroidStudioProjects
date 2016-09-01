package com.example.mao.twozerofoureight;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by maohh on 2016/9/1.
 */
public class GameView extends View {
    private final static String TAG = GameView.class.getSimpleName();
    public GameCore core;

    private Bitmap background = null;
    private Resources resources;
    private int numCellTypes = 21;

    private final BitmapDrawable[] bitmapCell = new BitmapDrawable[numCellTypes];
    private final Paint paint = new Paint();
    private Canvas canvas;

    private int cellSize = 0;
    private float textSize = 0;
    private float cellTextSize = 0;

    public GameView(Context context) {
        super(context);
        core = new GameCore(context,this);
        resources = context.getResources();


        this.setBackgroundColor(resources.getColor(R.color.background));

        setOnTouchListener(new InputListener(this));
        core.newGame();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(background, 0, 0, paint);
        drawCell(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        background = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void drawCell(Canvas canvas){
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.CENTER);

        for (int xx = 0; xx < core.squaresX; xx++){
            for (int yy = 0; yy < core.squaresY; yy++){
                Card currentCard = core.grid.getCellContent(xx,yy);
                ArrayList<AnimationCell> aArray = core.aGrid.getAnimationCell(xx, yy);
            }
        }

    }

    private void createBitmapCells() {

        int[] cellRectangleIds = getCellRectangleIds();
        paint.setTextAlign(Paint.Align.CENTER);
        for (int xx = 1; xx < bitmapCell.length; xx++) {
            int value = (int) Math.pow(2, xx);
            paint.setTextSize(cellTextSize);
            float tempTextSize = cellTextSize * cellSize * 0.9f / Math.max(cellSize * 0.9f, paint.measureText(String.valueOf(value)));
            paint.setTextSize(tempTextSize);
            Bitmap bitmap = Bitmap.createBitmap(cellSize, cellSize, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawDrawable(canvas, resources.getDrawable(cellRectangleIds[xx]), 0, 0, cellSize, cellSize);
            drawCellText(canvas, value);
            bitmapCell[xx] = new BitmapDrawable(resources, bitmap);
        }
    }




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

    private int centerText() {
        return (int) ((paint.descent() + paint.ascent()) / 2);
    }
}
