package com.atguigu.shoppingmall.utils;

import android.util.Log;

public class log {
    /**
     * log标签
     */
    public static String TAG = "TYPE";

    public static void v(String tag,String msg){
        Log.v(tag,msg);
    }

    public static void e(String tag,String msg){
        Log.e(TAG,msg);
    }

    public static void i(String tag,String msg){
        Log.i(tag,msg);
    }
    public static void w(String tag,String msg){
        Log.w(tag,msg);
    }
}
