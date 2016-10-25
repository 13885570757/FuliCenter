package com.wuyunlong.fulicenter.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wuyunlong.fulicenter.FuLiCenterApplication;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.bean.User;
import com.wuyunlong.fulicenter.utils.CommonUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyNick extends BaseActivity {

    @Bind(R.id.info_nick)
    TextView infoNick;
    @Bind(R.id.modify_nick)
    EditText modifyNick;
    @Bind(R.id.modify_btn)
    Button modifyBtn;
    ModifyNick mContext;

    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_nick);
        mContext = this;
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        //获取标题
      //  DisplayUtils.initBackWithTitle(mContext,
              //  getResources().getString(R.string.user_name));

    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();
        if (user != null) {
            modifyNick.setText(user.getMuserNick());
            modifyNick.setSelectAllOnFocus(true);
        } else {
            finish();
        }

    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.modify_btn)
    public void modifyNick() {
        if (user!=null){
           String nick =  modifyNick.getText().toString().trim();
            if (nick.equals(user.getMuserNick())){
                CommonUtils.showLongToast("修改的昵称不能和之前昵称相同");
            }else if(TextUtils.isEmpty(nick)){
                CommonUtils.showLongToast("未输入新的昵称");
            }else{//服务器请求
                updateNick(nick);
            }
        }
    }

    private void updateNick(String nick) {

    }
}
