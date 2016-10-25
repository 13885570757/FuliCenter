package com.wuyunlong.fulicenter.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wuyunlong.fulicenter.FuLiCenterApplication;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.bean.User;
import com.wuyunlong.fulicenter.dao.SharePrefrenceUtils;
import com.wuyunlong.fulicenter.utils.ImageLoader;
import com.wuyunlong.fulicenter.utils.MFGT;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalInfo extends BaseActivity {

    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.ivAvatar)
    ImageView ivAvatar;
    @Bind(R.id.tvNick)
    TextView tvNick;
    @Bind(R.id.rlNick)
    RelativeLayout rlNick;
    @Bind(R.id.ivqrcode)
    ImageView ivqrcode;
    @Bind(R.id.rlQrcode)
    RelativeLayout rlQrcode;
    @Bind(R.id.btUnLogin)
    Button btUnLogin;

    User user;
    PersonalInfo mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        //DisplayUtils.initBackWithTitle(mContext,getResources().getString());


    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();//从Fuli中获取用户信息
        if (user != null) {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, ivAvatar);

            tvNick.setText(user.getMuserNick());
        } else {
            finish();
        }

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.ivBack, R.id.rlUserAvatar, R.id.rlNick, R.id.rlQrcode, R.id.btUnLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.rlUserAvatar:
                //修改头像的方法

                break;
            case R.id.rlNick:
                changeNick();
                break;
            case R.id.rlQrcode://二维码界面
                MFGT.gotoQrcodeActivity(this);
                break;
            case R.id.btUnLogin://注销
                loginout();
                break;
        }
    }

    /**
     * 注销账号
     */
    private void loginout() {
        if (user!=null){
            SharePrefrenceUtils.getInstance(this).removeUser();
            FuLiCenterApplication.setUser(null);
            MFGT.gotoLogin(this);
        }
        MFGT.finish(this);


    }

    /**
     * 修改昵称
     */
    private void changeNick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("修改昵称")
                .setMessage("确定修改昵称？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }
}
