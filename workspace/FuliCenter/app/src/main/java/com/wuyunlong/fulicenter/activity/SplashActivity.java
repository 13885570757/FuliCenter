package com.wuyunlong.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wuyunlong.fulicenter.FuLiCenterApplication;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.bean.UserAvatarBean;
import com.wuyunlong.fulicenter.dao.SharePreferencesUtils;
import com.wuyunlong.fulicenter.dao.UserDao;
import com.wuyunlong.fulicenter.utils.L;
import com.wuyunlong.fulicenter.utils.MFGT;


public class SplashActivity extends AppCompatActivity {


    private final long sleepTime = 2000;
    SplashActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        MFGT.finish(SplashActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();

                long costTime = System.currentTimeMillis() - start;
                if (sleepTime - costTime > 0) {
                    try {
                        Thread.sleep(sleepTime - costTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                //creatd db执行耗时操作
                UserAvatarBean user = FuLiCenterApplication.getUser();
                L.i("user==" + user);
                String userName = SharePreferencesUtils.getInstance(mContext).getUser();
                if (user == null && userName != null) {
                    UserDao dao = new UserDao(mContext);
                    user = dao.getUser(userName);
                    L.i("user=" + user.toString());
                    if (user != null) {
                        FuLiCenterApplication.setUser(user);
                    }
                }
                MFGT.gotoMainActivity(SplashActivity.this);



            }
        }).start();

    }
}
