package com.example.mao.animation.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mao.animation.Fragment.FragmentThree;
import com.example.mao.animation.Fragment.FragmentTwo;
import com.example.mao.animation.Fragment.StringFragment;

/**
 * Created by maohh on 2016/8/18.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return StringFragment.newInstance();
            case 1:
                return FragmentTwo.newInstance();
            case 2:
                return FragmentThree.newInstance();
            default:
                return StringFragment.newInstance();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "StringFragment";
            case 1:
                return "FragmentTwo";
            case 2:
                return "FragmentThree";
        }
        return null;
    }
    @Override
    public int getCount() {
        return 3;
    }
}
