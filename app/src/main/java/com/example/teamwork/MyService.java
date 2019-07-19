package com.example.teamwork;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private final String TAG = "MyService";

    private  MediaPlayer mediaPlayer;

    public MyService() {
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBind();
    }

    //    播放音乐
    public  void playMusic(Context context) {
        try {
            mediaPlayer = MediaPlayer.create(context, R.raw.music);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            Log.e("UserCenter", "播放失败");
        }

    }

    //    暂停音乐
    public void stopMusic() {
        if (null != mediaPlayer) {
            mediaPlayer.stop();
        }
    }

    //    停止音乐
    public void pauseMusic() {

        if (null != mediaPlayer) {
            mediaPlayer.pause();
        }
    }

    class MyBind extends Binder{
        public MyService getMyService(){
            return MyService.this;
        }
    }
}
