package com.wuyunlong.fulicenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GoodsDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
    }

    static class ViewHolder {
        @Bind(R.id.goods_name)
        TextView goodsName;
        @Bind(R.id.goods_attr)
        TextView goodsAttr;
        @Bind(R.id.goods_pic)
        ImageView goodsPic;
        @Bind(R.id.goods_brief)
        TextView goodsBrief;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
