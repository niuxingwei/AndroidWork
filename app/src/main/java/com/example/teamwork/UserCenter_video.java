package com.example.teamwork;


import androidx.appcompat.app.AppCompatActivity;


import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;

import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;



public class UserCenter_video extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
//         导包的快捷键 alt + enter

    private TextView mTextView;
    private Button mButton;
    private Button mGoButton;
    private SeekBar mSeekBar;
    private VideoView mVideoView;
    private Button minusvolume;
    private Button plusvolume;
    private TextView volume;
    private TextView text;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e("sc", "连接成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("sc", "连接失败");
        }
    };

    public void init() {
//            findViewById()方法找到对应空间
//            构造方法舒适化参数
//            R 文件存储项目中所有的资源文件， 完全不需要更改，存储数据的方式都是以int类型保存
        mTextView = findViewById(R.id.Text_View);
        mButton = findViewById(R.id.Button);
        mSeekBar = findViewById(R.id.SeekBar);
        mVideoView = findViewById(R.id.Video_view);
//        设置音量初始化
        plusvolume = findViewById(R.id.plus);
        minusvolume = findViewById(R.id.minus);
        volume = findViewById(R.id.volume);

//        指定播放的视频
        String uri = "android.resource://" + getPackageName() + "/raw/" + R.raw.video;
        mVideoView.setVideoURI(Uri.parse(uri));
//        mButton.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
        mButton.setOnClickListener(this);
        plusvolume.setOnClickListener(this);
        minusvolume.setOnClickListener(this);
    }

    //         创建当前activity对应的xml布局
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.my_activity_land);
            Log.e("onServiceConnected","横屏");
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_main_);
            Log.e("onServiceConnected","薯片");
        }
        Log.e("MainActivity", "onResume()");
        init();

    }

    //    显示布局
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MainActivity", "onStart()");
    }

    //    可以进行交互activity和xml交互
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MainActivity", "onStart()");
    }

    //    进行一系列的数据保存
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("MainActivity", "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MainActivity", "onStop");
    }

    //    销毁
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //    从不可见到可见的方法
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    //       在UI主线程中不可以进行任何复杂的操作
    @Override
    public void onClick(View view) {
//      开启子线程，更新进度
        //      点击出现对话框
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        获取最大音量和当前音量
        int maxVolum = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int minVolum = audioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC);
        switch (view.getId()) {
            case R.id.Button: {
                mTextView.setText("欢迎观看视频");
                //开始播放
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    mButton.setText("▶");
                } else {
                    mVideoView.start();
                    mButton.setText("||");
                }

                Log.e("time", mVideoView.getDuration() + " ");
                mSeekBar.setMax(mVideoView.getDuration());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (mVideoView.isPlaying()) {

                            int progress = mVideoView.getCurrentPosition();
                            Message message = new Message();
                            message.arg1 = progress;
                            myHandler.sendMessage(message);
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            break;
            case R.id.plus: {
                int currentVolum = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                if (currentVolum <= maxVolum) {
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FX_FOCUS_NAVIGATION_UP);
                    volume.setText(String.valueOf(currentVolum));
                }
            }
            break;
            case R.id.minus: {
                int currentVolum = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                if (currentVolum >= minVolum) {
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FX_FOCUS_NAVIGATION_UP);
                    volume.setText(String.valueOf(currentVolum));
                }
            }
            break;
        }
    }

    private Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //在主线程中更新进度
            mSeekBar.setProgress(msg.arg1);
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        if (mVideoView.isPlaying()){
            int position = seekBar.getProgress();
            mVideoView.seekTo(position);
        }
    }

}




