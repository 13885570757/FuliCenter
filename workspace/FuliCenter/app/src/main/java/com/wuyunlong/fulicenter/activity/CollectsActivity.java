package com.wuyunlong.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wuyunlong.fulicenter.FuLiCenterApplication;
import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.adapter.CollectsAdapter;
import com.wuyunlong.fulicenter.bean.CollectBean;
import com.wuyunlong.fulicenter.bean.UserAvatarBean;
import com.wuyunlong.fulicenter.net.NetDao;
import com.wuyunlong.fulicenter.utils.ConvertUtils;
import com.wuyunlong.fulicenter.utils.OkHttpUtils;
import com.wuyunlong.fulicenter.views.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CollectsActivity extends AppCompatActivity {
    CollectsActivity mContext;
    @Bind(R.id.new_goods_tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.new_goods_recycler)
    RecyclerView mRv;
    @Bind(R.id.new_goods_swipeRefresh)
    SwipeRefreshLayout mSrl;

    GridLayoutManager glm;

    CollectsAdapter mAdapter;
    ArrayList<CollectBean> mList;
    int pageId = 1;

    UserAvatarBean user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_collects);
        ButterKnife.bind(this);
        mContext = this;
        mList = new ArrayList<>();
        mAdapter = new CollectsAdapter(mContext,mList);
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
    }
    private void initView() {
//        DisplayUtils.initBackWithTitle(mContext, getResources().getString(0));
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_yellow)
        );
        glm = new GridLayoutManager(mContext, I.COLUM_NUM);
        mRv.setLayoutManager(glm);
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(new SpaceItemDecoration(12));
    }

    private void setListener() {
        setPullUpListener();
        setPullDownListener();

    }

    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                pageId=1;
                downloadCollects(I.ACTION_PULL_DOWN);
            }
        });

    }

    private void downloadCollects(final int action) {
        NetDao.downloadCollects(mContext, user.getMuserName(), pageId, new OkHttpUtils.OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                mSrl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(true);
                if (result!=null&&result.length>0){
                    ArrayList<CollectBean> list = ConvertUtils.array2List(result);
                    if (action==I.ACTION_DOWNLOAD||action==I.ACTION_PULL_DOWN){
                        mAdapter.initCollects(list);
                    }else {
                       mAdapter.addCollects(list);
                    }if (list.size()<I.PAGE_ID_DEFAULT){
                        mAdapter.setMore(false);
                    }
                }else {
                    mAdapter.setMore(false);
                }

            }

            @Override
            public void onError(String error) {

            }
        });
    }


    private void setPullUpListener() {
        mRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
           int lastPosition = glm.findLastVisibleItemPosition();
                if (newState==RecyclerView.SCROLL_STATE_IDLE
                    &&lastPosition==mAdapter.getItemCount()-1
                        &&mAdapter.isMore()){
                    pageId++;
                    downloadCollects(I.ACTION_PULL_UP);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = glm.findFirstVisibleItemPosition();
                mSrl.setEnabled(firstPosition==0);
            }
        });

    }

    private void initData() {
        user = FuLiCenterApplication.getUser();
        if (user==null){//判断我的收藏是否有物品
            finish();
        }
        downloadCollects(I.ACTION_DOWNLOAD);

    }

    /**
     * 同步数据
     */
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
