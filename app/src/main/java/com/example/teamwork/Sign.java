package com.example.teamwork;
//注册界面后端代码
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Sign extends AppCompatActivity implements View.OnClickListener
{
//    定义成员变量
    private Button SureButton;
    private Button CancleButton;
    private Intent intent;
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
//        事件注册
        SureButton.setOnClickListener(this);
        CancleButton.setOnClickListener(this);
    }

//      事件监听
    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            注册确定按钮事件操作，返回登录界面
            case R.id.register_btn_sure:
                intent= new Intent(Sign.this,Login.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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
