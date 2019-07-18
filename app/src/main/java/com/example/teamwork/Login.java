package com.example.teamwork;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {
    //    定义登录、注册、注销三变量
    private Button loginButton;
    private Button registerButton;
    private Button cancelButton;
    private Intent intent;
//    定义输入的用户名和密码两个变量
    private EditText loginAccount;
    private EditText loginPwd;

    //        确定注销动作，返回桌面
    private DialogInterface.OnClickListener okButton = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent1= new Intent(Intent.ACTION_MAIN);
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
        setContentView(R.layout.activity_login);
        init();
    }

    //    变量初始化
    public void init() {
        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.register);
        cancelButton = findViewById(R.id.login_btn_cancle);
        loginAccount= findViewById(R.id.login_edit_account);
        loginPwd = findViewById(R.id.login_edit_pwd);
//    事件注册
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);


    }

    //     事件监听
    @Override
    public void onClick(View view) {
//        获取用户名和密码输入的内容
        String name = loginAccount.getText().toString();
        String pwd = loginPwd.getText().toString();
//        点击事件区分
        switch (view.getId()) {
//            去往登录界面
            case R.id.login:
//                获取输入框的信息进行下一步状态判断
                if (name.length()<1||pwd.length()<1){
                    Toast.makeText(Login.this, "请输入您的用户名或者密码",Toast.LENGTH_LONG).show();
                }
//                创建文件保存信息
                else {
                    /*
                    后续检测代码

                     */
                    intent = new Intent(Login.this, UserCenter.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                }
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
                alertdialog.setMessage("您确定要退出吗？");
                alertdialog.setPositiveButton("确定", okButton);
                alertdialog.setNegativeButton("取消", cancleButton);
                AlertDialog alertDialog = alertdialog.create();
                alertDialog.show();
            default:
                break;
        }
    }
}
