package com.example.mao.quickdev.viewpageranmi;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mao.quickdev.R;
import com.example.mao.quickdev.viewpageranmi.transformer.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class DepthActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private int[] mImages = new int[]{R.mipmap.guide_image1,R.mipmap.guide_image2,R.mipmap.guide_image3};
    private List<ImageView> mImageViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anima_item);


        mViewPager = (ViewPager) findViewById(R.id.view_page);
        mViewPager.setPageTransformer(true,new DepthPageTransformer());
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(DepthActivity.this);
                imageView.setImageResource(mImages[position]);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(imageView);
                mImageViews.add(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImageViews.get(position));
            }

            @Override
            public int getCount() {
                return mImages.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
    }
}
