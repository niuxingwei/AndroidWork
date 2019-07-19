package com.example.teamwork;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserCenter extends AppCompatActivity implements View.OnClickListener {
    //      定义变量
    private Button backButton;
    private Button PLAY;
    private Button PAUSE;
    private Button StopButton;
    private Intent intent;
    private TextView textView;
    private static MediaPlayer mediaPlayer;
    private MyService service;

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
        textView = findViewById(R.id.center_title);
        StopButton= findViewById(R.id.STOP);
//        事件注册
        backButton.setOnClickListener(this);
        PLAY.setOnClickListener(this);
        PAUSE.setOnClickListener(this);
        StopButton.setOnClickListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences("SavedData", MODE_PRIVATE);
        String WordMessaGe = sharedPreferences.getString("user_name", null);
        textView.setText(WordMessaGe + ",欢迎您!");
    }
//    事件监听

//    public static void playMusic(Context context) {
//        try {
//            mediaPlayer = MediaPlayer.create(context, R.raw.music);
//            mediaPlayer.start();
//            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mediaPlayer) {
//                    mediaPlayer.start();
//                }
//            });
//        } catch (Exception e) {
//            Log.e("UserCenter", "播放失败");
//        }
//
//    }

//    public void stopMusic() {
//        if (null != mediaPlayer) {
//            mediaPlayer.stop();
//        }
//    }
//
//    public void pauseMusic() {
//        if (null != mediaPlayer) {
//            mediaPlayer.pause();
//        }
//    }

    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.MyBind bind = (MyService.MyBind) iBinder;
            service = bind.getMyService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MyService.class);
        switch (view.getId()) {
            case R.id.PLAY:

                bindService(intent, sc, BIND_AUTO_CREATE);
                if (service != null) {
                    service.playMusic(UserCenter.this);
                }
                break;
            case R.id.PAUSE:
                bindService(intent, sc, BIND_AUTO_CREATE);
                service.pauseMusic();
                break;
            case R.id.STOP:
                bindService(intent, sc, BIND_AUTO_CREATE);
                service.stopMusic();
            default:
                Intent intent1 = new Intent(Intent.ACTION_MAIN);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                bindService(intent, sc, BIND_AUTO_CREATE);
                break;

        }
    }
}
