package com.example.mao.cat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by maohh on 2016/8/28.
 */
public class PlayGround extends SurfaceView implements View.OnTouchListener{
    private static  int WIDTH = 80;

    private static final int ROW = 10;
    private static final int COL = 10;
    private static final int BLOCK = 10;
    int k = 1;
    private Dot matrix[][];
    private Dot cat;

    SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            redraw();
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            WIDTH = i1 / (COL+1);
            redraw();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    };

    public PlayGround(Context context) {
        super(context);
        getHolder().addCallback(callback);
        initGame();
        setOnTouchListener(this);
    }

    public void redraw(){
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.LTGRAY);
        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        for (int i = 0;i < ROW;i++){
            int offset = 0;
            if (i % 2 != 0){
                offset = WIDTH / 2;
            }
            for (int j = 0;j < COL;j++){
                Dot temp = getDot(j,i);
                switch (temp.getStatus()){
                    case Dot.STATUS_IN:
                        paint.setColor(0xFFEE0000);
                        break;
                    case Dot.STATUS_OFF:
                        paint.setColor(0xFFFFAA00);
                        break;
                    case Dot.STATUS_ON:
                        paint.setColor(0xFFEEEEEE);
                        break;
                }
                canvas.drawOval(new RectF(temp.getX()*WIDTH + offset, temp.getY()*WIDTH, (temp.getX()+1)*WIDTH + offset, (temp.getY()+1)*WIDTH),paint);
            }
        }

        getHolder().unlockCanvasAndPost(canvas);
    }

    private void initGame(){
        matrix = new Dot[ROW][COL];
        for (int i = 0;i < ROW;i++){
            for (int j = 0; j < COL;j++){
                Dot temp = new Dot(j,i);
                temp.setStatus(Dot.STATUS_ON);
                matrix[i][j] = temp;
            }
        }
        for (int i = 0;i < BLOCK;){
            int x = (int) (Math.random() * 1000) % COL;
            int y = (int) (Math.random() * 1000) % ROW;
            Dot temp = getDot(x,y);
            if (temp.getStatus() == Dot.STATUS_ON){
                temp.setStatus(Dot.STATUS_OFF);
                i++;
            }
        }
        cat = new Dot(4,5);
        getDot(cat.getX(),cat.getY()).setStatus(Dot.STATUS_IN);
    }

    private boolean isAtEdge(Dot dot){
        if (dot.getX() * dot.getY() == 0 || dot.getX()+1 == COL || dot.getY()+1 == ROW ){
            return true;
        }
        return false;
    }

    private Dot getNrighbour(Dot dot,int dir){
        switch (dir){
            case 1:
                return getDot(dot.getX() - 1,dot.getY());
            case 2:
                if (dot.getY() % 2 == 0){
                    return getDot(dot.getX() - 1,dot.getY() - 1);
                } else {
                    return getDot(dot.getX(),dot.getY() - 1);
                }
            case 3:
                if (dot.getY() % 2 == 0){
                    return getDot(dot.getX(),dot.getY() - 1);
                } else {
                    return getDot(dot.getX() + 1,dot.getY() - 1);
                }
            case 4:
                return getDot(dot.getX() + 1,dot.getY());
            case 5:
                if (dot.getY() % 2 == 0){
                    return getDot(dot.getX(),dot.getY() + 1);
                } else {
                    return getDot(dot.getX() + 1,dot.getY() + 1);
                }
            case 6:
                if (dot.getY() % 2 == 0){
                    return getDot(dot.getX() - 1,dot.getY() + 1);
                } else {
                    return getDot(dot.getX(),dot.getY() + 1);
                }
        }

        return null;
    }

    private int getDistance(Dot dot,int dir){
        int distance = 0;
        if (isAtEdge(dot)){
            return 1;
        }
        Dot ori = dot,next;
        while (true){
            next = getNrighbour(ori,dir);
            if (next.getStatus() == Dot.STATUS_OFF){
                return distance * -1;
            }
            if (isAtEdge(next)){
                return distance+1;
            }
            distance++;
            ori = next;
        }
    }

    private Dot getDot(int x,int y){
        return matrix[y][x];
    }
    private void moveTo(Dot dot){
        dot.setStatus(Dot.STATUS_IN);
        getDot(cat.getX(),cat.getY()).setStatus(Dot.STATUS_ON);
        cat.setXY(dot.getX(),dot.getY());
    }

    private void move(){
        HashMap<Integer,Dot> avalible = new HashMap<>();
        for (int i = 1;i < 7;i++){
            Dot dot = getNrighbour(cat,i);
            if (dot.getStatus() == Dot.STATUS_ON){
                avalible.put(getDistance(cat,i),dot);
            }
            Log.e("AA",getDistance(cat,i)+ "----" +i);
        }

        if (avalible.size() == 0){
            win();
        } else {
            Iterator iterator = avalible.entrySet().iterator();
            int temp,minPlus = ROW,min = 0;
            while (iterator.hasNext()){
                Map.Entry entry = (Map.Entry) iterator.next();
                temp = (int) entry.getKey();
                if (temp > 0 && temp < minPlus){
                    minPlus = temp;
                } else if (temp < 0 && temp < min){
                    min = temp;
                }
            }
            if (minPlus < ROW){
                moveTo(avalible.get(minPlus));
            } else {
                moveTo(avalible.get(min));
            }
            if (isAtEdge(cat)){
                lose();
                return;
            }
        }
    }

    private void lose(){
        Toast.makeText(getContext(),"YOU LOSE",Toast.LENGTH_LONG).show();
        initGame();
        redraw();
    }
    private void win(){
        Toast.makeText(getContext(),"YOU WIN",Toast.LENGTH_LONG).show();
        initGame();
        redraw();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP){
            int x,y;
            y = (int) event.getY() / WIDTH;
            if (y % 2 == 0){
                x = (int) event.getX() / WIDTH;
            } else {
                x = (int) (event.getX()-WIDTH/2) / WIDTH;
            }
            if (y + 1 > ROW || x + 1 > COL){
                return false;
            } else {
                if (getDot(x,y).getStatus() == Dot.STATUS_IN){
                    return false;
                }
                getDot(x,y).setStatus(Dot.STATUS_OFF);
            }
            move();
            redraw();
        }
        return true;
    }
}
