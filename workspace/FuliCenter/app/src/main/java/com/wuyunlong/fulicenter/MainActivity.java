package com.wuyunlong.fulicenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity{

    RadioButton radio_btn_newGoods, radio_btn_boutique, radio_btn_category, radio_btn_cart, radio_btn_personal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        radio_btn_newGoods = (RadioButton) findViewById(R.id.radio_btn_newGoods);
        radio_btn_boutique = (RadioButton) findViewById(R.id.radio_btn_boutique);
        radio_btn_category = (RadioButton) findViewById(R.id.radio_btn_category);
        radio_btn_cart = (RadioButton) findViewById(R.id.radio_btn_cart);
        radio_btn_personal = (RadioButton) findViewById(R.id.radio_btn_personal);

    }

    private void seletor(RadioButton button) {
        if (button != radio_btn_newGoods) {
            radio_btn_newGoods.setChecked(false);
        }
        if (button != radio_btn_boutique) {
            radio_btn_boutique.setChecked(false);
        }
        if (button != radio_btn_category) {
            radio_btn_category.setChecked(false);
        }
        if (button != radio_btn_cart) {
            radio_btn_cart.setChecked(false);
        }
        if (button != radio_btn_personal) {
            radio_btn_personal.setChecked(false);
        }

    }

    public void onCheckedChange(View v) {
        switch (v.getId()) {
            case R.id.radio_btn_newGoods://选中新品
                seletor((RadioButton) v);
                break;
            case R.id.radio_btn_boutique://选择精选
                seletor((RadioButton) v);
                break;
            case R.id.radio_btn_category://分类
                seletor((RadioButton) v);
                break;
            case R.id.radio_btn_cart://购物车
                seletor((RadioButton) v);
                break;
            case R.id.radio_btn_personal://个人中心
                seletor((RadioButton) v);
                break;

        }
    }
}
