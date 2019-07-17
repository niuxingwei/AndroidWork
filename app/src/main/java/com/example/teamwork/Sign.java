package com.example.teamwork;
//注册界面后端代码
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Sign extends AppCompatActivity implements View.OnClickListener
{
//    定义成员变量
    private Button SureButton;
    private Button CancleButton;
    private Intent intent;
    private EditText signmUser_name;
    private EditText signUser_pwd;
    private EditText signRepet_pwd;
//函数入口
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        init();
    }
//    初始化成员变量
    public void init(){
        SureButton = findViewById(R.id.register_btn_sure);
        CancleButton = findViewById(R.id.register_btn_cancel);
        signmUser_name = findViewById(R.id.resetpwd_edit_name);
        signUser_pwd = findViewById(R.id.resetpwd_edit_pwd_new);
        signRepet_pwd= findViewById(R.id.resetpwd_edit_pwd_old);
//        事件注册
        SureButton.setOnClickListener(this);
        CancleButton.setOnClickListener(this);
    }

//      事件监听
    @Override
    public void onClick(View view) {
        String name_sign = signmUser_name.getText().toString();
        String pwdnew = signUser_pwd.getText().toString();
        String pwdold = signRepet_pwd.getText().toString();
        switch (view.getId()){
//            注册确定按钮事件操作，返回登录界面
            case R.id.register_btn_sure:
//                判断密码是否正确
//                密码正确则保存用户信息并且跳转登陆界面
                if (pwdnew.equals(pwdold)){
//                    创建文件存储信息
                    SharedPreferences sh = getSharedPreferences("SavedData",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
//                    使用put添加信息
                    intent= new Intent(Sign.this,Login.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }



                break;
            case R.id.register_btn_cancel:
                intent= new Intent(Sign.this,Login.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
//            注册取消按钮，返回登录界面
            default:
                break;
        }
    }
}
