package com.example.mao.quickdev.itemindex.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SectionIndexer;

/**
 * 右侧的索引条
 * 绘制
 * 操作
 */
class IndexScroller {
    private float mIndexbarWidth;
    private float mIndexbarMargin;
    private float mPreviewPadding;
    private float mDensity;
    private float mScaledDensity;
    private float mAlphaRate;
    private int mState = STATE_HIDDEN;
    private int mListViewWidth;
    private int mListViewHeight;
    private boolean mIsIndexing = false;
    private int mCurrentSection = -1;
    private ListView mListView = null;
    private SectionIndexer mIndexer = null;
    private String[] mSection = null;
    private RectF mIndexbarRectF;


    private static final int STATE_HIDDEN = 0;
    private static final int STATE_HIDING  = 1;
    private static final int STATE_SHOWN = 2;
    private static final int STATE_SHOWING = 3;


    IndexScroller(Context context, ListView listView) {
        mDensity = context.getResources().getDisplayMetrics().density;
        mScaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        mListView = listView;
        setAdapter(mListView.getAdapter());

        mIndexbarMargin = 10 * mDensity;
        mIndexbarWidth = 20 *mDensity;
        mPreviewPadding = 5 * mDensity;
    }

    void setAdapter(Adapter adapter){
        if (adapter instanceof SectionIndexer){
            mIndexer = (SectionIndexer) adapter;
            mSection = (String[]) mIndexer.getSections();
        }
    }

    void draw(Canvas canvas) {

        //绘制背景
        Paint indexbarPaint = new Paint();
        indexbarPaint.setColor(Color.BLACK);
        indexbarPaint.setAlpha((int) (100 * mAlphaRate));
        indexbarPaint.setAntiAlias(true);  // 抗锯齿

        canvas.drawRoundRect(mIndexbarRectF, 5 * mDensity, 5 * mDensity, indexbarPaint);


        if (mSection != null && mSection.length > 0){

            //当前点击了索引栏
            if (mCurrentSection > 0){
                Paint previewPaint = new Paint();
                previewPaint.setColor(Color.BLACK);
                previewPaint.setAlpha(100);
                previewPaint.setAntiAlias(true);
                previewPaint.setShadowLayer(3, 0, 0, Color.argb(64, 0, 0, 0)); // 设置阴影层

                Paint previewTextPaint = new Paint();
                previewTextPaint.setColor(Color.WHITE);
                previewTextPaint.setAntiAlias(true);
                previewTextPaint.setTextSize(50 * mScaledDensity);

                float previewTextWidth =  previewTextPaint.measureText(mSection[mCurrentSection]); // 计算字体宽度
                float previewHeight = 2 * mPreviewPadding + (previewTextPaint.descent() - previewTextPaint.ascent());

                RectF previewRectF = new RectF(
                        (mListViewWidth - previewHeight) / 2,
                        (mListViewHeight - previewHeight) / 2,
                        (mListViewWidth - previewHeight) / 2 + previewHeight,
                        (mListViewHeight - previewHeight) / 2 + previewHeight);

                canvas.drawRoundRect(previewRectF, 5 * mDensity, 5 * mDensity, previewPaint);
                canvas.drawText(
                        mSection[mCurrentSection],
                        previewRectF.left + (previewHeight - previewTextWidth) / 2,
                        previewRectF.top + mPreviewPadding - previewTextPaint.ascent(),
                        previewTextPaint);
            }

            // 绘制索引栏内的索引目录
            Paint indexPaint = new Paint();
            indexPaint.setColor(Color.WHITE);
            indexPaint.setAlpha((int) (255 * mAlphaRate));
            indexPaint.setAntiAlias(true);
            indexPaint.setTextSize(20 * mScaledDensity);

            float sectionHeight = (mIndexbarRectF.height() - 2 * mIndexbarMargin) / mSection.length;
            float sectionPaddingTop = (sectionHeight - (indexPaint.descent() - indexPaint.ascent())) / 2;

            for (int i = 0; i < mSection.length; i++) {
                float paddingLeft = (mIndexbarWidth - indexPaint.measureText(mSection[i])) / 2;
                canvas.drawText(
                        mSection[i],
                        mIndexbarRectF.left + paddingLeft,
                        mIndexbarRectF.top + mIndexbarMargin + sectionHeight * i + sectionPaddingTop - indexPaint.ascent(),
                        indexPaint);
            }

        }

    }
    boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (mState != STATE_HIDDEN && contains(event.getX(), event.getY())) {
                    setState(STATE_SHOWN);
                    mIsIndexing = true;
                    mCurrentSection = getSectionByPoint(event.getY());
                    mListView.setSelection(mIndexer.getPositionForSection(mCurrentSection));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsIndexing) {
                    if (contains(event.getX(), event.getY())) {
                        mCurrentSection = getSectionByPoint(event.getY());
                        mListView.setSelection(mIndexer.getPositionForSection(mCurrentSection));
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mIsIndexing) {
                    mIsIndexing = false;
                    mCurrentSection = -1;
                }
                if (mState == STATE_SHOWN)
                    setState(STATE_HIDING);
                break;
        }
        return false;
    }

    void onSizeChanged(int w, int h, int oldw, int oldh) {
        mListViewWidth = w;
        mListViewHeight = h;
        mIndexbarRectF = new RectF(
                w - mIndexbarMargin - mIndexbarWidth,
                mIndexbarMargin,
                w - mIndexbarMargin,
                h - mIndexbarMargin);
    }

    void hide() {
        if (mState == STATE_SHOWN) mState = STATE_HIDDEN;
    }
    void show() {
        if (mState == STATE_HIDDEN)
            setState(STATE_SHOWING);
        else if (mState == STATE_HIDING)
            setState(STATE_HIDING);
    }

    private void setState(int state){
        if (state != STATE_SHOWN && state != STATE_HIDDEN && state != STATE_SHOWING && state != STATE_HIDING){
            return;
        }
        mState = state;
        switch (state){
            case STATE_SHOWN:
                mHandler.removeMessages(0);
                break;
            case STATE_HIDDEN:
                mHandler.removeMessages(0);
                break;
            case STATE_SHOWING:
                mAlphaRate = 0;
                fade(0);
                break;
            case STATE_HIDING:
                mAlphaRate = 1;
                fade(3000);
                break;
        }
    }

    private void fade(int i) {
        mHandler.removeMessages(0);
        mHandler.sendEmptyMessageAtTime(0, SystemClock.uptimeMillis() + i);
    }

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (mState){
                case STATE_SHOWN:
                    setState(STATE_HIDING);
                    break;
                case STATE_SHOWING:
                    mAlphaRate += (1 - mAlphaRate) * 0.2;
                    if (mAlphaRate > 0.9){
                        mAlphaRate = 1;
                        setState(STATE_SHOWN);
                    }
                    mListView.invalidate();
                    fade(10);
                    break;
                case STATE_HIDING:
                    mAlphaRate -= mAlphaRate * 0.2;
                    if (mAlphaRate < 0.1) {
                        mAlphaRate = 0;
                        setState(STATE_HIDDEN);
                    }
                    mListView.invalidate();
                    fade(10);
                    break;
            }
        }
    };


    private boolean contains(float x, float y) {
        return (x >= mIndexbarRectF.left && y >= mIndexbarRectF.top && y <= mIndexbarRectF.top + mIndexbarRectF.height());
    }

    private int getSectionByPoint(float y) {

        if (mSection == null || mSection.length == 0) return 0;

        if (y < mIndexbarRectF.top + mIndexbarMargin) return 0;

        if (y >= mIndexbarRectF.top + mIndexbarRectF.height() - mIndexbarMargin)  return mSection.length - 1;

        return (int) ((y - mIndexbarRectF.top - mIndexbarMargin) / ((mIndexbarRectF.height() - 2 * mIndexbarMargin) / mSection.length));
    }


}
