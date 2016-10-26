package com.wuyunlong.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.adapter.BouTiQueChildAdapter;
import com.wuyunlong.fulicenter.bean.BoutiqueBean;
import com.wuyunlong.fulicenter.bean.GoodsDetailsBean;
import com.wuyunlong.fulicenter.net.NetDao;
import com.wuyunlong.fulicenter.utils.CommonUtils;
import com.wuyunlong.fulicenter.utils.ConvertUtils;
import com.wuyunlong.fulicenter.utils.ImageLoader;
import com.wuyunlong.fulicenter.utils.L;
import com.wuyunlong.fulicenter.utils.MFGT;
import com.wuyunlong.fulicenter.utils.OkHttpUtils;
import com.wuyunlong.fulicenter.views.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BoutiqueActivity extends AppCompatActivity {

    @Bind(R.id.lv_details_back)
    ImageView lvDetailsBack;
    @Bind(R.id.boutiqueTvrefresh)
    TextView boutiqueTvrefresh;
    @Bind(R.id.boutiqueRecycler)
    RecyclerView boutiqueRecycler;
    @Bind(R.id.boutiqueSwipeRefresh)
    SwipeRefreshLayout boutiqueSwipeRefresh;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    BoutiqueBean boutiqueBean;
    int mPageId = 1;
    BouTiQueChildAdapter mBtqAdapter;
    ArrayList<GoodsDetailsBean> mList;
    GridLayoutManager mManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bout_ti_que);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        boutiqueBean = (BoutiqueBean) intent.getSerializableExtra(I.Boutique.ID);
        L.i("接收" + boutiqueBean);
        if (boutiqueBean == null) {
            finish();
        }
        initView();
        initData(I.ACTION_DOWNLOAD);
        setListener();
    }

    private void setListener() {
        setOnPullDownListener();

    }

    @OnClick(R.id.lv_details_back)
    public void onBackListener() {
        lvDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MFGT.finish(BoutiqueActivity.this);
            }
        });
    }

    private void setOnPullDownListener() {
        boutiqueSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                boutiqueSwipeRefresh.setEnabled(true);
                boutiqueSwipeRefresh.setRefreshing(true);
                boutiqueTvrefresh.setVisibility(View.VISIBLE);
                mPageId = 1;
                initData(I.ACTION_PULL_DOWN);
            }
        });

    }

    private void initView() {
        mManager = new GridLayoutManager(this, I.COLUM_NUM, LinearLayoutManager.VERTICAL, false);
        mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == mBtqAdapter.getItemCount() - 1 ? 2 : 1;
            }
        });
        mList = new ArrayList<>();
        mBtqAdapter = new BouTiQueChildAdapter(this, mList);
        boutiqueRecycler.setAdapter(mBtqAdapter);
        boutiqueRecycler.setLayoutManager(mManager);
        boutiqueSwipeRefresh.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        boutiqueRecycler.addItemDecoration(new SpaceItemDecoration(12));
        tvTitle.setText(boutiqueBean.getTitle());
    }

    private void initData(final int actionDownload) {
        int id = boutiqueBean.getId();
        NetDao.downloadBouTiQueChild(this, id, mPageId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean[]>() {
            @Override
            public void onSuccess(GoodsDetailsBean[] result) {
                if (result != null && result.length > 0) {

                    mBtqAdapter.setMore(true);
                    ArrayList<GoodsDetailsBean> list = ConvertUtils.array2List(result);
                    switch (actionDownload) {
                        case I.ACTION_DOWNLOAD:

                            mBtqAdapter.initNewGoods(list);
                            mBtqAdapter.setMore(true);
                            break;
                        case I.ACTION_PULL_DOWN:
                            mBtqAdapter.initNewGoods(list);
                            mBtqAdapter.setMore(true);
                            boutiqueSwipeRefresh.setRefreshing(false);
                            boutiqueTvrefresh.setVisibility(View.GONE);
                            ImageLoader.release();
                            break;

                    }
                    L.i(list.toString());
                }
            }

            @Override
            public void onError(String error) {
                boutiqueSwipeRefresh.setRefreshing(false);
                CommonUtils.showShortToast("出错");
            }
        });
    }


}
