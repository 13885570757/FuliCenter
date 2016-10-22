package com.wuyunlong.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.bean.Result;
import com.wuyunlong.fulicenter.net.NetDao;
import com.wuyunlong.fulicenter.net.OkHttpUtils;
import com.wuyunlong.fulicenter.utils.CommonUtils;
import com.wuyunlong.fulicenter.utils.ConvertUtils;
import com.wuyunlong.fulicenter.utils.L;
import com.wuyunlong.fulicenter.utils.MFGT;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.register_back)
    ImageView registerBack;
    @Bind(R.id.register_username)
    EditText registerUsername;
    @Bind(R.id.register_nick)
    EditText registerNick;
    @Bind(R.id.register_password)
    EditText registerPassword;
    @Bind(R.id.register_repassword)
    EditText registerRepassword;
    @Bind(R.id.btn_register)
    Button btnRegister;

    String username;
    String nickname;
    String password;
    RegisterActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ButterKnife.bind(this);

        setContentView(R.layout.activity_register);
        mContext  =  this;
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

    @OnClick(R.id.btn_register)
    public void checkedInput() {
        String username = registerUsername.getText().toString().trim();
        String nickname =registerNick.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();
        String repassword = registerRepassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)){//账号不能为空
            CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
            registerUsername.requestFocus();
            return;
        }else if(!username.matches(("[a-zA-z]\\w{5,15}"))){//密码的范围
            CommonUtils.showShortToast(R.string.illegal_user_name);
            return;
        }else if(TextUtils.isEmpty(nickname)){//昵称不能为空
            CommonUtils.showShortToast(R.string.nick_name_connot_be_empty);
            return;
        }else if(TextUtils.isEmpty(password)){//密码不能为空
            CommonUtils.showShortToast(R.string.password_connot_be_empty);
            return;
        }else if(TextUtils.isEmpty(repassword)){
            CommonUtils.showShortToast(R.string.confirm_password_connot_be_empty);
            return;
        }else if (!password.equals(repassword)){//两次密码不一致
            CommonUtils.showShortToast(R.string.two_input_password);
            return;
        }
        register();
    }

    private void register() {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.registering));
        pd.show();
        NetDao.register(mContext, username, nickname, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pd.dismiss();
                if (result==null){//注册失败
                    CommonUtils.showShortToast(R.string.register_fail);
                    setResult(RESULT_OK,new Intent().putExtra(I.User.USER_NAME,username));
                    MFGT.finish(mContext);
                }else{
                    CommonUtils.showShortToast(R.string.register_fail_exists);
                }
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showShortToast(error);
                L.e("注册错误"+error);

            }
        });

    }

}
