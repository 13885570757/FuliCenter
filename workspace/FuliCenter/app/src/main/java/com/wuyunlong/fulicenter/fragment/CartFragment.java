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

import com.wuyunlong.fulicenter.FuLiCenterApplication;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.activity.MainActivity;
import com.wuyunlong.fulicenter.adapter.CartAdapter;
import com.wuyunlong.fulicenter.bean.CartBean;
import com.wuyunlong.fulicenter.bean.UserAvatarBean;
import com.wuyunlong.fulicenter.net.NetDao;
import com.wuyunlong.fulicenter.utils.L;
import com.wuyunlong.fulicenter.utils.OkHttpUtils;
import com.wuyunlong.fulicenter.utils.ResultUtils;
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
//    @Bind(R.id.tvSumPrice)
//    TextView tvSumPrice;
//    @Bind(R.id.tvSavePrice)
//    TextView tvSavePrice;
//    @Bind(R.id.btn_buyIt)
//    Button btnBuyIt;


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
            NetDao.downloadCart(mContext, user.getMuserName(),
                    new OkHttpUtils.OnCompleteListener<String>() {
                @Override
                public void onSuccess(String s) {
                 ArrayList<CartBean> list =    ResultUtils.getCartFromJson(s);
                    L.e("=========下载购物车"+list);
                    mSrl.setRefreshing(false);
                    tvRefresh.setVisibility(View.GONE);
                    if (list!= null && list.size()> 0) {
                        L.e(TAG,"list[0]"+list.get(0));
                        mAdapter.initData(list);
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }

    private  void sumPrice(){
        int sumPrice = 0;
        int rankPrice = 0;
        if (mList!=null&&mList.size()>0){
            for (CartBean c:mList){
                if (c.isChecked()){
                    sumPrice+=getPrice(c.getGoods().getCurrencyPrice())*c.getCount();
                    rankPrice+=getPrice(c.getGoods().getRankPrice())*c.getCount();
                }
            }
            tvSumPrice.setText("合计：￥"+Double.valueOf(sumPrice));
            tvSavePrice.setText("合计：￥"+Double.valueOf(sumPrice-rankPrice));
        }else {
            tvSumPrice.setText("合计：￥"+0);
            tvSavePrice.setText("合计：￥"+0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
