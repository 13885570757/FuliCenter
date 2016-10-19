package com.wuyunlong.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;

import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.fragment.BoutiqueFragment;
import com.wuyunlong.fulicenter.fragment.NewGoodsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {


    RadioButton radioBtnPersonal;

    RadioButton[] rbs;

    int index = 0;
    int currentIndex = 0;

    Fragment[] mFragments;
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    @Bind(R.id.layout_new_good)
    RadioButton layoutNewGood;
    @Bind(R.id.layout_boutique)
    RadioButton layoutBoutique;
    @Bind(R.id.layout_category)
    RadioButton layoutCategory;
    @Bind(R.id.layout_cart)
    RadioButton layoutCart;
    @Bind(R.id.layout_personal_center)
    RadioButton layoutPersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initFragment();
    }

    private void initFragment() {
        mFragments = new Fragment[5];
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mFragments[0] = mNewGoodsFragment;
        mFragments[1] = mBoutiqueFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new NewGoodsFragment())
                .add(R.id.fragment_container, new BoutiqueFragment())
                .hide(mBoutiqueFragment)
                .show(mNewGoodsFragment)
                .commit();
    }

    private void initView() {
        rbs = new RadioButton[5];
        rbs[0] = layoutNewGood;
        rbs[1] = layoutBoutique;
        rbs[2] = layoutCategory;
        rbs[3] = layoutCart;
        rbs[4] = layoutPersonal;

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
        FragmentTransaction transaction = manager.beginTransaction();
        switch (v.getId()) {
            case R.id.layout_new_good://选中新品
                index = 0;
                mNewGoodsFragment = new NewGoodsFragment();
                transaction.add(R.id.srl, mNewGoodsFragment);
                break;
            case R.id.layout_boutique://选择精选
                index = 1;
                break;
            case R.id.layout_category://分类
                index = 2;
                break;
            case R.id.layout_cart://购物车
                index = 3;
                break;
            case R.id.layout_personal_center://个人中心
                index = 4;
                break;
        }
        setFragment();
        seletor();
    }


    private void setFragment() {
        if (index != currentIndex) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mFragments[currentIndex]);
            if (mFragments[index].isAdded()) {
                ft.add(R.id.fragment_container, mFragments[index]);
            }
            ft.show(mFragments[index]).commit();
        }
    }

}

