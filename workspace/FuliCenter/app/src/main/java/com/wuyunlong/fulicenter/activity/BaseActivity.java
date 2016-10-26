package com.wuyunlong.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wuyunlong.fulicenter.utils.MFGT;


/**
 * Created by Administrator on 2016/10/19.
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        initData();
        initView();
        setListener();
        super.onCreate(savedInstanceState);
    }

    protected void setListener() {
    }

    protected void initView() {
    }

    protected void initData() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MFGT.finish(this);
    }
}
