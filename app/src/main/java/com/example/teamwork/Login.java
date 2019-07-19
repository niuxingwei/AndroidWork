package com.example.teamwork;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Login extends AppCompatActivity implements View.OnClickListener {
    //    定义登录、注册、注销三变量
    private Button loginButton;
    private Button registerButton;
    private Button cancelButton;
    private Intent intent;
    //    定义输入的用户名和密码两个变量
    private EditText loginAccount;
    private EditText loginPwd;
    private CheckBox savePasswordCB;
    private TextView ShowTime;
//    private int count = 0;

    //        确定退出动作，返回桌面
    private DialogInterface.OnClickListener okButton = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            SharedPreferences sh = getSharedPreferences("SavedData", MODE_PRIVATE);
            savePasswordCB = findViewById(R.id.savePasswordCB);
            if (savePasswordCB.isChecked()) {
                loginAccount.setText(sh.getString("user_name", ""));
                loginPwd.setText(sh.getString("user_pwd", ""));

                Log.e("MainActivity", "已选择记住密码");
            } else {
                loginAccount.setText(null);
                loginPwd.setText(null);
                Log.e("MainActivity", "未选择记住密码");
            }
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

//        提示第一次登录需要注册
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setMessage("You need register if you are newuser");
        alertdialog.setNegativeButton("Got it!", cancleButton);
        AlertDialog alertDialog = alertdialog.create();
//        Window window = alertDialog.getWindow();
//        window.setBackgroundDrawable(new ColorDrawable(3));
        alertDialog.show();
        setContentView(R.layout.activity_login);
        init();
    }

    //    变量初始化
    public void init() {
        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.register);
        cancelButton = findViewById(R.id.login_btn_cancle);
        loginAccount = findViewById(R.id.login_edit_account);
        loginPwd = findViewById(R.id.login_edit_pwd);
        ShowTime = findViewById(R.id.ShowTime);



//    事件注册
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        ShowTime.setOnClickListener(this);

//        从后台获取是否记住密码状态
//        SharedPreferences sh= getSharedPreferences("SavedData",MODE_PRIVATE);
//        boolean isRemember = sh.getBoolean(savePasswordCB,false)
    }


    //     事件监听
    @Override
    public void onClick(View view) {
//        获取用户名和密码输入的内容
        String name = loginAccount.getText().toString();
        Log.e("AppCompatActivity", "登陆界面传值" + name);
        String pwd = loginPwd.getText().toString();
//        点击事件区分
        switch (view.getId()) {
//            去往登录界面
            case R.id.login:
//                获取输入框的信息进行下一步状态判断
                if (name.length() < 1 || pwd.length() < 1) {
                    Toast.makeText(Login.this, "Please input your name or password", Toast.LENGTH_LONG).show();
                } else if (name.length() > 1 && pwd.length() > 1) {
                    //                    注册界面传递的数据和登录信息匹配
                    //                创建文件保存信息

                    SharedPreferences shared = getSharedPreferences("SavedData", MODE_PRIVATE);
                    String username = shared.getString("user_name", "");
                    Log.e("AppCompatActivity", "注册界面传值" + username);
                    String password = shared.getString("user_pwd", "");
                    if (username.equals(name) && pwd.equals(password)) {
                          /*
                    后续检测代码
                     */
                        Toast.makeText(Login.this, "Successful! Please Wait···", Toast.LENGTH_LONG).show();
                        intent = new Intent(Login.this, NavBar.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    } else {
                        Toast.makeText(Login.this, "Please check your username or password", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(Login.this, "Please register firstly!", Toast.LENGTH_LONG).show();
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
                alertdialog.setMessage("Are you sure to exit？");
                alertdialog.setPositiveButton("Srue", okButton);
                alertdialog.setNegativeButton("Cancle", cancleButton);
                AlertDialog alertDialog = alertdialog.create();
                alertDialog.show();
                break;
            case R.id.ShowTime:
//                获取当前时间
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                AlertDialog.Builder TimeDialog = new AlertDialog.Builder(this);
                TimeDialog.setMessage(simpleDateFormat.format(date));
                TimeDialog.setPositiveButton("Thanks", cancleButton);
                AlertDialog alertDialg = TimeDialog.create();
                alertDialg.show();
                break;
            default:
                break;
        }
    }

//    //    检测用户名是否存在
//    public boolean Exit(String name) {
//
//        String namea[] = new String[10];//最多保存十名用户
//
//
//    }
}
