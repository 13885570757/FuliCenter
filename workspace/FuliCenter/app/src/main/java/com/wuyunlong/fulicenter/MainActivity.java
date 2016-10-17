package com.wuyunlong.fulicenter;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;

import com.wuyunlong.fulicenter.fragment.NewGoodsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    NewGoodsFragment mNewGoodsFragment;//新品



    @Bind(R.id.radio_btn_newGoods)
    RadioButton radioBtnNewGoods;
    @Bind(R.id.radio_btn_boutique)
    RadioButton radioBtnBoutique;
    @Bind(R.id.radio_btn_category)
    RadioButton radioBtnCategory;
    @Bind(R.id.radio_btn_cart)
    RadioButton radioBtnCart;
    @Bind(R.id.radio_btn_personal)
    RadioButton radioBtnPersonal;

    RadioButton[] rbs;

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rbs = new RadioButton[5];
        rbs[0] = radioBtnNewGoods;
        rbs[1] = radioBtnBoutique;
        rbs[2] = radioBtnCategory;
        rbs[3] = radioBtnCart;
        rbs[4] = radioBtnPersonal;

    }


    public void seletor() {

        for (int i = 0; i < rbs.length; i++) {
            if (index == i) {
                rbs[i].setChecked(true);
            } else {
                rbs[i].setChecked(false);
            }

        }
    }

    public void onCheckedChange(View v) {
        FragmentManager manager = getSupportFragmentManager();
       android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        switch (v.getId()) {
            case R.id.radio_btn_newGoods://选中新品
                index = 0;
        mNewGoodsFragment = new NewGoodsFragment();
                transaction.add(R.id.srl,mNewGoodsFragment);
                break;
            case R.id.radio_btn_boutique://选择精选
                index = 1;
                break;
            case R.id.radio_btn_category://分类
                index = 2;
                break;
            case R.id.radio_btn_cart://购物车
                index = 3;
                break;
            case R.id.radio_btn_personal://个人中心
                index = 4;
                break;

        }
        seletor();
    }

}

