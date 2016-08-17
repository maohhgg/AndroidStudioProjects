package com.example.mao.animation;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Window;
import android.widget.ImageView;

import com.example.mao.animation.Adapter.SectionsPagerAdapter;
import com.squareup.picasso.Picasso;

public class DetailTabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);


        Bundle bundle = getIntent().getExtras();

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        ViewCompat.setTransitionName(imageView, "img");

        Picasso.with(imageView.getContext()).load(bundle.getString("img")).into(imageView);
    }

}
