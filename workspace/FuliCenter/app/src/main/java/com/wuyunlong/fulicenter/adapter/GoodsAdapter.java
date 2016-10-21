package com.wuyunlong.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.bean.NewGoodsBean;
import com.wuyunlong.fulicenter.utils.ImageLoader;
import com.wuyunlong.fulicenter.utils.MFGT;
import com.wuyunlong.fulicenter.view.FooterViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by clawpo on 2016/10/17.
 */

public class GoodsAdapter extends Adapter {
    Context mContext;
    List<NewGoodsBean> mList;
    boolean isMore;


    public GoodsAdapter(Context context, List<NewGoodsBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new GoodsViewHolder(View.inflate(mContext, R.layout.item_goods, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder vh = (FooterViewHolder) holder;
            vh.mTvFooter.setText(isMore?"加载中":"没有更多");
        } else {
            GoodsViewHolder vh = (GoodsViewHolder) holder;
            NewGoodsBean goods = mList.get(position);
            ImageLoader.downloadImg(mContext, vh.mIvGoodsThumb, goods.getGoodsThumb());
            vh.mTvGoodsName.setText(goods.getGoodsName());
            vh.mTvGoodsPrice.setText(goods.getCurrencyPrice());
            vh.mLayoutGoods.setTag(goods.getGoodsId());
        }
    }

    //private int getFootString() {
      //  return isMore ? R.string.load_more : R.string.no_more;
    //}

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class GoodsViewHolder extends ViewHolder {
        @Bind(R.id.ivGoodsThumb)
        ImageView mIvGoodsThumb;
        @Bind(R.id.tvGoodsName)
        TextView mTvGoodsName;
        @Bind(R.id.tvGoodsPrice)
        TextView mTvGoodsPrice;
        @Bind(R.id.layout_goods)
        LinearLayout mLayoutGoods;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.layout_goods)
        public void onGoodsItemClick() {
            int goodsId = (int) mLayoutGoods.getTag();
            MFGT.gotoGoodsDetailsActivity(mContext, goodsId);
        }
    }

}