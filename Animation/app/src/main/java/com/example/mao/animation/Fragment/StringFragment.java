package com.example.mao.animation.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mao.animation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StringFragment extends Fragment {


    public StringFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_string, container, false);
    }

    public static Fragment newInstance() {
        StringFragment fragment = new StringFragment();
        return fragment;
    }
}
