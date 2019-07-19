package com.example.teamwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class UserCenter extends AppCompatActivity implements View.OnClickListener {
    //      定义变量
    private Button backButton;
    private Button PLAY;
    private Button PAUSE;
    private Intent intent;
    private static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        init();
    }

    //    变量初始化
    protected void init() {
        backButton = findViewById(R.id.back);
        PLAY = findViewById(R.id.PLAY);
        PAUSE = findViewById(R.id.PAUSE);
//        事件注册
        backButton.setOnClickListener(this);
        PLAY.setOnClickListener(this);
        PAUSE.setOnClickListener(this);
    }
//    事件监听

    public static void playMusic(Context context) {
//        Intent intent = new Intent(this, MyService.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("Key", MyService.Control.PLAY);
//        intent.putExtras(bundle);
//        startService(intent);
        try{
            mediaPlayer= MediaPlayer.create(context,R.raw.music);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        }catch (Exception e){
            Log.e("UserCenter","播放失败");
        }

    }

    public void pauseMusic(View view) {
        Intent intent = new Intent(this, MyService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Key", MyService.Control.PAUSE);
        intent.putExtras(bundle);
        startService(intent);
    }

    public void stopMusic() {
//        Intent intent = new Intent(this, MyService.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("Key", MyService.Control.STOP);
//        intent.putExtras(bundle);
//        startService(intent);
        //或者是直接如下调用
        //Intent intent = new Intent(this, MyService.class);
        //stopService(intent);
        if (null!=mediaPlayer){
            mediaPlayer.stop();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.PLAY:
                playMusic(this);
                break;
            case R.id.PAUSE:
                stopMusic();

                break;
            default:
                Intent intent1 = new Intent(Intent.ACTION_MAIN);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                mediaPlayer.stop();
                break;


        }
    }
}
