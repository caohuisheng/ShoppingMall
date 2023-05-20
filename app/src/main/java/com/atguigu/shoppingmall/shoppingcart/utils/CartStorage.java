package com.atguigu.shoppingmall.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.atguigu.shoppingmall.app.MyApplication;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;

public class CartStorage {
    private Context context;
    private static CartStorage instance;
    private static final String KEY_CART = "cart";

    //优化后的HashMap
    private SparseArray<GoodsBean> sparseArray;

    private List<GoodsBean> goodsList;

    public CartStorage(Context context){
        this.goodsList = getDataFromLocal();
        this.sparseArray = listToSparse();
        this.context = context;
    }

    /**
     * 获取实例
     * @return
     */
    public static CartStorage getInstance(){
        if(instance == null){
            instance = new CartStorage(MyApplication.getContext());
        }
        return instance;
    }

    public List<GoodsBean> getAllData(){
        return getDataFromLocal();
    }

    /**
     * 从本地获取json数据，并转换为list集合
     * @return
     */
    private List<GoodsBean> getDataFromLocal() {

        List<GoodsBean> listBean = new ArrayList<>();

        String jsonString = CacheUtils.getJson(KEY_CART);
        Log.e("cart","json==="+jsonString);
        if(!TextUtils.isEmpty(jsonString)){
            listBean = new Gson().fromJson(jsonString,new TypeToken<List<GoodsBean>>(){}.getType());
        }

        return listBean;
    }

    /**
     * 保存json数据到本地
     */
    private void commit(){
        List<GoodsBean> goodsList = sparseToList();
        String jsonString = new Gson().toJson(goodsList);
        CacheUtils.saveJson(KEY_CART,jsonString);
    }

    /**
     * 将List转换为SparseArray
     * @return
     */
    private SparseArray<GoodsBean> listToSparse(){
         SparseArray<GoodsBean> sparse = new SparseArray<>();
         for(GoodsBean goods:goodsList){
             int key = Integer.parseInt(goods.getProduct_id());
             sparse.put(key,goods);
         }
         return sparse;
    }

    /**
     * 将SparseArray转换为List
     * @return
     */
    private List<GoodsBean> sparseToList() {
        List<GoodsBean> list = new ArrayList<>();
        if(sparseArray != null && sparseArray.size()>0){
            for(int i=0;i<sparseArray.size();i++){
                GoodsBean goods = sparseArray.valueAt(i);
                list.add(goods);
            }
        }
        return list;
    }

    /**
     * 添加数据
     * @param goodsBean
     */
    public void addData(GoodsBean goodsBean){
        int key = Integer.parseInt(goodsBean.getProduct_id());
        GoodsBean tempData = sparseArray.get(key);
        //判断该产品是否存在
        if(tempData != null){
            tempData.setNumber(tempData.getNumber() + 1);
        }else{
            tempData = goodsBean;
            tempData.setNumber(1);
        }
        sparseArray.put(key,tempData);
        commit();
    }


    /**
     * 删除数据
     * @param goods
     */
    public void deleteData(GoodsBean goods){
        int key = Integer.valueOf(goods.getProduct_id());
        sparseArray.delete(key);
        commit();
    }

    /**
     * 更新数据
     * @param goods
     */
    public void updateData(GoodsBean goods){
        int key = Integer.valueOf(goods.getProduct_id());
        sparseArray.put(key,goods);
        commit();
    }

}
