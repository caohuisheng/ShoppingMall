package com.atguigu.shoppingmall.utils;

import android.content.Context;
import android.content.Intent;

import com.atguigu.shoppingmall.app.GoodsInfoActivity;
import com.atguigu.shoppingmall.home.bean.GoodsBean;

public class myUtils {
    private static final String GOODS_BEAN = "goodsBean";
    /**
     * 启动商品详情页面
     * @param goodsBean
     */
    public static void startGoodsInfoActivity(Context mContext,GoodsBean goodsBean){
        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
        intent.putExtra(GOODS_BEAN,goodsBean);
        mContext.startActivity(intent);
    }
}
