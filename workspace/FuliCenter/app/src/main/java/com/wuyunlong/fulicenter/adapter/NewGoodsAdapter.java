package com.wuyunlong.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.bean.NewGoodsBean;
import com.wuyunlong.fulicenter.utils.ImageLoader;
import com.wuyunlong.fulicenter.view.FooterViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/17.
 */
public class NewGoodsAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<NewGoodsBean> mlist;

    //构造方法
    public NewGoodsAdapter(Context context, List<NewGoodsBean> list) {
        this.mContext = context;
        mlist = new ArrayList<>();
        mlist.addAll(list);
    }

    boolean isMore;//通知

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }


    /**
     * 绑定布局文件
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case I.TYPE_FOOTER:
                holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
                break;
            case I.TYPE_ITEM:
                holder = new NewGoodsViewHolder(View.inflate(mContext, R.layout.item_goods, null));
                break;
        }
        return holder;
    }

    /**
     * 数据绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder vh = (FooterViewHolder) holder;
            vh.mTvFooter.setText(getFooterString());
        } else {
            NewGoodsViewHolder vh = (NewGoodsViewHolder) holder;
            NewGoodsBean goods = mlist.get(position);
            ImageLoader.downloadImg(mContext, vh.ivGoodsThumb, goods.getGoodsImg());
            vh.tvGoodsName.setText(goods.getGoodsName());
            vh.tvGoodsPrice.setText(goods.getCurrencyPrice());
            vh.layoutGoods.setTag(goods.getGoodsId());
        }
    }

    private int getFooterString() {
        //return isMore ? "加载中" : "没有更多";
        return isMore?R.string.load_more:R.string.no_more;
    }

    /**
     * 商品总量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mlist == null ? mlist.size() + 1 : 1;
    }

    /**
     * 判断返回数据
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }


    public void addData(ArrayList<NewGoodsBean> list) {
        mlist.addAll(list);
        notifyDataSetChanged();
    }


    public void initData(ArrayList<NewGoodsBean> list) {
        if (mlist != null) {
            mlist.clear();
        }
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    static class NewGoodsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @Bind(R.id.tvGoodsName)
        TextView tvGoodsName;
        @Bind(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;
        @Bind(R.id.layout_goods)
        LinearLayout layoutGoods;

        NewGoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
