package com.wuyunlong.fulicenter.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wuyunlong.fulicenter.R;

public class SplashActivity extends AppCompatActivity{

    private final long sleepTime = 2000;//设置等待事件为2秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }


    @Override
    protected void onStart() {//主线程不适合耗时操作，写onStart中
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start =System.currentTimeMillis();
                long costTime = System.currentTimeMillis()-start;
                if (sleepTime-costTime>0){
                    try {
                        Thread.sleep(sleepTime-costTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        }).start();

    }
}
