package com.example.mao.quickdev.viewpage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mao.quickdev.R;
import com.example.mao.quickdev.viewpage.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewPageActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;
    private List<String> mTitle = Arrays.asList("Item1", "Item2", "Item3", "Item4", "Item5", "Item6", "Item7", "Item8", "Item9", "Item10");
    private List<VpSimpleFragment> mContents = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);

        initViews();
        initDatas();

        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mIndicator.setVisibleTabCount(4);
        mIndicator.setTabItemTitles(mTitle);
    }

    private void initDatas() {
        for (String title : mTitle) {
            VpSimpleFragment fragment = VpSimpleFragment.newInstance(title);
            mContents.add(fragment);
        }
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.view_page);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.view_page_indicator);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };
    }
}
