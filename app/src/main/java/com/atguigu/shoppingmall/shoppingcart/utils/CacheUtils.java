package com.atguigu.shoppingmall.shoppingcart.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.atguigu.shoppingmall.app.MyApplication;

/**
 * 缓存json数据的工具类
 */
public class CacheUtils {
    private static final String SP_NAME = "atguigu";
    private static SharedPreferences sp = MyApplication.getContext().getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
    /**
     * 获取json数据
     * @param key
     * @return
     */
    public static String getJson(String key){
        return sp.getString(key,null);
    }

    /**
     * 保存json数据
     * @param key
     * @param value
     */
    public static void saveJson(String key,String value){
        sp.edit().putString(key,value).commit();
    }
}
