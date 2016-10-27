package com.wuyunlong.fulicenter.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.bean.CartBean;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class CartAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<CartBean> mList;

    public CartAdapter(Context context, ArrayList<CartBean> list) {
        mContext = context;
        this.mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder holder = new CartViewHolder(LayoutInflater.
                from(mContext).inflate(R.layout.item_cart_good, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CartBean cartBean = mList.get(position);
        CartViewHolder goods = (CartViewHolder) holder;
        //goods.tvGoodsName.setText(cartBean.getUserName());
        //goods.tvCartPrice.setText(cartBean.get());
       // ImageLoader.downloadImg(mContext, goods.imGoodsImage, cartBean.(), true);
       // goods.rlCart.setTag(cartBean.getGoodsId());

    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }
    public void initData(ArrayList<CartBean> list){
        if (mList!=null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.chkGoods)
        CheckBox chkGoods;
        @Bind(R.id.imGoodsImage)
        ImageView imGoodsImage;
        @Bind(R.id.tvGoodsName)
        TextView tvGoodsName;
        @Bind(R.id.imAdd)
        ImageView imAdd;
        @Bind(R.id.tvCartCount)
        TextView tvCartCount;
        @Bind(R.id.imDel)
        ImageView imDel;
        @Bind(R.id.tvCartPrice)
        TextView tvCartPrice;
        @Bind(R.id.rlCart)
        RelativeLayout rlCart;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
