package com.wuyunlong.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuyunlong.fulicenter.FuLiCenterApplication;
import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.bean.Result;
import com.wuyunlong.fulicenter.bean.UserAvatarBean;
import com.wuyunlong.fulicenter.dao.SharePreferencesUtils;
import com.wuyunlong.fulicenter.dao.UserDao;
import com.wuyunlong.fulicenter.net.NetDao;
import com.wuyunlong.fulicenter.utils.CommonUtils;
import com.wuyunlong.fulicenter.utils.L;
import com.wuyunlong.fulicenter.utils.MFGT;
import com.wuyunlong.fulicenter.utils.OkHttpUtils;
import com.wuyunlong.fulicenter.utils.ResultUtils;
import com.wuyunlong.fulicenter.views.DisplayUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateNickActivity extends AppCompatActivity {

    @Bind(R.id.backClickArea)
    ImageView backClickArea;
    @Bind(R.id.tv_common_title)
    TextView tvCommonTitle;
    @Bind(R.id.etNick)
    EditText etNick;
    @Bind(R.id.btnChange)
    Button btnChange;

    UpdateNickActivity mContext;

    String reUserNick;
    String userNick;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cg_nick);
        mContext = this;
        ButterKnife.bind(this);
        DisplayUtils.initBackWithTitle(mContext, "修改昵称");
        UserAvatarBean user = FuLiCenterApplication.getUser();
        if (user != null) {
            etNick.setText(FuLiCenterApplication.getUser().getMuserNick());
            userName = FuLiCenterApplication.getUser().getMuserName();
        } else {
            finish();
        }

    }


    @OnClick({R.id.backClickArea, R.id.btnChange})
    public void onClick(View view) {
        updateUserNick(view);
    }

    private void updateUserNick(View view) {
        reUserNick = etNick.getText().toString().trim();
        userNick = FuLiCenterApplication.getUser().getMuserNick();
        switch (view.getId()) {
            case R.id.backClickArea:
                MFGT.finish(this);
                break;
            case R.id.btnChange:
                if (TextUtils.isEmpty(reUserNick)) {
                    CommonUtils.showShortToast("不能为空");
                } else if (TextUtils.equals(reUserNick, userNick)) {
                    CommonUtils.showShortToast("不能重复");
                } else {
                    NetDao.updateUserNick(this, userName, reUserNick, new OkHttpUtils.OnCompleteListener<String>() {
                                @Override
                                public void onSuccess(String s) {
                                    Result result = ResultUtils.getResultFromJson(s, UserAvatarBean.class);
                                    if (result.isRetMsg()) {
                                        UserAvatarBean user = (UserAvatarBean) result.getRetData();
                                        String userNick = user.getMuserNick();
                                        UserDao dao = new UserDao(mContext);
                                        L.i("UserAvatarBean = " + user.toString());
                                        boolean isSuccess = dao.updateUser(user);
                                        L.e("isS" + isSuccess);
                                        if (isSuccess) {
                                            FuLiCenterApplication.setUser(user);
                                            SharePreferencesUtils.getInstance(mContext).saveUser(user.getMuserName());
                                            CommonUtils.showShortToast("修改成功");
                                            setResult(RESULT_OK);
                                            MFGT.finish(mContext);
                                            if (result.getRetCode() == I.MSG_USER_SAME_NICK) {
                                                CommonUtils.showLongToast("昵称相同");
                                            } else if (result.getRetCode() == I.MSG_USER_UPDATE_NICK_FAIL) {
                                                CommonUtils.showLongToast("昵称修改失败");
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onError(String error) {
                                    CommonUtils.showLongToast("昵称修改失败," + error);

                                }
                            }

                    );
                }

                break;
        }
    }
}
