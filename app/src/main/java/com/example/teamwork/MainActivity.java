package com.example.teamwork;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;


public class MainActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showProgressDialog("Wait", "Loading the data···");

//        欢迎界面，2s内跳转
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(3000);

                    toMainMenu();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
//    跳转注册界面

    public void toMainMenu() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    //    进度加载页面
    /*
     * 提示加载
     */
    public void showProgressDialog(String title, String message) {
//        Window window = progressDialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.alpha = 0.7f;
//        lp.dimAmount=0.8f;
//        window.setAttributes(lp);
        if (progressDialog == null) {

            progressDialog = ProgressDialog.show(MainActivity.this, title,
                    message, true, false);
        } else if (progressDialog.isShowing()) {
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        }
        progressDialog.show();

    }

    /*
     * 隐藏提示加载
     */
    public void hideProgressDialog() {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


//    设置背景透明度

}
