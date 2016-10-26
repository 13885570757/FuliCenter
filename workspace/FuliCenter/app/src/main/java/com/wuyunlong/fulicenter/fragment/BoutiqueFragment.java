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

import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.activity.MainActivity;
import com.wuyunlong.fulicenter.adapter.BoutiqueAdapter;
import com.wuyunlong.fulicenter.bean.BoutiqueBean;
import com.wuyunlong.fulicenter.net.NetDao;
import com.wuyunlong.fulicenter.utils.CommonUtils;
import com.wuyunlong.fulicenter.utils.ConvertUtils;
import com.wuyunlong.fulicenter.utils.ImageLoader;
import com.wuyunlong.fulicenter.utils.L;
import com.wuyunlong.fulicenter.utils.OkHttpUtils;
import com.wuyunlong.fulicenter.views.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends BaseFragment {


    @Bind(R.id.new_goods_tv_refresh)
    TextView newGoodsTvRefresh;
    @Bind(R.id.new_goods_recycler)
    RecyclerView newGoodsRecycler;
    @Bind(R.id.new_goods_swipeRefresh)
    SwipeRefreshLayout newGoodsSwipeRefresh;
    BoutiqueAdapter mBtqAdapter;
    MainActivity mContext;
    ArrayList<BoutiqueBean> mList;
    LinearLayoutManager mManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, view);
        mContext = (MainActivity) getContext();
        mList = new ArrayList<BoutiqueBean>();
        mBtqAdapter = new BoutiqueAdapter(mContext, mList);
        mManager = new LinearLayoutManager(mContext);
        newGoodsRecycler.setLayoutManager(mManager);
        newGoodsRecycler.setAdapter(mBtqAdapter);
        downloadBoutique(I.ACTION_DOWNLOAD);
//        initView();
//        initData();
//        setListener();
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void setListener() {
        setOnPullDownListener();
    }

    private void setOnPullDownListener() {
        newGoodsSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newGoodsSwipeRefresh.setEnabled(true);
                newGoodsSwipeRefresh.setRefreshing(true);
                newGoodsTvRefresh.setVisibility(View.VISIBLE);
                downloadBoutique(I.ACTION_PULL_DOWN);
            }
        });
    }

    @Override
    protected void initView() {
        newGoodsSwipeRefresh.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        newGoodsRecycler.addItemDecoration(new SpaceItemDecoration(12));

    }


    @Override
    protected void initData() {
        downloadBoutique(I.ACTION_DOWNLOAD);
    }

    protected void downloadBoutique(final int actionDownload) {
        NetDao.downloadBouTiQue(mContext, new OkHttpUtils.OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                if (result != null && result.length > 0) {
                    mBtqAdapter.setMore(true);
                    ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                    switch (actionDownload) {
                        case I.ACTION_DOWNLOAD:
                            mBtqAdapter.initBouTiQue(list);
                            mBtqAdapter.setMore(true);
                            break;
                        case I.ACTION_PULL_DOWN:
                            mBtqAdapter.initBouTiQue(list);
                            mBtqAdapter.setMore(true);
                            newGoodsSwipeRefresh.setRefreshing(false);
                            newGoodsTvRefresh.setVisibility(View.GONE);
                            ImageLoader.release();
                            break;

                    }
                    L.i(list.toString());
                }
            }

            @Override
            public void onError(String error) {
                newGoodsSwipeRefresh.setRefreshing(false);
                CommonUtils.showShortToast("出错");
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
