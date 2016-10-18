package com.wuyunlong.fulicenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wuyunlong.fulicenter.GoodsDetails;
import com.wuyunlong.fulicenter.MainActivity;
import com.wuyunlong.fulicenter.R;


public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    public static void gotogoodsDeatilsActivity(Context context,int pageId){
        Intent intent = new Intent();
        intent.setClass(context, GoodsDetails.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID,pageId);
        startActivity(context,intent,pageId);
    }
    public static void startActivity(Context context,Intent intent,int pageId){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
}
