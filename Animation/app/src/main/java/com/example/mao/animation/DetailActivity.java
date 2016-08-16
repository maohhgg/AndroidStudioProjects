package com.example.mao.animation;

import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());

        setContentView(R.layout.activity_detail);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView textViewDetail = (TextView) findViewById(R.id.textViewDetail);

        ViewCompat.setTransitionName(imageView, "img");
        ViewCompat.setTransitionName(textViewDetail, "title");

        textViewDetail.setText(bundle.getString("title"));
        Picasso.with(imageView.getContext()).load(bundle.getString("img")).into(imageView);
    }
}
