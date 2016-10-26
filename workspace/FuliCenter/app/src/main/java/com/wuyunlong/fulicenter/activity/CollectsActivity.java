package com.wuyunlong.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.views.DisplayUtils;

public class CollectsActivity extends AppCompatActivity {
    CollectsActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_collects);
        mContext = this;
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        DisplayUtils.initBackWithTitle(mContext, getResources().getString(R.string.mycollects_title));
    }

}
