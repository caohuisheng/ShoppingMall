package com.atguigu.shoppingmall.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.adapter.GoodsAdapter;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.home.bean.GoodsListBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoodsListActivity extends AppCompatActivity {
    private static final String TAG = "GoodsList";
    private String[] urls = new String[]{
            Constants.CLOSE_STORE,
            Constants.GAME_STORE,
            Constants.COMIC_STORE,
            Constants.COSPLAY_STORE,
            Constants.GUFENG_STORE,
            Constants.STICK_STORE,
            Constants.WENJU_STORE,
            Constants.FOOD_STORE,
            Constants.SHOUSHI_STORE,
    };

    @BindView(R.id.ib_goods_list_back)
    public ImageButton ib_goods_list_back;
    @BindView(R.id.tv_goods_list_search)
    public TextView tv_goods_list_search;
    @BindView(R.id.ib_goods_list_home)
    public ImageButton ib_goods_list_home;
    @BindView(R.id.tv_goods_list_sort)
    public TextView tv_goods_list_sort;
    @BindView(R.id.tv_goods_list_price)
    public TextView tv_goods_list_price;
    @BindView(R.id.iv_goods_list_arrow)
    public ImageView iv_goods_list_arrow;
    @BindView(R.id.tv_goods_list_select)
    public TextView tv_goods_list_select;
    @BindView(R.id.recyclerview)
    public RecyclerView rv_goods;

    private GoodsAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        getDataFromNet(position);
    }

    public void getDataFromNet(int position){
        String url = urls[position];
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(GoodsListActivity.this, "网络请求数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                processData(json);
                Log.e(TAG,"GoodsListData==="+json);
            }
        });
    }

    /**
     * 梳理数据
     * @param json
     */
    private void processData(String json) {
        GoodsListBean goodsListBean = new Gson().fromJson(json, new TypeToken<GoodsListBean>() {}.getType());
        List<GoodsListBean.ResultBean.PageDataBean> result = goodsListBean.getResult().getPage_data();
        //在主线程加载数据到布局
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new GoodsAdapter(GoodsListActivity.this,result);
                rv_goods.setAdapter(adapter);
                //设置点击某一条的监听
                adapter.setOnItemClickListener(position -> {
                    Toast.makeText(GoodsListActivity.this,"position==="+position,Toast.LENGTH_SHORT).show();
                });
                //设置LayoutManager
                StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                rv_goods.setLayoutManager(manager);
            }
        });

    }

}
