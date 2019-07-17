package com.example.teamwork;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity implements View.OnClickListener {
    //    定义登录、注册、注销三变量
    private Button loginButton;
    private Button registerButton;
    private Button cancelButton;
    private Intent intent;

    //        确定注销动作，返回桌面
    private DialogInterface.OnClickListener okButton = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            intent.setAction(Intent.ACTION_MAIN);//设置动作
            intent.addCategory(Intent.CATEGORY_HOME);//设置动作种类
            startActivity(intent);

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
        setContentView(R.layout.activity_login);
        init();
    }

    //    变量初始化
    public void init() {
        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.register);
        cancelButton = findViewById(R.id.login_btn_cancle);
//    事件注册
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);


    }

    //     事件监听
    @Override
    public void onClick(View view) {
//        点击事件区分
        switch (view.getId()) {
//            去往登录界面
            case R.id.login:
                intent = new Intent(Login.this, UserCenter.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
//                去往注册界面
            case R.id.register:
                intent = new Intent(Login.this, Sign.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
//                去往注销界面
            case R.id.login_btn_cancle:
//                是否确定注销
                AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
                alertdialog.setMessage("您确定要注销吗？");
                alertdialog.setPositiveButton("确定", okButton);
                alertdialog.setNegativeButton("取消", cancleButton);
                AlertDialog alertDialog = alertdialog.create();
                alertDialog.show();
            default:
                break;
        }
    }
}
