package com.example.mao.animation.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mao.animation.R;

/**
 * Created by maohh on 2016/8/18.
 */
public class FragmentThree extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_string, container, false);
    }

    public static Fragment newInstance() {
        FragmentThree fragment = new FragmentThree();
        return fragment;
    }
}
