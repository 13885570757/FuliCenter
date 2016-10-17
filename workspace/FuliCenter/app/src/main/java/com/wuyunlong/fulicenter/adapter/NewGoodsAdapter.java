package com.wuyunlong.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.bean.NewGoodsBean;
import com.wuyunlong.fulicenter.utils.I;
import com.wuyunlong.fulicenter.utils.ImageLoader;

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


    public NewGoodsAdapter(Context mContext, List<NewGoodsBean> mlist) {
        this.mContext = mContext;
        this.mlist = mlist;
    }

    /**
     * 绑定布局文件
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case I.TYPE_FOOTER:
                holder = new FooterViewHolder(View.inflate(mContext,R.layout.item_footer,null));
                break;
            case I.TYPE_ITEM:
                holder = new NewGoodsViewHolder(View.inflate(mContext,R.layout.item_newgoods,null));
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
        if (getItemViewType(position)==I.TYPE_FOOTER){
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
        }else {
            NewGoodsViewHolder vh = (NewGoodsViewHolder) holder;
            NewGoodsBean goods = mlist.get(position);
            //setImage
            ImageLoader.downloadImg(mContext,vh.newgoodsPic,goods.getGoodsImg());
            vh.newgoodsName.setText(goods.getGoodsName());
            vh.newgoodsPrice.setText(goods.getCurrencyPrice());
        }
    }

    /**
     * 商品总量
     * @return
     */
    @Override
    public int getItemCount() {

        return mlist == null ? 0 : mlist.size() + 1;
    }

    /**
     * 判断返回数据
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        } else {
            return I.TYPE_ITEM;
        }
    }


    class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView lvFooter;

        public FooterViewHolder(View layout) {
            super(layout);
            lvFooter = (TextView) itemView.findViewById(R.id.tvFooter);
        }
    }


   class NewGoodsViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.newgoods_pic)
        ImageView newgoodsPic;
        @Bind(R.id.newgoods_name)
        TextView newgoodsName;
        @Bind(R.id.newgoods_price)
        TextView newgoodsPrice;

        NewGoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}