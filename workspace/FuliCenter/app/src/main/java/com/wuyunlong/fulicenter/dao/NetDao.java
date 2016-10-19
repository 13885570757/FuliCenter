package com.wuyunlong.fulicenter.dao;

import android.content.Context;

import com.wuyunlong.fulicenter.GoodsDetails;
import com.wuyunlong.fulicenter.bean.BoutiqueBean;
import com.wuyunlong.fulicenter.bean.GoodsDetailsBean;
import com.wuyunlong.fulicenter.bean.NewGoodsBean;
import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/10/17.
 * 数据操作,拼接URL语句
 */
public class NetDao {
        public static void downloadNewGoods(Context context, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener) {
                OkHttpUtils utils = new OkHttpUtils(context);
                utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                        .addParam(I.NewAndBoutiqueGoods.CAT_ID,String .valueOf(I.CAT_ID))
                        .addParam(I.PAGE_ID,String.valueOf(pageId))
                        .addParam(I.PAGE_SIZE,String.valueOf(I.PAGE_SIZE_DEFAULT))
                        .targetClass(NewGoodsBean[].class)
                        .execute(listener);
        }
        public static void downloadGoodsDetail (
                Context context, int goodsId, OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener){
                OkHttpUtils utils = new OkHttpUtils(context);
                utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                        .addParam(I.GoodsDetails.KEY_GOODS_ID,String.valueOf(goodsId))
                        .targetClass(GoodsDetailsBean.class)
                        .execute(listener);
        }

        public static void downloadBoutique(
                Context context, OkHttpUtils.
                OnCompleteListener<BoutiqueBean> listener){
                OkHttpUtils utils = new OkHttpUtils(context);
                utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                        .addParam(I.Boutique.KEY_GOODS,
                                String.valueOf())
        }
}
