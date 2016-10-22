package com.wuyunlong.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.bean.Result;
import com.wuyunlong.fulicenter.bean.User;
import com.wuyunlong.fulicenter.net.NetDao;
import com.wuyunlong.fulicenter.net.OkHttpUtils;
import com.wuyunlong.fulicenter.utils.CommonUtils;
import com.wuyunlong.fulicenter.utils.L;
import com.wuyunlong.fulicenter.utils.MFGT;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.login_title_back)
    ImageButton loginTitleBack;
    @Bind(R.id.login_title)
    LinearLayout loginTitle;
    @Bind(R.id.login_username)
    EditText mLoginUsername;
    @Bind(R.id.login_password)
    EditText mLoginPassword;
    @Bind(R.id.login)
    Button login;
    @Bind(R.id.register)
    Button register;

    String username;
    String userpassword;
    LoginActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setContentView(R.layout.activity_login);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.login, R.id.register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                checkedInput();
                break;
            case R.id.register:
                MFGT.gotoRegister(this);
                break;
        }
    }

    /**
     * 获取登录的参数
     */
    private void checkedInput() {
        username = mLoginUsername.getText().toString().trim();
        userpassword = mLoginPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)){
            CommonUtils.showShortToast("账号不能为空");//可调用布局中的参数
            mLoginUsername.requestFocus();
        }else  if (TextUtils.isEmpty(userpassword)){
            CommonUtils.showShortToast("请输入密码");
            mLoginPassword.requestFocus();
            return;
        }
        login_event();

    }

    private void login_event() {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.logining));//登录动画
        pd.show();
        L.i("username:"+username);
        L.e("password:"+userpassword);
        NetDao.login(mContext, username, userpassword, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pd.dismiss();//窗口消失
                L.e("");
                if (result==null){
                    CommonUtils.showShortToast(R.string.login_fail);
                }else {
                    if (result.isRetMsg()){//登录成功
                        User user = (User) result.getRetData();
                        L.e("user"+user);
                        MFGT.finish(mContext);
                    }else {//各种判断账号密码，登录失败

                        if (result.getRetCode()== I.MSG_LOGIN_UNKNOW_USER){
                            CommonUtils.showShortToast(R.string.login_fail_unknow_user);//未知账号
                        }else if (result.getRetCode()==I.MSG_LOGIN_ERROR_PASSWORD){
                            CommonUtils.showShortToast(R.string.login_fail_error_password);//密码错误
                        }else {
                            CommonUtils.showShortToast(R.string.login_fail);
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showShortToast(error);
                L.e("登录方法错误"+error);
            }
        });
    }

    /**
     * 保存用户登录结果
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&requestCode==I.REQUEST_CODE_REGISTER){
            String name = data.getStringExtra(I.User.USER_NAME);
            mLoginUsername.setText(name);
        }
    }
}
