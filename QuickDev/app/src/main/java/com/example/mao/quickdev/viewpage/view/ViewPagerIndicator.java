package com.example.mao.quickdev.viewpage.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mao.quickdev.R;

import java.math.BigDecimal;
import java.util.List;


public class ViewPagerIndicator extends LinearLayout {
    private Paint mPaint;
    private Path mPath;
    private int mTrangleWidth;
    private int mTrangleHeight;
    private int mTranslationX;
    private int mInitTranslationX;
    private int mVisibleTabCount;

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;

    private static final float RADIO_TRANGLE_WIDTH = 1 / 7f;
    private static final int TAB_COUNT_DEFAULT = 4;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mVisibleTabCount = ta.getInt(R.styleable.ViewPagerIndicator_visible_tab_count, TAB_COUNT_DEFAULT);
        if (mVisibleTabCount < 0) {
            mVisibleTabCount = TAB_COUNT_DEFAULT;
        }
        ta.recycle();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 1);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

        super.dispatchDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTrangleWidth = (int) (w / mVisibleTabCount * RADIO_TRANGLE_WIDTH);
        mInitTranslationX = w / mVisibleTabCount / 2 - mTrangleWidth / 2;
        initTrangle();
    }

    private void initTrangle() {
        mTrangleHeight = mTrangleWidth / 2;
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTrangleWidth, 0);
        mPath.lineTo(mTrangleWidth / 2, -mTrangleHeight);
        mPath.close();
    }


    public void scroll(int position, float offset) {
        int tabWidth = getWidth() / mVisibleTabCount;
        mTranslationX = (int) (tabWidth * (position + offset));
        int tabCount = getChildCount();
        if ((position >= (mVisibleTabCount - 2)) && position <= tabCount - 2 && offset > 0 && tabCount > mVisibleTabCount) {
            if (mVisibleTabCount != 1) {
                this.scrollTo((int) (tabWidth * (position - 2 + offset)), 0);
            } else {
                this.scrollTo((int) (tabWidth * (position + offset)), 0);
            }
        }
        invalidate();
    }



    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int tabCount = getChildCount();
        if (tabCount == 0) return;
        for (int i = 0; i < tabCount; i++) {
            View view = getChildAt(i);
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.weight = 0;
            lp.width = getSrceenWidth() / mVisibleTabCount;
            view.setLayoutParams(lp);
        }
        setItemClickEvent();
    }

    public int getSrceenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public void setVisibleTabCount(int count) {
        this.mVisibleTabCount = count;
    }

    public void setTabItemTitles(List<String> titles) {
        if (titles != null && titles.size() > 0) {
            this.removeAllViews();
            for (String title : titles) {
                addView(generateTitle(title));
            }
        }
        setItemClickEvent();
    }

    private View generateTitle(String title) {
        TextView tv = new TextView(getContext());
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.width = getSrceenWidth() / mVisibleTabCount;
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(0x77ffffff);
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(lp);
        return tv;
    }

    public void setViewPager(ViewPager viewPager, int position){
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position,positionOffset);
                if (mListener != null){
                    mListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (mListener != null){
                    mListener.onPageSelected(position);
                }
                highLightTextView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mListener != null){
                    mListener.onPageScrollStateChanged(state);
                }
            }
        });
        mViewPager.setCurrentItem(position);
        highLightTextView(position);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener){
        mListener = listener;
    }

    public void highLightTextView(int position){
        for (int i = 0; i < getChildCount(); i++){
            View tv = getChildAt(i);
            if (tv instanceof TextView){
                ((TextView) tv).setTextColor(0x77ffffff);
            }
        }
        View view = getChildAt(position);
        if (view instanceof TextView){
            ((TextView) view).setTextColor(0xffffffff);
        }
    }

    public void setItemClickEvent(){
        int count = getChildCount();
        for (int i = 0; i < count;i++){
            final int j = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }

}
