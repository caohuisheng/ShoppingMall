package com.atguigu.shoppingmall.utils;

public class Constants {




    //public static String BASE_URL = "http://10.135.254.249:8080/atguigu";
    public static String BASE_URL = "http://192.168.223.2:8080/atguigu";
    //请求json数据的基本URL
    public static String BASE_URL_JSON = BASE_URL+"/json/";

    //请求图片数据的基本URL
    public static String  BASE_URL_IMAGE = BASE_URL + "/img";

    //主页面fragment路径
    public static String HOME_URL = BASE_URL + "/json/HOME_URL.json";

    //分类Fragment里的标签Fragment数据
    public static final String TAG_URL = BASE_URL_JSON + "TAG_URL.json";

    //商品详情路径
    public static String GOODS_INFO_URL = BASE_URL + "/json/GOODSINFO_URL.json";

    //小裙子
    public static final String SKIRT_URL = BASE_URL_JSON + "SKIRT_URL.json";
    //上衣
    public static final String JACKET_URL = BASE_URL_JSON + "JACKET_URL.json";
    //下装(裤子)
    public static final String PANTS_URL = BASE_URL_JSON + "PANTS_URL.json";
    //外套
    public static final String OVERCOAT_URL = BASE_URL_JSON + "OVERCOAT_URL.json";
    //配件
    public static final String ACCESSORY_URL = BASE_URL_JSON + "ACCESSORY_URL.json";
    //包包
    public static final String BAG_URL = BASE_URL_JSON + "BAG_URL.json";
    //装扮
    public static final String DRESS_UP_URL = BASE_URL_JSON + "DRESS_UP_URL.json";
    //居家宅品
    public static final String HOME_PRODUCTS_URL = BASE_URL_JSON + "HOME_PRODUCTS_URL.json";
    //办公文具
    public static final String STATIONERY_URL = BASE_URL_JSON + "STATIONERY_URL.json";
    //数码周边
    public static final String DIGIT_URL = BASE_URL_JSON +  "DIGIT_URL.json";
    //游戏专区
    public static final String GAME_URL = BASE_URL_JSON + "GAME_URL.json";

}
