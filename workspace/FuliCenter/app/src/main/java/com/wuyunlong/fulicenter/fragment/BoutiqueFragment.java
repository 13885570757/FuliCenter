package com.wuyunlong.fulicenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuyunlong.fulicenter.MainActivity;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.adapter.BoutiqueAdapter;
import com.wuyunlong.fulicenter.bean.BoutiqueBean;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment {

    @Bind(R.id.tvRefresh)
    TextView tvRefresh;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    LinearLayoutManager llm;
    MainActivity mContext;
    ArrayList<BoutiqueBean> mList;
    BoutiqueAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate
                (R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, layout);
        return layout;
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue));
        llm = new LinearLayoutManager(mContext);
        rv.setLayoutManager(llm);
        
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
