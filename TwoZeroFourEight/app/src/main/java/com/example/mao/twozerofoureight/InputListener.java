package com.example.mao.twozerofoureight;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by maohh on 2016/9/1.
 */
public class InputListener implements View.OnTouchListener {
    private static final int SWIPE_THRESHOLD_VELOCITY = 25;
    private static final int MOVE_THRESHOLD = 250;

    private final GameView mView;

    private float x;
    private float y;
    private float startX;
    private float startY;
    private float lastX;
    private float lastY;

    private int previousDirection = 1;
    private int veryLastDirection = 1;

    private boolean hasMoved = false;

    public InputListener(GameView mView) {
        super();
        this.mView = mView;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                hasMoved = false;
                startX = x;
                startY = y;
                lastX = 0;
                lastY = 0;
                return true;
            case MotionEvent.ACTION_UP:
                x = event.getX();
                y = event.getY();
                previousDirection = 1;
                veryLastDirection = 1;
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                if (pathMoved() > 0 && !hasMoved){
                    boolean moved = false;
                    float dx = x - startX;
                    float dy = y - startY;

                    //Vertical
                    if (((dy >= SWIPE_THRESHOLD_VELOCITY && Math.abs(dy) >= Math.abs(dx)) || dy >= MOVE_THRESHOLD) && previousDirection % mView.core.MOVE_DOWN != 0) {
                        moved = true;
                        previousDirection = previousDirection * mView.core.MOVE_DOWN;
                        veryLastDirection = mView.core.MOVE_DOWN;
                        mView.core.move(mView.core.MOVE_DOWN);
                    } else if (((dy <= -SWIPE_THRESHOLD_VELOCITY && Math.abs(dy) >= Math.abs(dx)) || dy <= -MOVE_THRESHOLD) && previousDirection % mView.core.MOVE_UP != 0) {
                        moved = true;
                        previousDirection = previousDirection * mView.core.MOVE_UP;
                        veryLastDirection = mView.core.MOVE_UP;
                        mView.core.move(mView.core.MOVE_UP);
                    }
                    //Horizontal
                    if (((dx >= SWIPE_THRESHOLD_VELOCITY && Math.abs(dx) >= Math.abs(dy)) || dx >= MOVE_THRESHOLD) && previousDirection % mView.core.MOVE_RIGHT != 0) {
                        moved = true;
                        previousDirection = previousDirection * mView.core.MOVE_RIGHT;
                        veryLastDirection = mView.core.MOVE_RIGHT;
                        mView.core.move(mView.core.MOVE_RIGHT);
                    } else if (((dx <= -SWIPE_THRESHOLD_VELOCITY && Math.abs(dx) >= Math.abs(dy)) || dx <= -MOVE_THRESHOLD) && previousDirection % mView.core.MOVE_LEFT != 0) {
                        moved = true;
                        previousDirection = previousDirection * mView.core.MOVE_LEFT;
                        veryLastDirection = mView.core.MOVE_LEFT;
                        mView.core.move(mView.core.MOVE_LEFT);
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


    private float pathMoved() {
        return (x - startX) * (x - startX) + (y - startY) * (y - startY);
    }
}
