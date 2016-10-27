package com.wuyunlong.fulicenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wuyunlong.fulicenter.FuLiCenterApplication;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.activity.MainActivity;
import com.wuyunlong.fulicenter.adapter.CartAdapter;
import com.wuyunlong.fulicenter.bean.CartBean;
import com.wuyunlong.fulicenter.bean.UserAvatarBean;
import com.wuyunlong.fulicenter.net.NetDao;
import com.wuyunlong.fulicenter.utils.ConvertUtils;
import com.wuyunlong.fulicenter.utils.L;
import com.wuyunlong.fulicenter.utils.OkHttpUtils;
import com.wuyunlong.fulicenter.views.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends BaseFragment {

    private static final String TAG = CartFragment.class.getSimpleName();

    ArrayList<CartBean> mList;
    MainActivity mContext;
    CartAdapter mAdapter;
    LinearLayoutManager llm;

    @Bind(R.id.new_goods_tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.new_goods_recycler)
    RecyclerView mRv;
    @Bind(R.id.new_goods_swipeRefresh)
    SwipeRefreshLayout mSrl;
    @Bind(R.id.tvSumPrice)
    TextView tvSumPrice;
    @Bind(R.id.tvSavePrice)
    TextView tvSavePrice;
    @Bind(R.id.btn_buyIt)
    Button btnBuyIt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        L.i("=======================购物车适配器onCreate");
        View view = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, view);
        mContext = (MainActivity) getContext();
        mList = new ArrayList<>();
        mAdapter = new CartAdapter(mContext, mList);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void setListener() {
        setPullDownListenr();//下拉刷新

    }

    private void setPullDownListenr() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                downloadCart();
            }
        });
    }

    @Override
    protected void initView() {
        
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        mRv.addItemDecoration(new SpaceItemDecoration(12));
    }

    @Override
    protected void initData() {
        downloadCart();

    }


    private void downloadCart() {
        UserAvatarBean user = FuLiCenterApplication.getUser();
        if (user != null) {
            NetDao.downloadCart(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    L.e(TAG);
                    mSrl.setRefreshing(false);
                    tvRefresh.setVisibility(View.GONE);
                    if (result != null && result.length > 0) {
                        ArrayList<CartBean> list = ConvertUtils.array2List(result);
                        mAdapter.initData(list);
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
