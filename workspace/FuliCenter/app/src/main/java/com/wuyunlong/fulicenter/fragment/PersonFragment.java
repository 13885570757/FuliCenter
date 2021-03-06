package com.wuyunlong.fulicenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuyunlong.fulicenter.FuLiCenterApplication;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.activity.CollectsActivity;
import com.wuyunlong.fulicenter.activity.MainActivity;
import com.wuyunlong.fulicenter.activity.SettingActivity;
import com.wuyunlong.fulicenter.bean.MessageBean;
import com.wuyunlong.fulicenter.bean.Result;
import com.wuyunlong.fulicenter.bean.UserAvatarBean;
import com.wuyunlong.fulicenter.dao.UserDao;
import com.wuyunlong.fulicenter.net.NetDao;
import com.wuyunlong.fulicenter.utils.ImageLoader;
import com.wuyunlong.fulicenter.utils.L;
import com.wuyunlong.fulicenter.utils.MFGT;
import com.wuyunlong.fulicenter.utils.OkHttpUtils;
import com.wuyunlong.fulicenter.utils.ResultUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonFragment extends Fragment {
    MainActivity mContext;


    @Bind(R.id.tvSetting)
    TextView tvSetting;
    @Bind(R.id.ivAvatar)
    ImageView ivAvatar;
    @Bind(R.id.tvUserName)
    TextView tvUserName;

    UserAvatarBean user;
    @Bind(R.id.tvCollectGoods)//收藏的商品
            TextView tvCollectGoods;

    @Bind(R.id.lyCollectGoods)//收藏的商品
            LinearLayout lyCollectGoods;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View layout = inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getActivity();
        initView();
        initData();
        return layout;
    }

    private void initView() {

    }

    private void initData() {
        user = FuLiCenterApplication.getUser();
        if (user == null) {
            MFGT.gotoLoginActivity(mContext);
            return;
        } else {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, ivAvatar);
            tvUserName.setText(user.getMuserNick());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        user = FuLiCenterApplication.getUser();
        if (user == null) {
            initData();
        } else {
            initData();
        }
        syncUserInfo();
        getCollectsCount();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 点击设置，跳转到个人信息
     */
    @OnClick(R.id.tvSetting)
    public void onClick() {
        MFGT.startActivity(mContext, SettingActivity.class);
    }

    @OnClick(R.id.lyCollectGoods)
    public void onCollectgoods() {
        MFGT.startActivity(mContext, CollectsActivity.class);
    }


    /**
     * 同步客户端和服务器的个人信息
     */
    private void syncUserInfo() {
        NetDao.syncUser(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, UserAvatarBean.class);
                if (result != null) {
                    UserAvatarBean u = (UserAvatarBean) result.getRetData();
                    if (!user.equals(u)) {
                        UserDao dao = new UserDao(mContext);
                        boolean b = dao.saveUser(u);
                        if (b) {
                            FuLiCenterApplication.setUser(u);
                            user = u;
                            ImageLoader.setAvatar
                                    (ImageLoader.getAvatarUrl(user), mContext, ivAvatar);
                            tvUserName.setText(user.getMuserNick());
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void getCollectsCount() {
        NetDao.downLoadCollectCount(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {
                    tvCollectGoods.setText(result.getMsg());
                } else {
                    tvCollectGoods.setText(String.valueOf(0));//不能直接设置为0
                }
            }

            @Override
            public void onError(String error) {
                tvCollectGoods.setText(String.valueOf(0));
                L.e("=====收藏的商品错误" + error);
            }
        });
    }
}
