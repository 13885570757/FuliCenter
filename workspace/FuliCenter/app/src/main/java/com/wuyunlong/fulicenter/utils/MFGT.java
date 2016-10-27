package com.wuyunlong.fulicenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.activity.BoutiqueActivity;
import com.wuyunlong.fulicenter.activity.CategoryChildActivity;
import com.wuyunlong.fulicenter.activity.CollectsActivity;
import com.wuyunlong.fulicenter.activity.GoodsDetailsActivity;
import com.wuyunlong.fulicenter.activity.LoginActivity;
import com.wuyunlong.fulicenter.activity.MainActivity;
import com.wuyunlong.fulicenter.activity.RegisterActivity;
import com.wuyunlong.fulicenter.activity.UpdateNickActivity;
import com.wuyunlong.fulicenter.bean.BoutiqueBean;
import com.wuyunlong.fulicenter.bean.CategoryChildBean;

import java.util.ArrayList;


public class MFGT {
    public static void finish(Activity activity) {
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public static void gotoMainActivity(Activity context) {
        startActivity(context, MainActivity.class);
    }

    public static void backtoMainActivity(Activity context) {
        startActivity(context, MainActivity.class);
    }

    public static void startActivity(Activity context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    //    intent.putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId);

    public static void gotoGoodsDetailsActivity(Context context, int goodsId) {
        Intent intent = new Intent();
        intent.setClass(context, GoodsDetailsActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID, goodsId);
        startActivity(context, intent);
    }

    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void gotoBouTiQueActivity(Context context, BoutiqueBean bean) {
        Intent intent = new Intent();
        intent.setClass(context, BoutiqueActivity.class);
        intent.putExtra(I.Boutique.ID, bean);
        startActivity(context, intent);
    }

    public static void gotoCategoryActivity(Context context, int catId, String groupName, ArrayList<CategoryChildBean> list) {
        Intent intent = new Intent();
        intent.setClass(context, CategoryChildActivity.class);
        intent.putExtra(I.CategoryChild.CAT_ID, catId);
        intent.putExtra(I.CategoryGroup.NAME, groupName);
        intent.putExtra(I.CategoryChild.ID, list);
        startActivity(context, intent);
    }

    public static void gotoLoginActivity(Activity mContext) {
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
        startActivityForResult(mContext, intent, I.REQUEST_CODE_LOGIN);
        mContext.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
    public static void gotoLoginFormCart(Activity mContext) {
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
        startActivityForResult(mContext, intent, I.REQUEST_CODE_LOGIN_FORM_CART);
        mContext.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public static void Register2Login() {

    }

    /**
     * 注册
     * @param mContext
     */
    public static void gotoRegister(Activity mContext) {
        Intent intent = new Intent();
        intent.setClass(mContext, RegisterActivity.class);
        startActivityForResult(mContext, intent, I.REQUEST_CODE_REGISTER);

    }

    public static void startActivityForResult(Activity mContext, Intent intent, int requestCode) {
        mContext.startActivityForResult(intent, requestCode);
        mContext.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }

    public static void gotoMainForResult(Activity mContext) {
        Intent intent = new Intent();
        intent.setClass(mContext, MainActivity.class);
        startActivityForResult(mContext, intent, I.REQUEST_CODE_LOGIN);

    }

    public static void gotoMainResult(Activity mContext) {
        Intent intent = new Intent();
        mContext.setResult(I.REQUEST_CODE_LOGIN, intent);
        finish(mContext);
        mContext.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }

    /**
     * 跳转到修改昵称
     * @param mContext
     */
    public static void gotoUpdateNick(Activity mContext) {
        startActivityForResult(mContext, new Intent(mContext, UpdateNickActivity.class
        ), I.REQUEST_CODE_NICK);

    }

    /**
     * 跳转到我收藏的商品
     * @param mContext
     */
    public static void gotoCollects(Activity mContext){
        startActivity(mContext,new Intent(mContext, CollectsActivity.class));

    }

}
