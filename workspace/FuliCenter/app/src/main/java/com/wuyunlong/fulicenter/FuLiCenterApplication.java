package com.wuyunlong.fulicenter;

import android.app.Application;

/**
 * Created by Administrator on 2016/10/17.
 */
public class FuLiCenterApplication extends Application {
    public static FuLiCenterApplication applicationContext;

    public static FuLiCenterApplication getInstance(){
        if (applicationContext==null){
            synchronized (applicationContext){
                if (applicationContext==null){
                    applicationContext = new FuLiCenterApplication();
                }
            }
        }
        return applicationContext;
    }


}
