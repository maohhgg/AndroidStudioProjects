package com.example.mao.quickdev.progressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.example.mao.quickdev.R;


public class HorizontalProgressBar extends ProgressBar {

    private static final int DEFAULT_TEXT_COLOR = 0xFF000000;
    private static final int DEFAULT_TEXT_SIZE = 12;
    private static final int DEFAULT_TEXT_OFFSET = 5;
    private static final int DEFAULT_UNRECH_COLOR = 0xFF00C853;
    private static final int DEFAULT_RECH_COLOR = 0xFFE65100;
    private static final int DEFAULT_RECH_HEIGHT = 2;
    private static final int DEFAULT_UNRECH_HEIGHT = 2;

    protected int mTextColor = DEFAULT_TEXT_COLOR;
    protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    protected int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);
    protected int mUnRechColor = DEFAULT_UNRECH_COLOR;
    protected int mRechColor = DEFAULT_RECH_COLOR;
    protected int mRechHeight = dp2px(DEFAULT_RECH_HEIGHT);
    protected int mUnRechHeight = dp2px(DEFAULT_UNRECH_HEIGHT);

    protected Paint mPaint = new Paint();
    private int mRealWidth;

    public HorizontalProgressBar(Context context) {
        this(context, null);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttrs(attrs);
    }

    private void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalProgressBar);

        mTextColor = ta.getColor(R.styleable.HorizontalProgressBar_progress_text_color, mTextColor);
        mTextSize = (int) ta.getDimension(R.styleable.HorizontalProgressBar_progress_text_size, mTextSize);
        mTextOffset = (int) ta.getDimension(R.styleable.HorizontalProgressBar_progress_text_offset, mTextOffset);
        mUnRechColor = ta.getColor(R.styleable.HorizontalProgressBar_progress_unrech_color, mUnRechColor);
        mUnRechHeight = (int) ta.getDimension(R.styleable.HorizontalProgressBar_progress_unrech_height, mUnRechHeight);
        mRechColor = ta.getColor(R.styleable.HorizontalProgressBar_progress_rech_color, mRechColor);
        mRechHeight = (int) ta.getDimension(R.styleable.HorizontalProgressBar_progress_rech_height, mRechHeight);

        ta.recycle();

        mPaint.setTextSize(mTextSize);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasuredHeight(heightMeasureSpec);

        setMeasuredDimension(measureWidth, measureHeight);
        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();

        canvas.translate(getPaddingLeft(), getHeight() / 2);
        boolean isNeedUnRech = true;

        String text = getProgress() + "%";
        float radio = getProgress() * 1.0f / getMax();
        int textWidth = (int) mPaint.measureText(text);
        float progressX = radio * mRealWidth;

        if (progressX + textWidth > mRealWidth) {
            isNeedUnRech = false;
            progressX = mRealWidth - textWidth;
        }

        if (progressX > 0) {
            float endX = progressX - mTextOffset / 2;
            mPaint.setColor(mRechColor);
            mPaint.setStrokeWidth(mRechHeight);
            canvas.drawLine(0, 0, endX, 0, mPaint);
        }

        mPaint.setColor(mTextColor);
        float y = (int) (-(mPaint.ascent() + mPaint.descent()) / 2);
        float textStartX = progressX;
        canvas.drawText(text, textStartX, y, mPaint);


        if (isNeedUnRech) {
            float startX = progressX + mTextOffset / 2 + textWidth;
            mPaint.setColor(mUnRechColor);
            mPaint.setStrokeWidth(mUnRechHeight);
            canvas.drawLine(startX, 0, mRealWidth, 0, mPaint);
        }

        canvas.restore();
    }

    private int MeasuredHeight(int heightMeasureSpec) {
        int result;
        int mod = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (mod == MeasureSpec.EXACTLY) {
            result = height;
        } else {
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());
            result = getPaddingTop()
                    + getPaddingBottom()
                    + Math.max(
                    Math.max(mRechHeight, mUnRechHeight),
                    Math.abs(textHeight));

            if (mod == MeasureSpec.AT_MOST) {
                result = Math.min(height, result);
            }
        }

        return result;
    }

    protected int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    protected int dp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sp, getResources().getDisplayMetrics());
    }
}
