package com.example.teamwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserCenter extends AppCompatActivity implements View.OnClickListener {
//      定义变量
    private Button backButton;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
         init();
    }

//    变量初始化
    protected void init(){
        backButton = findViewById(R.id.back);
//        事件注册
        backButton.setOnClickListener(this);
    }
//    事件监听

    @Override
    public void onClick(View view) {
        intent = new Intent(UserCenter.this,Login.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
