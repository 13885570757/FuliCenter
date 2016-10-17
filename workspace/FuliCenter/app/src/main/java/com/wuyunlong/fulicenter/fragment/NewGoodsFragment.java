package com.wuyunlong.fulicenter.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuyunlong.fulicenter.MainActivity;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.adapter.NewGoodsAdapter;
import com.wuyunlong.fulicenter.bean.NewGoodsBean;
import com.wuyunlong.fulicenter.dao.NetDao;
import com.wuyunlong.fulicenter.utils.OkHttpUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewGoodsFragment extends Fragment {

    MainActivity mContext;
    NewGoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mList;
    int pageId = 1;
    @Bind(R.id.tvRefresh)
    TextView tvRefresh;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, view);
        mContext = (MainActivity) getContext();
        mList = new ArrayList<>();
        mAdapter = new NewGoodsAdapter(mContext, mList);
        initView();
        initData();
        return view;

    }

    /**
     * 获取数据
     */
    private void initData() {
        NetDao.downloadNewGoods(mContext, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                if (result.g)


            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        srl.setColorScheme(getResources().getColor(R.color.google_blue));
        srl.setColorScheme(getResources().getColor(R.color.google_green));
        srl.setColorScheme(getResources().getColor(R.color.google_red));
        srl.setColorScheme(getResources().getColor(R.color.google_yellow));


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}