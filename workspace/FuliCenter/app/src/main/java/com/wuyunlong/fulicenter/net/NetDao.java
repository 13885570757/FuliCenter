package com.wuyunlong.fulicenter.net;

import android.content.Context;

import com.wuyunlong.fulicenter.I;
import com.wuyunlong.fulicenter.bean.BoutiqueBean;
import com.wuyunlong.fulicenter.bean.CartResultBean;
import com.wuyunlong.fulicenter.bean.CategoryChildBean;
import com.wuyunlong.fulicenter.bean.CategoryGroupBean;
import com.wuyunlong.fulicenter.bean.CollectBean;
import com.wuyunlong.fulicenter.bean.GoodsDetailsBean;
import com.wuyunlong.fulicenter.bean.MessageBean;
import com.wuyunlong.fulicenter.bean.NewGoodsBean;
import com.wuyunlong.fulicenter.bean.Result;
import com.wuyunlong.fulicenter.utils.MD5;
import com.wuyunlong.fulicenter.utils.OkHttpUtils;

import java.io.File;


/**
 * Created by Administrator on 2016/10/17.
 */
public class NetDao {

    public static void downloadCateGroup(Context mContext, OkHttpUtils.OnCompleteListener<CategoryGroupBean[]> listener) {
        OkHttpUtils<CategoryGroupBean[]> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }

    public static void downloadCateChild(Context mContext, int parentId, OkHttpUtils.OnCompleteListener<CategoryChildBean[]> listener) {
        OkHttpUtils<CategoryChildBean[]> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID, String.valueOf(parentId))
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }

    public static void downloadNewGoods(Context mContext, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID, String.valueOf(I.CAT_ID))
                .addParam(I.PAGE_ID, String.valueOf(pageId))
                .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);

    }

    /**
     * 下载商品详情
     * @param context
     * @param goodsId
     * @param listener
     */
    public static void downloadGoodsDetail(Context context, int goodsId, OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener) {
        OkHttpUtils<GoodsDetailsBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.GoodsDetails.KEY_GOODS_ID, String.valueOf(goodsId))
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);

    }

    /**
     * 下载精选商品
     * @param context
     * @param listener
     */
    public static void downloadBouTiQue(Context context, OkHttpUtils.OnCompleteListener<BoutiqueBean[]> listener) {
        OkHttpUtils<BoutiqueBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
                .targetClass(BoutiqueBean[].class)
                .execute(listener);
    }

    /**
     * 下载分类大类
     * @param mContext
     * @param catId
     * @param pageId
     * @param listener
     */
    public static void downloadBouTiQueChild(Context mContext, int catId, int pageId, OkHttpUtils.OnCompleteListener<GoodsDetailsBean[]> listener) {
        OkHttpUtils<GoodsDetailsBean[]> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID, String.valueOf(catId))
                .addParam(I.PAGE_ID, String.valueOf(pageId))
                .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(GoodsDetailsBean[].class)
                .execute(listener);

    }

    /**
     * 下载分类子类
     * @param mContext
     * @param catId
     * @param pageId
     * @param listener
     */

    public static void downloadCategoryChild(Context mContext, int catId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID, String.valueOf(catId))
                .addParam(I.PAGE_ID, String.valueOf(pageId))
                .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);

    }

    /**
     * 登录
     * @param mContext
     * @param userName
     * @param passWord
     * @param listener
     */
    public static void loginSet(Context mContext, String userName, String passWord, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME, userName)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(passWord))
                .targetClass(String.class)
                .execute(listener);
    }

    /**
     * 注册
     * @param mContext
     * @param username
     * @param usernick
     * @param password
     * @param listener
     */

    public static void register(Context mContext, String username, String usernick, String password, OkHttpUtils.OnCompleteListener<Result> listener) {
        OkHttpUtils<Result> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME, username)
                .addParam(I.User.NICK, usernick)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(Result.class)
                .post()
                .execute(listener);
    }

    /**
     * 下载购物车
     * @param mContext
     * @param goodsId
     * @param userName
     * @param count
     * @param isChecked
     * @param listener
     */

    public static void downloadCart(Context mContext, int goodsId, String userName, int count, boolean isChecked
            , OkHttpUtils.OnCompleteListener<CartResultBean> listener) {
        OkHttpUtils<CartResultBean> utils = new OkHttpUtils(mContext);
        utils.setRequestUrl(I.REQUEST_ADD_CART)
                .addParam(I.Cart.GOODS_ID, String.valueOf(goodsId))
                .addParam(I.Cart.USER_NAME, userName)
                .addParam(I.Cart.COUNT, String.valueOf(count))
                .targetClass(CartResultBean.class)
                .addParam(I.Cart.IS_CHECKED, String.valueOf(isChecked))
                .execute(listener);

    }

    /**
     * 更新昵称
     * @param mContext
     * @param userName
     * @param userNick
     * @param listener
     */
    public static void updateUserNick(Context mContext, String userName, String userNick, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME, userName)
                .addParam(I.User.NICK, userNick)
                .targetClass(String.class)
                .execute(listener);

    }

    /**
     * 更新头像
     * @param mContext
     * @param userName
     * @param file
     * @param listener
     */
    public static void updateAvatar(Context mContext, String userName, File file, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID, userName)
                .addParam(I.AVATAR_TYPE, I.AVATAR_TYPE_USER_PATH)
                .addFile2(file)
                .targetClass(String.class)
                .post()
                .execute(listener);
    }

    /**
     * 同步用户信息
     * @param mContext
     * @param userName
     * @param listener
     */
    public static void syncUser(Context mContext, String userName, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_FIND_USER)
                .addParam(I.User.USER_NAME, userName)
                .targetClass(String.class)
                .execute(listener);

    }

    /**
     * 下载收藏商品数量
     * @param mContext
     * @param userName
     * @param listener
     */
    public static void downLoadCollectCount(Context mContext, String userName, OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.Collect.USER_NAME, userName)
                .targetClass(MessageBean.class)
                .execute(listener);

    }

    /**
     * 下载收藏商品详情
     * @param mContext
     * @param userName
     * @param pageId
     * @param listener
     */
    public static void downLoadCollect(Context mContext, String userName, int pageId, OkHttpUtils.OnCompleteListener<CollectBean[]> listener) {
        OkHttpUtils<CollectBean[]> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME, userName)
                .addParam(I.PAGE_ID, String.valueOf(pageId))
                .addParam(I.PAGE_SIZE, String.valueOf(I.PAGE_SIZE_DEFAULT))
                .targetClass(CollectBean[].class)
                .execute(listener);
    }

    /**
     *
     * @param mContext
     * @param userName
     * @param goodsId
     * @param listener
     */
    public static void deleteCollect(Context mContext, String userName, int goodsId, OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_DELETE_COLLECT)
                .addParam(I.Collect.GOODS_ID, goodsId + "")
                .addParam(I.Collect.USER_NAME, userName)
                .targetClass(MessageBean.class)
                .execute(listener);

    }

    public static void downloadIsCollect(Context mContext, String userName, int goodsId, OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_IS_COLLECT)
                .addParam(I.Collect.USER_NAME, userName)
                .addParam(I.Collect.GOODS_ID, String.valueOf(goodsId))
                .targetClass(MessageBean.class)
                .execute(listener);

    }

    /**
     * 增加购物车数量
     * @param mContext
     * @param userName
     * @param goodsId
     * @param listener
     */
    public static void addCollect(Context mContext, String userName, int goodsId, OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_ADD_COLLECT)
                .addParam(I.Collect.GOODS_ID, String.valueOf(goodsId))
                .addParam(I.Collect.USER_NAME, userName)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    public static void isCollected(Context mContext, String userName, int goodsId, OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_ADD_COLLECT)
                .addParam(I.Collect.GOODS_ID, String.valueOf(goodsId))
                .addParam(I.Collect.USER_NAME, userName)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    public static void downloadCarts(Context mContext, String userName, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.Cart.USER_NAME, userName)
                .targetClass(String.class)
                .execute(listener);
    }

    /**
     * 更新购物车
     * @param mContext
     * @param count
     * @param cartId
     * @param listener
     */
    public static void updateCart(Context mContext, int count, int cartId, OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID, String.valueOf(cartId))
                .addParam(I.Cart.COUNT, String.valueOf(count))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    /**
     * 删除购物车
     * @param mContext
     * @param cartId
     * @param listener
     */
    public static void deleteCart(Context mContext, int cartId, OkHttpUtils.OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(mContext);
        utils.setRequestUrl(I.REQUEST_DELETE_CART)
                .addParam(I.Cart.ID, String.valueOf(cartId))
                .targetClass(MessageBean.class)
                .execute(listener);
    }


}
