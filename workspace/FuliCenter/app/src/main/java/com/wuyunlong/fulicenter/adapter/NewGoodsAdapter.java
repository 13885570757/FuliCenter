package com.wuyunlong.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.bean.NewGoodsBean;
import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.utils.ImageLoader;
import com.wuyunlong.fulicenter.utils.MFGT;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/17.
 */
public class NewGoodsAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<NewGoodsBean> mlist;

    boolean isMore;//通知
    @Bind(R.id.newgoods_pic)
    ImageView newgoodsPic;
    @Bind(R.id.newgoods_name)
    TextView newgoodsName;
    @Bind(R.id.newgoods_price)
    TextView newgoodsPrice;
    @Bind(R.id.onclick)
    RelativeLayout onclick;


    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public NewGoodsAdapter(Context mContext, List<NewGoodsBean> list) {
        this.mContext = mContext;
        this.mlist = list;
        mlist.addAll(list);//仅仅显示数据
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
                holder = new NewGoodsViewHolder(View.inflate(mContext, R.layout.item_newgoods, null));
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
            // vh.tvFooter.setText(getFooterString());
        } else {
            NewGoodsViewHolder vh = (NewGoodsViewHolder) holder;
            NewGoodsBean goods = mlist.get(position);
            //setImage
            ImageLoader.downloadImg(mContext, vh.newgoodsPic, goods.getGoodsImg());
            vh.newgoodsName.setText(goods.getGoodsName());
            vh.newgoodsPrice.setText(goods.getCurrencyPrice());
            vh.relativeLayout.setTag(goods.getGoodsId());
        }
    }

    /**
     * 商品总量
     *
     * @return
     */
    @Override
    public int getItemCount() {

        return mlist == null ? 0 : mlist.size() + 1;
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
        } else {
            return I.TYPE_ITEM;
        }
    }

    private int getFooterString() {
        return isMore ? R.string.load_more : R.string.no_more;
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mlist.addAll(list);
        notifyDataSetChanged();
    }




   static class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvFooter)
        TextView tvFooter;

        public  FooterViewHolder(View layout) {
            super(layout);
            tvFooter = (TextView) itemView.findViewById(R.id.tvFooter);
        }
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mlist != null) {
            mlist.clear();
        }
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    class NewGoodsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.newgoods_pic)
        ImageView newgoodsPic;
        @Bind(R.id.newgoods_name)
        TextView newgoodsName;
        @Bind(R.id.newgoods_price)
        TextView newgoodsPrice;
        @Bind(R.id.onclick)
                RelativeLayout relativeLayout;

        NewGoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.onclick)
        public void onClick() {
            int id = (int) relativeLayout.getTag();
            MFGT.gotogoodsDeatilsActivity(mContext,id);
        }

    }
}
