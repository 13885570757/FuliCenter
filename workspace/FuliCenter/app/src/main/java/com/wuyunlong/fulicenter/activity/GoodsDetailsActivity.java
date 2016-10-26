package com.wuyunlong.fulicenter.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuyunlong.fulicenter.FuLiCenterApplication;
import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.bean.AlbumsBean;
import com.wuyunlong.fulicenter.bean.CartResultBean;
import com.wuyunlong.fulicenter.bean.GoodsDetailsBean;
import com.wuyunlong.fulicenter.bean.MessageBean;
import com.wuyunlong.fulicenter.bean.UserAvatarBean;
import com.wuyunlong.fulicenter.net.NetDao;
import com.wuyunlong.fulicenter.utils.CommonUtils;
import com.wuyunlong.fulicenter.utils.L;
import com.wuyunlong.fulicenter.utils.MFGT;
import com.wuyunlong.fulicenter.utils.OkHttpUtils;
import com.wuyunlong.fulicenter.views.FlowIndicator;
import com.wuyunlong.fulicenter.views.SlideAutoLoopView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/10/17.
 */
public class GoodsDetailsActivity extends BaseActivity {


    @Bind(R.id.detailsEnglishName)
    TextView detailsEnglishName;
    @Bind(R.id.detailsGoodsName)
    TextView detailsGoodsName;
    @Bind(R.id.detailsColor)
    TextView detailsColor;
    @Bind(R.id.detailsPriceShop)
    TextView detailsPrice;
    @Bind(R.id.detailsPriceCurrent)
    TextView detailsPriceCurrent;
    @Bind(R.id.detailsSlideAutoLoopView)
    SlideAutoLoopView detailsSlideAutoLoopView;
    @Bind(R.id.detailsGoods)
    WebView detailsGoods;
    @Bind(R.id.detailsFlowIndicator)
    FlowIndicator detailsFlowIndicator;
    @Bind(R.id.lv_details_back)
    ImageView lvDetailsBack;
    @Bind(R.id.iv_details_cart)//加入购物车
    ImageView ivDetailsCart;
    @Bind(R.id.iv_details_collect)//收藏
    ImageView ivDetailsCollect;
    @Bind(R.id.iv_details_share)//分享
    ImageView ivDetailsShare;

    String userName;
    int goodsId;
    GoodsDetailsActivity mContext;

    boolean isCollected = false;//商品是否为收藏状态，默认不收藏


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.item_goods_details);
        ButterKnife.bind(this);
        userName = FuLiCenterApplication.userName;
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        if (goodsId == 0) {
            finish();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @Override
    protected void initData() {
        NetDao.downloadGoodsDetail(mContext, goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                L.i(result.toString());
                if (result != null) {
                    showGoodDetails(result);
                } else {
                    finish();
                }
            }


            @Override
            public void onError(String error) {
                finish();
                CommonUtils.showLongToast(error + "");
            }
        });
    }

    @OnClick(R.id.lv_details_back)
    public void onBackClick() {
        MFGT.finish(this);
    }

    private void showGoodDetails(GoodsDetailsBean details) {
        detailsEnglishName.setText(details.getGoodsEnglishName());
        detailsGoodsName.setText(details.getGoodsName());
        detailsPrice.setText(details.getShopPrice());
        detailsPriceCurrent.setText(details.getCurrencyPrice());
        detailsSlideAutoLoopView.startPlayLoop(detailsFlowIndicator, getAlbumImgUrl(details), getAlbumImgCount(details));
        detailsGoods.loadDataWithBaseURL(null, details.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
    }

    private int getAlbumImgCount(GoodsDetailsBean details) {
        if (details.getProperties() != null && details.getProperties().length > 0) {
            return details.getProperties()[0].getAlbums().length;
        }
        return 0;

    }

    private String[] getAlbumImgUrl(GoodsDetailsBean details) {
        String[] urls = new String[]{};
        if (details.getProperties() != null && details.getProperties().length > 0) {
            AlbumsBean[] albums = details.getProperties()[0].getAlbums();
            urls = new String[albums.length];
            for (int i = 0; i < albums.length; i++) {
                urls[i] = albums[i].getImgUrl();
            }
            L.i(urls[0].toString());
        }
        return urls;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick({R.id.lv_details_back, R.id.iv_details_cart, R.id.iv_details_collect, R.id.iv_details_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lv_details_back:
                MFGT.finish(this);
                break;
            case R.id.iv_details_cart:
                int count = 1;
                initCartData(count);
                break;
            case R.id.iv_details_collect:
                addCollect();
                break;
            case R.id.iv_details_share:
                break;
        }
    }

    /**
     * 将商品设为我的收藏
     */
    private void addCollect() {
            UserAvatarBean user = FuLiCenterApplication.getUser();
            if (user==null){//若user为空，判断为未登录，跳转到登录界面
                MFGT.gotoLoginActivity(mContext);
            }else {
                if (isCollected){
                    //取消收藏
                }else{
                    NetDao.addCollect(mContext,user.getMuserName(),goodsId, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result!=null&&result.isSuccess()){
                                isCollected = !isCollected;
                                updateGoodsCollectStatus();
                                CommonUtils.showShortToast("已添加收藏");
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                }
            }
    }

    private void initCartData(int count) {
        NetDao.downloadCart(mContext, goodsId, userName, count, true, new OkHttpUtils.OnCompleteListener<CartResultBean>() {
            @Override
            public void onSuccess(CartResultBean result) {
                if (result.getSuccess() == true) {
                    CommonUtils.showShortToast("已加入购物车");
                } else {
                    CommonUtils.showShortToast("加入失败");
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isCollected();
    }

    /**
     * 判断商品是否为收藏状态
     */
    private  void isCollected(){
        UserAvatarBean user = FuLiCenterApplication.getUser();
        if (user!=null){//判断商品是否收藏
            NetDao.isCollected(mContext, user.getMuserName(), goodsId, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result!=null&&result.isSuccess()){
                        isCollected=true;
                        updateGoodsCollectStatus();
                    }
                }
                @Override
                public void onError(String error) {

                }
            });
        }
        updateGoodsCollectStatus();
    }

    /**
     * 商品是否收藏的显示状态
     */
    private void updateGoodsCollectStatus() {
        if (isCollected){
            ivDetailsCollect.setImageResource(R.mipmap.bg_collect_out);
        }else{
            ivDetailsCollect.setImageResource(R.mipmap.bg_collect_in);
        }
    }
}
