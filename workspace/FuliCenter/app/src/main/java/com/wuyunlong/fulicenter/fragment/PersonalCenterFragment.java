package com.wuyunlong.fulicenter.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuyunlong.fulicenter.FuLiCenterApplication;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.activity.MainActivity;
import com.wuyunlong.fulicenter.bean.User;
import com.wuyunlong.fulicenter.utils.ImageLoader;
import com.wuyunlong.fulicenter.utils.L;
import com.wuyunlong.fulicenter.utils.MFGT;

import butterknife.Bind;
import butterknife.ButterKnife;


public class PersonalCenterFragment extends BaseFragment {
    private static final String TAG = PersonalCenterFragment.class.getSimpleName();

    @Bind(R.id.iv_user_avatar)
    ImageView mIvUserAvatar;
    @Bind(R.id.tv_user_name)
    TextView mTvUserName;

    MainActivity mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, view);
        mContext = (MainActivity) getActivity();
        super.onCreateView(inflater,container,savedInstanceState);
        return view;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        User user = FuLiCenterApplication.getUser();
        L.e(TAG,"用户+"+user);
        if(user ==null){
            MFGT.gotoLogin(mContext);
        }else{
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext,mIvUserAvatar);
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
}