package com.example.mao.quickdev.progressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.example.mao.quickdev.R;


public class RoundProgressBar extends HorizontalProgressBar {
    private int mRadius = dp2px(50);
    private int mMaxPaintWidth;

    public RoundProgressBar(Context context) {
        super(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRechHeight = (int) (mUnRechHeight * 2.5f);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        mRadius = (int) ta.getDimension(R.styleable.RoundProgressBar_radius, mRadius);
        ta.recycle();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMaxPaintWidth = Math.max(mUnRechHeight, mRechHeight);
        int expect = mRadius * 2 + mMaxPaintWidth + getPaddingLeft() + getPaddingRight();
        int width = resolveSize(expect, widthMeasureSpec);
        int height = resolveSize(expect, heightMeasureSpec);

        int readWidth = Math.max(width, height);
        mRadius = (readWidth - getPaddingRight() - getPaddingLeft()) / 2;
        setMeasuredDimension(readWidth, readWidth);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        String text = getProgress() + "%";
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;

        canvas.save();

        canvas.translate(getPaddingLeft() + mMaxPaintWidth / 2, getPaddingTop() + mMaxPaintWidth / 2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mUnRechColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mUnRechHeight);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);

        float sweepAngle = getProgress() * 1.0f / getMax() * 360;
        mPaint.setColor(mRechColor);
        mPaint.setStrokeWidth(mRechHeight);
        mPaint.setAntiAlias(true);
        canvas.drawArc(new RectF(0, 0, mRadius * 2, mRadius * 2), 0, sweepAngle, false, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mTextColor);
        mPaint.setAntiAlias(true);
        canvas.drawText(text, mRadius - textWidth / 2, mRadius - textHeight, mPaint);

        canvas.restore();
    }
}
