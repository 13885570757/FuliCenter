package com.wuyunlong.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.bean.CollectBean;
import com.wuyunlong.fulicenter.utils.ImageLoader;
import com.wuyunlong.fulicenter.utils.L;
import com.wuyunlong.fulicenter.utils.MFGT;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/17.
 */
public class CollectsAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<CollectBean> mList;

    RecyclerView parent;
    boolean isMore;
    String tvFooter;

    int sortBy = I.SORT_BY_PRICE_DESC;

    public int getSortBy() {
        return sortBy;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
        notifyDataSetChanged();
    }

    public String getTvFooter() {
        return tvFooter;
    }

    public void setTvFooter(String tvFooter) {
        this.tvFooter = tvFooter;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public CollectsAdapter(Context mContext, ArrayList<CollectBean> mList) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
        mList.addAll(mList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = (RecyclerView) parent;
        RecyclerView.ViewHolder holder = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout = null;
        switch (viewType) {
            case I.TYPE_FOOTER:
                layout = inflater.inflate(R.layout.item_newgoods_footer, parent, false);
                holder = new FooterViewHolder(layout);
                break;
            case I.TYPE_ITEM:
                layout = inflater.inflate(R.layout.item_collects, parent, false);
                holder = new CollectsViewHolder(layout);
                break;

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder footer = (FooterViewHolder) holder;
            footer.tvFooter.setText(getTvFooter());
        } else {
            CollectsViewHolder goods = (CollectsViewHolder) holder;
            CollectBean collectBean = mList.get(position);
            goods.lvGoodsIntroduce.setText(collectBean.getGoodsName());
         //   goods.lvGoodsPrice.setText(goodsBean.getShopPrice());
            ImageLoader.downloadImg(mContext, goods.lvGoodsImage, collectBean.getGoodsThumb(), true);
            goods.lvLayoutCollects.setTag(collectBean.getGoodsId());
            L.i("=========ColkectBean:" + collectBean.getGoodsId());

        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        } else {
            return I.TYPE_ITEM;
        }
    }




    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvFooter)
        TextView tvFooter;


        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class CollectsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.lv_goods_image)
        ImageView lvGoodsImage;
        @Bind(R.id.lv_goods_introduce)
        TextView lvGoodsIntroduce;
        @Bind(R.id.delete_collect)
        ImageView ivdelete;
        @Bind(R.id.layout_goods)
        RelativeLayout lvLayoutCollects;


        public CollectsViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.layout_goods)
        public void onGoodsItemClick() {
            int goodsId = (int) lvLayoutCollects.getTag();
//            mContext.startActivity(new Intent(mContext, GoodsDetailsActivity.class)
//                    .putExtra(I.GoodsDetails.KEY_GOODS_ID, goodsId));
            MFGT.gotoGoodsDetailsActivity(mContext, goodsId);

        }
    }

    public void initCollects(ArrayList<CollectBean> mList) {

        this.mList.clear();
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    public void addCollects(ArrayList<CollectBean> mList) {
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }


}
