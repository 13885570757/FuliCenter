package com.wuyunlong.fulicenter.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.activity.MainActivity;
import com.wuyunlong.fulicenter.adapter.GoodsAdapter;
import com.wuyunlong.fulicenter.bean.NewGoodsBean;
import com.wuyunlong.fulicenter.net.NetDao;
import com.wuyunlong.fulicenter.net.OkHttpUtils;
import com.wuyunlong.fulicenter.utils.CommonUtils;
import com.wuyunlong.fulicenter.utils.ConvertUtils;
import com.wuyunlong.fulicenter.utils.L;
import com.wuyunlong.fulicenter.view.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class NewGoodsFragment extends BaseFragment {
    MainActivity mContext;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mList;
    int pageId = 1;
    GridLayoutManager glm;
    @Bind(R.id.tv_refresh)
    TextView mTvRefresh;
    @Bind(R.id.rv)
    RecyclerView mRv;
    @Bind(R.id.srl)
    SwipeRefreshLayout mSrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.e("NewGoodsFragment.onCreateView");
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        mTvRefresh = (TextView) layout.findViewById(R.id.tv_refresh);
        mRv = (RecyclerView) layout.findViewById(R.id.rv);
        mSrl = (SwipeRefreshLayout) layout.findViewById(R.id.srl);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();
        mList = new ArrayList<>();
        mAdapter = new GoodsAdapter(mContext, mList);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void setListener() {
        setPullUpListener();
        setPullDownListener();
    }

    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                mSrl.setEnabled(true);
                mTvRefresh.setVisibility(View.VISIBLE);
                pageId = 1;
                downloadNewGoods(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void downloadNewGoods(final int action) {
        NetDao.downloadNewGoods(mContext, I.CAT_ID, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                mSrl.setRefreshing(false);
                mSrl.setEnabled(false);
                mTvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(true);
                L.e("result=" + result);
                if (result != null && result.length > 0) {
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(list);
                    } else {
                        mAdapter.addData(list);
                    }
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mAdapter.setMore(false);
                    }
                } else {
                    mAdapter.setMore(false);
                }
            }

            @Override
            public void onError(String error) {
                mSrl.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(false);
                CommonUtils.showShortToast(error);
                L.e("error:" + error);
            }
        });
    }

    private void setPullUpListener() {
        mRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = glm.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastPosition == mAdapter.getItemCount() - 1
                        && mAdapter.isMore()) {
                    pageId++;
                    downloadNewGoods(I.ACTION_PULL_UP);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = glm.findFirstVisibleItemPosition();
                mSrl.setEnabled(firstPosition == 0);
            }
        });
    }

    @Override
    protected void initData() {
        downloadNewGoods(I.ACTION_DOWNLOAD);
    }

    @Override
    protected void initView() {
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        glm = new GridLayoutManager(mContext, I.COLUM_NUM);
        mRv.setLayoutManager(glm);
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(new SpaceItemDecoration(12));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
