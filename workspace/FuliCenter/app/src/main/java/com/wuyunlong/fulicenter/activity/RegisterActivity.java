package com.wuyunlong.fulicenter.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.utils.CommonUtils;

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
    @Bind(R.id.btn_ragister)
    Button btnRagister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
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

    @OnClick(R.id.btn_ragister)
    public void onClick() {
        String username = registerUsername.getText().toString().trim();
        String nickname =registerNick.getText().toString().trim();
        String password = registerPassword.getText().toString().trim();
        String repassword = registerRepassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)){
            CommonUtils.showShortToast("错误");
            registerUsername.requestFocus();
            return;
        }else if(!username.matches(("[a-zA-z]\\w{5,15}"))){
            CommonUtils.showShortToast("错误");
            return;
        }else if(TextUtils.isEmpty(nickname)){
            CommonUtils.showShortToast("请输入昵称");
            return;
        }else if(TextUtils.isEmpty(password)){
            CommonUtils.showShortToast("密码不能为空");
            return;
        }else if(TextUtils.isEmpty(repassword)){
            CommonUtils.showShortToast("请再次输入密码");
            return;
        }
    }
}
