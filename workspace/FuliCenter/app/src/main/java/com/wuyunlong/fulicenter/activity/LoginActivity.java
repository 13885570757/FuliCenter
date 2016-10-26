package com.wuyunlong.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.backClickArea)
    ImageView tlBack;
    @Bind(R.id.tv_common_title)
    TextView tlTitle;
    @Bind(R.id.etLoginUserName)
    EditText etLoginUserName;
    @Bind(R.id.etLoginPwd)
    EditText etLoginPwd;
    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.btnReg)
    Button btnReg;

    String userName, password;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String toUserName = intent.getStringExtra("passusername");
        etLoginUserName.setText(toUserName);
    }


    private void initData() {
        final ProgressDialog bd = new ProgressDialog(mContext);
        bd.setMessage(getResources().getString(R.string.login));
        bd.show();
        userName = etLoginUserName.getText().toString();
        password = etLoginPwd.getText().toString();
        NetDao.loginSet(this, userName, password, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, UserAvatarBean.class);
                if (result.isRetMsg()) {
                    UserAvatarBean user = (UserAvatarBean) result.getRetData();
                    String userNick = user.getMuserNick();
                    UserDao dao = new UserDao(mContext);
                    L.i("UserAvatarBean = " + user.toString());
                    boolean isSuccess = dao.saveUser(user);
                    L.e("isS" + isSuccess);
                    if (isSuccess) {
                        FuLiCenterApplication.setUser(user);
                        SharePreferencesUtils.getInstance(mContext).saveUser(user.getMuserName());
                        MFGT.gotoMainActivity(LoginActivity.this);
                        Toast.makeText(LoginActivity.this, "欢迎用户:" + userNick, Toast.LENGTH_SHORT).show();
                    } else {
                        CommonUtils.showLongToast(R.string.user_database_error);
                    }

                } else if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                    Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                } else if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                    Toast.makeText(LoginActivity.this, "账户密码错误", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(String error) {
                bd.dismiss();
            }
        });
        bd.dismiss();
    }

    @OnClick({R.id.btnLogin, R.id.btnReg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                initData();
                break;
            case R.id.btnReg:
                MFGT.gotoRegister(LoginActivity.this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        MFGT.gotoMainActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_REGISTER) {
            String name = data.getStringExtra(I.User.USER_NAME);
            etLoginUserName.setText(name);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
