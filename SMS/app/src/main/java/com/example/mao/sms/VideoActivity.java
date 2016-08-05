package com.example.mao.sms;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import java.io.File;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = (VideoView) findViewById(R.id.video_view);
        findViewById(R.id.video_play).setOnClickListener(this);
        findViewById(R.id.video_stop).setOnClickListener(this);
        findViewById(R.id.video_pause).setOnClickListener(this);
        initVideoPath();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null){
            videoView.suspend();
        }
    }

    public void initVideoPath(){
        File file = new File(Environment.getExternalStorageDirectory(),"1.mp4");
        videoView.setVideoPath(file.getPath());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.video_play:
                if (!videoView.isPlaying()){
                    videoView.start();
                }
            break;
            case R.id.video_stop:
                if (videoView.isPlaying()){
                    videoView.start();
                }
                break;
            case R.id.video_pause:
                if (videoView.isPlaying()){
                    videoView.pause();
                }
                break;
        }
    }
}
