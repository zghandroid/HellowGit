package com.zhang.hellowgit;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Vitamio 第三方
 * 做一个简单的视频播放
 * VideoView
 */
public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, CompoundButton.OnCheckedChangeListener, View.OnTouchListener {

    private VideoView video;
    //声明一个媒体控制器
    private MediaController controller;
    private CheckBox paly;
    private TextView startTime;
    private SeekBar seek;
    private TextView totalTime;
    private CheckBox Full;
    private boolean isPare;
    private LinearLayout conterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        controller = new MediaController(this);
        //实例化一个媒体控制器
        video = (VideoView) findViewById(R.id.video);
        //设置媒体控制器
   //     video.setMediaController(controller);
        //为VideoView设置播放的uri  耗时的
//        video.setVideoURI(Uri.parse("http://7rflo2.com2.z0.glb.qiniucdn.com/5714b0b53c958.mp4"));

//        video.start();
        //设置准备好了的listener
        video.setOnPreparedListener(this);
        video.setOnTouchListener(this);
        int height = getResources().getDisplayMetrics().heightPixels;
        video.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height / 3));
        new Thread() {
            @Override
            public void run() {
                video.setVideoURI(Uri.parse("http://7rflo2.com2.z0.glb.qiniucdn.com/5714b0b53c958.mp4"));
                isPare=true;
            }
        }.start();
        paly = (CheckBox) findViewById(R.id.paly);
        startTime = (TextView) findViewById(R.id.palyer_start_time);
        seek = (SeekBar) findViewById(R.id.palyer_seek);
        totalTime = (TextView) findViewById(R.id.palyer_total_time);
        Full = (CheckBox) findViewById(R.id.palyer_full);
        conterView= (LinearLayout) findViewById(R.id.conter_view);
        Full.setOnCheckedChangeListener(this);
        paly.setOnCheckedChangeListener(this);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        video.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        video.start();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()){
            case R.id.palyer_full:
                if(isChecked){
                    //添加一个全屏的标记
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    //请求全屏
                    //requestWindowFeature(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }else{
                    //清除全屏的标记
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                break;
            case R.id.paly:
                if(isPare&&isChecked){
                    video.start();
                }else if(isPare&&!isChecked){
                    video.pause();
                }

                break;
        }


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (conterView.getVisibility()== View.VISIBLE) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.conler);
            conterView.startAnimation(animation);
            conterView.setVisibility(View.GONE);
        }else {
            conterView.setVisibility(View.VISIBLE);
        }

        return false;
    }
}
