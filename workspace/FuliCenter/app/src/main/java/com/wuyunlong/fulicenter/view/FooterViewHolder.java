package com.wuyunlong.fulicenter.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wuyunlong.fulicenter.R;
import butterknife.Bind;
import butterknife.ButterKnife;


public class FooterViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tvFooter)
    public TextView mTvFooter;

    public FooterViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
