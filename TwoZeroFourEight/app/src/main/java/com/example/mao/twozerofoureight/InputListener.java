package com.example.mao.twozerofoureight;

import android.view.MotionEvent;
import android.view.View;

public class InputListener implements View.OnTouchListener {
    private static final int SWIPE_THRESHOLD_VELOCITY = 25;
    private static final int MOVE_THRESHOLD = 250;

    private final GameView mView;

    private float x;
    private float y;
    private float startX;
    private float startY;

    private int previousDirection = 1;

    private boolean hasMoved = false;

    public InputListener(GameView mView) {
        super();
        this.mView = mView;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()){
            // 屏幕按下时 记录按下位置的X Y值
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                hasMoved = false;
                startX = x;
                startY = y;
                return true;

            // 手指离开屏幕时
            case MotionEvent.ACTION_UP:
                previousDirection = 1;
                break;

            // 手指在屏幕上移动时（移动一次 会多次触发本方法）
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                if (pathMoved() > 0 && !hasMoved){
                    boolean moved = false;
                    float dx = x - startX;
                    float dy = y - startY;

                    //  根据移动发生的手指位置判断移动方向
                    //  垂直方向的移动
                    if (((dy >= SWIPE_THRESHOLD_VELOCITY && Math.abs(dy) >= Math.abs(dx)) || dy >= MOVE_THRESHOLD) && previousDirection % GameCore.MOVE_DOWN != 0) {
                        moved = true;
                        previousDirection = previousDirection * GameCore.MOVE_DOWN;
                        mView.core.move(GameCore.MOVE_DOWN);
                    } else if (((dy <= -SWIPE_THRESHOLD_VELOCITY && Math.abs(dy) >= Math.abs(dx)) || dy <= -MOVE_THRESHOLD) && previousDirection % GameCore.MOVE_UP != 0) {
                        moved = true;
                        previousDirection = previousDirection * GameCore.MOVE_UP;
                        mView.core.move(GameCore.MOVE_UP);
                    }
                    //  水平方向的移动
                    if (((dx >= SWIPE_THRESHOLD_VELOCITY && Math.abs(dx) >= Math.abs(dy)) || dx >= MOVE_THRESHOLD) && previousDirection % GameCore.MOVE_RIGHT != 0) {
                        moved = true;
                        previousDirection = previousDirection * GameCore.MOVE_RIGHT;
                        mView.core.move(GameCore.MOVE_RIGHT);
                    } else if (((dx <= -SWIPE_THRESHOLD_VELOCITY && Math.abs(dx) >= Math.abs(dy)) || dx <= -MOVE_THRESHOLD) && previousDirection % GameCore.MOVE_LEFT != 0) {
                        moved = true;
                        previousDirection = previousDirection * GameCore.MOVE_LEFT;
                        mView.core.move(GameCore.MOVE_LEFT);
                    }
                    if (moved){
                        hasMoved = true;
                        startX = x;
                        startY = y;
                    }
                }
                break;
        }
        return false;
    }


    /**
     *  返回当移动发生时手指移动的距离 用来判断是否发生移动
     *  2  1  2
     *  1  *  1
     *  2  1  2
     */
    private float pathMoved() {
        return (x - startX) * (x - startX) + (y - startY) * (y - startY);
    }
}
