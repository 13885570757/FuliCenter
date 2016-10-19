package com.wuyunlong.fulicenter.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.R;
import com.wuyunlong.fulicenter.bean.AlbumsBean;
import com.wuyunlong.fulicenter.bean.GoodsDetailsBean;
import com.wuyunlong.fulicenter.net.NetDao;
import com.wuyunlong.fulicenter.net.OkHttpUtils;
import com.wuyunlong.fulicenter.utils.CommonUtils;
import com.wuyunlong.fulicenter.utils.L;
import com.wuyunlong.fulicenter.utils.MFGT;
import com.wuyunlong.fulicenter.view.FlowIndicator;
import com.wuyunlong.fulicenter.view.SlideAutoLoopView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GoodsDetailActivity extends BaseActivity {

    @Bind(R.id.backClickArea)
    LinearLayout mBackClickArea;
    @Bind(R.id.tv_good_name_english)
    TextView mTvGoodNameEnglish;
    @Bind(R.id.tv_good_name)
    TextView mTvGoodName;
    @Bind(R.id.tv_good_price_shop)
    TextView mTvGoodPriceShop;
    @Bind(R.id.tv_good_price_current)
    TextView mTvGoodPriceCurrent;
    @Bind(R.id.salv)
    SlideAutoLoopView mSalv;
    @Bind(R.id.indicator)
    FlowIndicator mIndicator;
    @Bind(R.id.wv_good_brief)
    WebView mWvGoodBrief;

    int goodsId;
    GoodsDetailActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.e("details", "goodsid=" + goodsId);
        if(goodsId==0){
            finish();
        }
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected  void initData() {
        NetDao.downloadGoodsDetail(mContext, goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                L.i("details="+result);
                if(result!=null){
                    showGoodDetails(result);
                }else{
                    finish();
                }
            }

            @Override
            public void onError(String error) {
                finish();
                L.e("details,error="+error);
                CommonUtils.showShortToast(error);
            }
        });
    }

    private void showGoodDetails(GoodsDetailsBean details) {
        mTvGoodNameEnglish.setText(details.getGoodsEnglishName());
        mTvGoodName.setText(details.getGoodsName());
        mTvGoodPriceCurrent.setText(details.getCurrencyPrice());
        mTvGoodPriceShop.setText(details.getShopPrice());
        mSalv.startPlayLoop(mIndicator,getAlbumImgUrl(details),getAlbumImgCount(details));
        mWvGoodBrief.loadDataWithBaseURL(null,details.getGoodsBrief(),I.TEXT_HTML,I.UTF_8,null);
    }

    private int getAlbumImgCount(GoodsDetailsBean details) {
        if(details.getProperties()!=null && details.getProperties().length>0) {
            return details.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumImgUrl(GoodsDetailsBean details) {
        String[] urls = new String[]{};
        if(details.getProperties()!=null && details.getProperties().length>0){
            AlbumsBean[] albums = details.getProperties()[0].getAlbums();
            urls = new String[albums.length];
            for(int i=0;i<albums.length;i++){
                urls[i] = albums[i].getImgUrl();
            }
        }
        return urls;
    }

    @Override
    protected  void initView() {

    }

    @OnClick(R.id.backClickArea)
    public void onBackClick(){
        MFGT.finish(this);
    }

}
