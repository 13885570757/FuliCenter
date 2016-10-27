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

import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.bean.CartBean;
import com.wuyunlong.fulicenter.bean.GoodsDetailsBean;
import com.wuyunlong.fulicenter.utils.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class CartAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<CartBean> mList;

    RecyclerView parent;

    boolean isMore;


    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public CartAdapter(Context mContext, ArrayList<CartBean> list) {
        mContext = mContext;
        mList = new ArrayList<>();
        mList.addAll(list);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = (RecyclerView) parent;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout = null;
        RecyclerView.ViewHolder holder = new CartGoodsViewHolder(layout);
        switch (viewType) {
            case I.TYPE_FOOTER:
                layout = inflater.inflate(R.layout.item_newgoods_footer, parent, false);
                holder = new GoodsAdapter.FooterViewHolder(layout);
                break;
            case I.TYPE_ITEM:
                layout = inflater.inflate(R.layout.item_cart_good, parent, false);
                holder = new CartGoodsViewHolder(layout);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CartBean cartBean = mList.get(position);
        GoodsDetailsBean goods = cartBean.getGoods();
        if (goods != null) {
            ImageLoader.downloadImg(mContext, ((CartGoodsViewHolder) holder).imGoodsImage, goods.getGoodsThumb());
            ((CartGoodsViewHolder) holder).tvGoodsName.setText(goods.getGoodsName());
            // 价钱holder.
            ((CartGoodsViewHolder) holder).tvCartPrice.setText(goods.getCurrencyPrice());
        }
        ((CartGoodsViewHolder) holder).tvCartCount.setText("(" + cartBean.getCount() + ")");
        ((CartGoodsViewHolder) holder).chkGoods.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class CartGoodsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.chkGoods)
        CheckBox chkGoods;
        @Bind(R.id.imGoodsImage)
        ImageView imGoodsImage;
        @Bind(R.id.tvGoodsName)
        TextView tvGoodsName;
        @Bind(R.id.imAdd)
        ImageView imAdd;
        @Bind(R.id.tvCartCount)
        TextView tvCartCount;//商品数量
        @Bind(R.id.tvCartPrice)
        TextView tvCartPrice;//商品价格
        @Bind(R.id.imDel)
        ImageView imDel;
        @Bind(R.id.rlCart)
        RelativeLayout rlCart;

        CartGoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvFooter)
        TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void initData(ArrayList<CartBean> list) {
        this.mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }
}
