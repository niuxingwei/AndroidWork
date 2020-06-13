package com.example.teamwork;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NavBar extends AppCompatActivity implements View.OnClickListener {
    private Button video, music, exit, shake, call, photo;
    //定义进入拍照按钮界面
    private TextView Navtime;
    private Intent intent;
    //        确定退出动作，返回桌面
    private DialogInterface.OnClickListener okButton = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent1 = new Intent(Intent.ACTION_MAIN);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent1);

        }
    };
    //    取消注销动作，留在当前的界面
    private DialogInterface.OnClickListener cancleButton = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);
        init();
    }

    //    初始化
    public void init() {
        video = findViewById(R.id.NAV_VIEDO);
        music = findViewById(R.id.NAV_MUSIC);
        exit = findViewById(R.id.NAV_EXIT);
        Navtime = findViewById(R.id.NAVTime);
        photo = findViewById(R.id.takePhoto);
        shake = findViewById(R.id.NAV_SHAKE);
        call = findViewById(R.id.NAV_CALL);
//    事件注册
        video.setOnClickListener(this);
        music.setOnClickListener(this);
        exit.setOnClickListener(this);
        photo.setOnClickListener(this);
        shake.setOnClickListener(this);
        call.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.NAV_MUSIC:
                Toast.makeText(NavBar.this, "Successful! Please Wait···", Toast.LENGTH_SHORT).show();
                intent = new Intent(NavBar.this, UserCenter.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.NAV_VIEDO:
                Toast.makeText(NavBar.this, "Successful! Please Wait···", Toast.LENGTH_SHORT).show();
                intent = new Intent(NavBar.this, UserCenter_video.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.takePhoto:
                Toast.makeText(NavBar.this, "Successful! Please Wait···", Toast.LENGTH_SHORT).show();
                intent = new Intent(NavBar.this, takePhoto.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.NAV_CALL:
                intent = new Intent(NavBar.this, FunctionView.class);
                startActivity(intent);
                break;
            case R.id.NAV_SHAKE:
                intent = new Intent(NavBar.this, shark.class);
                startActivity(intent);
                break;
            default:
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
                alertdialog.setMessage("Are you sure to exit？");
                alertdialog.setPositiveButton("Yes", okButton);
                alertdialog.setNegativeButton("No", cancleButton);
                AlertDialog alertDialog = alertdialog.create();
                alertDialog.show();

        }

    }

}
