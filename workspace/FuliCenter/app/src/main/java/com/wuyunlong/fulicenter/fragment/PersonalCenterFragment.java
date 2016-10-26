package com.wuyunlong.fulicenter.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wuyunlong.fulicenter.FuLiCenterApplication;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.activity.MainActivity;
import com.wuyunlong.fulicenter.bean.Result;
import com.wuyunlong.fulicenter.bean.User;
import com.wuyunlong.fulicenter.dao.UserDao;
import com.wuyunlong.fulicenter.net.NetDao;
import com.wuyunlong.fulicenter.net.OkHttpUtils;
import com.wuyunlong.fulicenter.utils.ImageLoader;
import com.wuyunlong.fulicenter.utils.L;
import com.wuyunlong.fulicenter.utils.MFGT;
import com.wuyunlong.fulicenter.utils.ResultUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PersonalCenterFragment extends BaseFragment {
    private static final String TAG = PersonalCenterFragment.class.getSimpleName();

    @Bind(R.id.iv_user_avatar)
    ImageView mIvUserAvatar;
    @Bind(R.id.tv_user_name)
    TextView mTvUserName;

    MainActivity mContext;
    @Bind(R.id.tv_center_settings)
    TextView tvCenterSettings;
    @Bind(R.id.center_user_info)
    RelativeLayout centerUserInfo;

    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, view);
        mContext = (MainActivity) getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        User user = FuLiCenterApplication.getUser();
        L.e(TAG, "用户+" + user);
        if (user == null) {
            MFGT.gotoLogin(mContext);
        } else {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mIvUserAvatar);
            mTvUserName.setText(user.getMuserNick());
        }

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_center_settings, R.id.center_user_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_center_settings://跳转到个人信息
                MFGT.gotoPersonaInfo(mContext);
                break;
            case R.id.center_user_info:
                MFGT.gotoPersonaInfo(mContext);//跳转到个人信息
                break;
        }
    }

    private void syncUsetInfo() {
        NetDao.syncUserInfo(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, User.class);
                if (result != null) {
                    User u = (User) result.getRetData();
                    if (!user.equals(u)) {
                        UserDao dao = new UserDao(mContext);
                        boolean b = dao.saveUser(u);
                        if (b) {
                            FuLiCenterApplication.setUser(u);
                            user = u;
                            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),
                                    mContext, mIvUserAvatar);
                            mTvUserName.setText(user.getMuserNick());
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void getCollectsCount() {
        NetDao.getCollectsCount(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
