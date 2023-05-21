package com.atguigu.shoppingmall.home.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.app.MessageActivity;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.home.adapter.HomeFragmentAdapter;
import com.atguigu.shoppingmall.home.bean.ResultBeanData;
import com.atguigu.shoppingmall.utils.Constants;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 主页的fragment
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private TextView tv_search_home;
    private TextView tv_message_home;
    private RecyclerView rv_home;
    private ImageButton ib_top;
    private ResultBeanData.ResultBean resultBean;

    private HomeFragmentAdapter adapter;

    @Override
    public View initView() {
        Log.e(TAG,"主页视图被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home,null);
        tv_search_home = view.findViewById(R.id.tv_search_home);
        tv_message_home = view.findViewById(R.id.tv_message_home);
        rv_home = view.findViewById(R.id.rv_home);
        ib_top = view.findViewById(R.id.ib_top);
        initListener();
        return view;
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        tv_search_home.setOnClickListener(view -> {
            Toast.makeText(mContext,"搜索",Toast.LENGTH_SHORT).show();
        });
        tv_message_home.setOnClickListener(view -> {
            Toast.makeText(mContext,"消息",Toast.LENGTH_SHORT).show();
        });
        ib_top.setOnClickListener(view -> {
            Toast.makeText(mContext,"按钮",Toast.LENGTH_SHORT).show();
            //回到顶部
            rv_home.scrollToPosition(0);
        });
        tv_message_home.setOnClickListener(view -> {
            startActivity(new Intent(mContext, MessageActivity.class));
        });
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
        Log.e(TAG,"主页数据被初始化了");
    }

    /**
     * 网络请求
     */
    public void getDataFromNet(){
        String url = Constants.HOME_URL;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                processData(response.body().string());
                Log.e("TAG","currentThread==="+Thread.currentThread().getName());
                //Log.e(TAG,"请求成功==="+response.body().string());
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG,"请求失败");
                e.printStackTrace();
            }
        });
    }

    /**
     * 处理数据
     * @param json
     */
    public void processData(String json){
        ResultBeanData resultBeanData = JSON.parseObject(json,ResultBeanData.class);
        resultBean = resultBeanData.getResult();
        Log.e(TAG,"解析成功==="+resultBean.getHot_info().get(0).getName());
        if(resultBean != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //设置适配器
                    adapter = new HomeFragmentAdapter(mContext,resultBean);
                    //设置布局管理者
                    GridLayoutManager manager = new GridLayoutManager(mContext,1);
                    manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            if(position<=3){
                                ib_top.setVisibility(View.INVISIBLE);
                            }else{
                                ib_top.setVisibility(View.VISIBLE);
                            }
                            //只能返回1
                            return 1;
                        }
                    });
                    rv_home.setLayoutManager(manager);

                    rv_home.setAdapter(adapter);

                }
            });

        }
    }
}
