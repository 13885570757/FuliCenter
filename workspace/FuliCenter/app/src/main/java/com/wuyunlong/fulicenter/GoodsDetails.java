package com.wuyunlong.fulicenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuyunlong.fulicenter.bean.AlbumsBean;
import com.wuyunlong.fulicenter.bean.GoodsDetailsBean;
import com.wuyunlong.fulicenter.dao.NetDao;
import com.wuyunlong.fulicenter.utils.CommonUtils;
import com.wuyunlong.fulicenter.utils.OkHttpUtils;
import com.wuyunlong.fulicenter.views.FlowIndicator;
import com.wuyunlong.fulicenter.views.SlideAutoLoopView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GoodsDetails extends AppCompatActivity {

    @Bind(R.id.backClickArea)
    LinearLayout backClickArea;
    @Bind(R.id.tv_good_detaul_name_english)
    TextView tvGoodDetaulNameEnglish;
    @Bind(R.id.tv_good_detaul_name)
    TextView tvGoodName;
    @Bind(R.id.tv_good_price_shop)
    TextView tvGoodPriceShop;
    @Bind(R.id.tv_good_price_currency_shop)
    TextView tvGoodPriceCurrencyShop;
    @Bind(R.id.salv)
    SlideAutoLoopView salv;
    @Bind(R.id.indicator)
    FlowIndicator indicator;
    @Bind(R.id.wv_good_brief)
    WebView wvGoodBrief;
    GoodsDetails mContext;

    int goodsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        if (goodsId == 0) {
            finish();
        }
        initView();
        initData();
        setListener();

    }

    private void setListener() {


    }

    private void initData() {
        NetDao.downloadGoodsDetail(mContext, goodsId,
                new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
                    @Override
                    public void onSuccess(GoodsDetailsBean result) {
                        if (result != null) {
                            showGoodsDetails(result);
                        } else {
                            finish();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        finish();
                        CommonUtils.showShortToast(error);
                    }
                });
    }


    private void showGoodsDetails(GoodsDetailsBean details) {
        tvGoodDetaulNameEnglish.setText(details.getGoodsEnglishName());
        tvGoodName.setText(details.getGoodsName());
        tvGoodPriceCurrencyShop.setText(details.getCurrencyPrice());
        tvGoodPriceShop.setText(details.getShopPrice());
        salv.startPlayLoop(indicator, getAlbumImgUrl(details),
                getAlbumImgCount(details));
        wvGoodBrief.loadDataWithBaseURL(null, details.
                getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);


    }

    /**
     * 获取图片数量
     *
     * @param details
     * @return
     */
    private int getAlbumImgCount(GoodsDetailsBean details) {
        if (details.getProperties() != null && details.getProperties().length > 0) {

        }
        return details.getProperties()[0].getAlbums().length;
    }


    /**
     * 获取图片Url
     *
     * @param details
     * @return
     */
    private String[] getAlbumImgUrl(GoodsDetailsBean details) {
        String[] urls = new String[]{};
        if (details.getProperties() != null && details.getProperties().length > 0) {
            AlbumsBean[] albums = details.getProperties()[0].getAlbums();
            for (int i = 0; i < albums.length; i++) {
                urls[i] = albums[i].getImgUrl();
            }
        }
        return urls;
    }

    private void initView() {

    }

}
