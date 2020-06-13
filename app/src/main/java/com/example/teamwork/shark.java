package com.example.teamwork;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

public class shark extends AppCompatActivity implements SensorEventListener {

    //震动
    Vibrator vibrator = null;
    //显示摇一摇
    TextView textView = null;
    // Sensor管理器
    SensorManager sm = null;
    //检测抖动次数
    int times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shark);
        textView = findViewById(R.id.sharkTest);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sm != null) {//注册监听器
            sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sm != null) {//取消监听器
            sm.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] value = event.values;

        Log.i("X轴数据", (value[0]) + "; Y轴数据" + (value[1]) + "; Z轴数据" + (value[2]));
        if (Math.abs(value[0]) > 18 || Math.abs(value[1]) > 18 || Math.abs(value[2]) > 18) {
            textView.setText("手机抖动" + (times++) + "次啦");
            vibrator.vibrate(2000);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}