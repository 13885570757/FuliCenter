package com.wuyunlong.fulicenter.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wuyunlong.fulicenter.FuLiCenterApplication;
import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.bean.Result;
import com.wuyunlong.fulicenter.bean.User;
import com.wuyunlong.fulicenter.dao.UserDao;
import com.wuyunlong.fulicenter.net.NetDao;
import com.wuyunlong.fulicenter.net.OkHttpUtils;
import com.wuyunlong.fulicenter.utils.CommonUtils;
import com.wuyunlong.fulicenter.utils.L;
import com.wuyunlong.fulicenter.utils.MFGT;
import com.wuyunlong.fulicenter.utils.ResultUtils;
import com.wuyunlong.fulicenter.view.DisplayUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyNick extends BaseActivity {

    private static final String TAG = ModifyNick.class.getSimpleName();

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
      DisplayUtils.initBackWithTitle(mContext,
              getResources().getString(R.string.user_name));

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
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.update_user_nick));
        pd.show();
        NetDao.updateNick(mContext,user.getMuserName(),nick,
                new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                    Result result = ResultUtils.getResultFromJson(s, User.class);

                  L.e(TAG, "result=" + result);
                    if (result == null) {
                        CommonUtils.showLongToast("更新失败");
                    } else {
                        if (result.isRetMsg()) {
                            User  u= (User) result.getRetData();
                            L.e(TAG, "user=" + u);
                            //MFGT.finish(mContext);
                            UserDao dao = new UserDao(mContext);
                            boolean isSuccess = dao.updateUser(u);
                            if (isSuccess) {
//                                SharePrefrenceUtils.getInstance(mContext)
//                                        .saveUser(user.getMuserName());
                                FuLiCenterApplication.setUser(u);
                                setResult(RESULT_OK);
                                MFGT.finish(mContext);
                            } else {
                                CommonUtils.showLongToast("更新失败");
                            }
                        } else {
                            if (result.getRetCode() == I.MSG_USER_SAME_NICK) {
                                CommonUtils.showLongToast("同样的昵称");
                            } else if (result.getRetCode() == I.MSG_USER_UPDATE_NICK_FAIL) {
                                CommonUtils.showLongToast("失败");
                            } else {
                                CommonUtils.showLongToast("失败");
                            }
                        }
                    }
                    pd.dismiss();
                }
            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showLongToast(error);
                L.e("====error==="+error);
            }
        });
    }
}
