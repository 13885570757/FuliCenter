package com.wuyunlong.fulicenter.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wuyunlong.fulicenter.R;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/10/19.
 */
public class FooterViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tvFooter)
    public
    TextView mtvFooter;

    public FooterViewHolder(View layout) {
        super(layout);
        mtvFooter = (TextView) layout.findViewById(R.id.tvFooter);
    }
}
