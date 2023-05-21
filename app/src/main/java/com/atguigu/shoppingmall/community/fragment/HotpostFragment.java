package com.atguigu.shoppingmall.community.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.community.adapter.HotpostLVAdapter;
import com.atguigu.shoppingmall.community.bean.HotPostBean;
import com.atguigu.shoppingmall.type.adapter.LeftLVAdapter;
import com.atguigu.shoppingmall.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 */
public class HotpostFragment extends BaseFragment {

    private ListView lv_hot_post;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_hot_post,null);
        lv_hot_post = view.findViewById(R.id.lv_hot_post);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    /**
     * 网络请求数据
     */
    private void getDataFromNet() {
        String url = Constants.HOT_POST_URL;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        //发起异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(mContext,"请求数据失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                processData(json);
                Log.e("community",json);
            }
        });
    }

    /**
     * 处理数据
     * @param json
     */
    private void processData(String json) {
        HotPostBean hotPostBean = new Gson().fromJson(json,new TypeToken<HotPostBean>(){}.getType());
        List<HotPostBean.ResultBean> result = hotPostBean.getResult();
        //在主线程更新页面
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lv_hot_post.setAdapter(new HotpostLVAdapter(mContext,result));
            }
        });
    }
}
