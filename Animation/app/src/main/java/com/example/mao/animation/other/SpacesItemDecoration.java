package com.example.mao.animation.Other;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by maohh on 2016/8/16.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpacesItemDecoration(int space) {
        this.space=space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=space;
        outRect.right=space;
        outRect.bottom=space;
        outRect.top = space;
    }
}