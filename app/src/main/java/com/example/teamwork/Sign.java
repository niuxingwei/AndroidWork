package com.example.teamwork;
//注册界面后端代码

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Sign extends AppCompatActivity implements View.OnClickListener {
    //    定义成员变量
    private Button SureButton;
    private Button CancleButton;
    private Intent intent;
    private EditText signmUser_name;
    private EditText signUser_pwd;
    private EditText signRepet_pwd;
    private TextView timeText;


    //函数入口
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        init();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        timeText.setText(simpleDateFormat.format(date));
    }

    //    初始化成员变量
    public void init() {
        SureButton = findViewById(R.id.register_btn_sure);
        CancleButton = findViewById(R.id.register_btn_cancel);
        signmUser_name = findViewById(R.id.resetpwd_edit_name);
        signUser_pwd = findViewById(R.id.resetpwd_edit_pwd_new);
        signRepet_pwd = findViewById(R.id.resetpwd_edit_pwd_old);
        timeText = findViewById(R.id.SIGNTime);
//        事件注册
        SureButton.setOnClickListener(this);
        CancleButton.setOnClickListener(this);
    }

//    //    存储用户数据
////存数据
//    public static boolean saveUserInfo(Context context, String username, String password) {
//        SharedPreferences sharedPrefs = context.getSharedPreferences("SavedData", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        editor.putString("username", username);
//        editor.putString("password", password);
//        editor.commit();
//        return true;
//    }

    //      事件监听
    @Override
    public void onClick(View view) {
        String name_sign = signmUser_name.getText().toString();
        String pwdnew = signUser_pwd.getText().toString();
        String pwdold = signRepet_pwd.getText().toString();
        switch (view.getId()) {
//            注册确定按钮事件操作，返回登录界面
            case R.id.register_btn_sure:
//                判断密码是否正确
//                密码正确则保存用户信息并且跳转登陆界面
                if (name_sign.length() < 1 || pwdnew.length() < 1 || pwdold.length() < 1) {
                    Toast.makeText(Sign.this, "Registration information is incomplete, please check it", Toast.LENGTH_LONG).show();
                } else {
//                    两次输入密码一致，检测其长度
                    if (pwdnew.equals(pwdold)) {
                        if (pwdnew.length() >= 6) {
//                    创建文件存储信息
                            SharedPreferences sh = getSharedPreferences("SavedData", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sh.edit();
//                    使用put添加信息
                            editor.putString("user_name", name_sign);
                            editor.putString("user_pwd", pwdnew);
//                            保存数据
                            editor.apply();
                            //                    出现弹框告知其返回登陆界面
                            Toast.makeText(Sign.this, "Success! You are about to return to the landing interface", Toast.LENGTH_LONG).show();
                            intent = new Intent(Sign.this, Login.class);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                        } else {
                            Toast.makeText(Sign.this, "Password length at least six bits", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Sign.this, "Two inconsistencies in password input, please check", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.register_btn_cancel:
                intent = new Intent(Sign.this, Login.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
//            注册取消按钮，返回登录界面
            default:
                break;
        }
    }
}
