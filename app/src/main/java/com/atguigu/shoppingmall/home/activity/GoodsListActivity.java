package com.atguigu.shoppingmall.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.adapter.GoodsAdapter;
import com.atguigu.shoppingmall.home.adapter.TypeExpandableLVAdapter;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.home.bean.GoodsListBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoodsListActivity extends AppCompatActivity implements View.OnClickListener {
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
    @BindView(R.id.dl_left)
    public DrawerLayout dl_left;
    @BindView(R.id.ll_goods_list_price)
    public LinearLayout ll_goods_list_price;
    @BindView(R.id.ll_price_root)
    public LinearLayout ll_price_root;
    @BindView(R.id.ll_theme_root)
    public LinearLayout ll_theme_root;
    @BindView(R.id.ll_type_root)
    public LinearLayout ll_type_root;

    @BindView(R.id.rl_select_price)
    public RelativeLayout rl_select_price;
    @BindView(R.id.rl_select_recommend_theme)
    public RelativeLayout rl_select_recommend_theme;
    @BindView(R.id.rl_select_type)
    public RelativeLayout rl_select_type;
    @BindView(R.id.btn_drawer_layout_cancel)
    public Button btn_drawer_layout_cancel;
    @BindView(R.id.btn_drawer_theme_cancel)
    public Button btn_drawer_theme_cancel;
    @BindView(R.id.btn_drawer_type_cancel)
    public Button btn_drawer_type_cancel;
    @BindView(R.id.expandableListView)
    public ExpandableListView expandableListView;



    /**
     * 商品ListView的适配器
     */
    private GoodsAdapter adapter;

    //private static final int
    private static final int SortByPrice = 0;
    private static final int SortByAll = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        getDataFromNet(position);
        initListener();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ib_goods_list_back:
                finish();
                break;
            case R.id.tv_goods_list_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_goods_list_home:
                break;
            case R.id.tv_goods_list_sort:
                Toast.makeText(this,"综合排序",Toast.LENGTH_SHORT).show();
                changeSortType(SortByAll);
                break;
            case R.id.ll_goods_list_price:
                Toast.makeText(this,"价格",Toast.LENGTH_SHORT).show();
                changeSortType(SortByPrice);
                break;
            case R.id.tv_goods_list_select:
                Toast.makeText(this,"筛选",Toast.LENGTH_SHORT).show();
                tv_goods_list_select.setTextColor(Color.RED);
                dl_left.openDrawer(Gravity.RIGHT);
                break;
            case R.id.rl_select_price:
                Toast.makeText(this,"价格",Toast.LENGTH_SHORT).show();
                ll_price_root.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_select_recommend_theme:
                ll_theme_root.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_select_type:
                ll_type_root.setVisibility(View.VISIBLE);
                initTypeView();
                break;
            case R.id.btn_drawer_layout_cancel:
                ll_price_root.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_drawer_theme_cancel:
                ll_theme_root.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_drawer_type_cancel:
                ll_type_root.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private List<String> group = new ArrayList<>();
    private List<List<String>> child = new ArrayList<>();

    /**
     * 初始化分类
     */
    private void initTypeView() {
        initTypeData();
        expandableListView.setAdapter(new TypeExpandableLVAdapter(this,group,child));
    }

    private void initTypeData() {
        addInfo("全部", new String[]{});
        addInfo("上衣", new String[]{"古风", "和风", "lolita", "日常"});
        addInfo("下装", new String[]{"日常", "泳衣", "汉风", "lolita", "创意T恤"});
        addInfo("外套", new String[]{"汉风", "古风", "lolita", "胖次", "南瓜裤", "日常"});
    }

    private void addInfo(String g,String[] c){
        group.add(g);
        List<String> newChild = new ArrayList<>();
        for(int i=0;i<c.length;i++){
            newChild.add(c[i]);
        }
        child.add(newChild);
    }


    private int selectStatue = 0;



    //价格倒序
    private boolean isPriceDesc = true;

    /**
     * 切换选择的状态
     */
    public void changeSortType(int type){
        switch(type){
            case SortByPrice:
                tv_goods_list_price.setTextColor(Color.RED);
                tv_goods_list_sort.setTextColor(getResources().getColor(R.color.graytext));
                if(isPriceDesc){
                    iv_goods_list_arrow.setBackgroundResource(R.drawable.new_price_sort_desc);
                }else{
                    iv_goods_list_arrow.setBackgroundResource(R.drawable.new_price_sort_asc);
                }
                isPriceDesc = !isPriceDesc;
                break;
            case SortByAll:
                tv_goods_list_sort.setTextColor(Color.RED);
                tv_goods_list_price.setTextColor(getResources().getColor(R.color.graytext));
                iv_goods_list_arrow.setImageResource(R.drawable.new_price_sort_normal);
                break;
        }
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        ib_goods_list_back.setOnClickListener(this);
        tv_goods_list_sort.setOnClickListener(this);
        tv_goods_list_sort.setOnClickListener(this);
        tv_goods_list_sort.setOnClickListener(this);
        tv_goods_list_sort.setOnClickListener(this);
        ll_goods_list_price.setOnClickListener(this);
        tv_goods_list_select.setOnClickListener(this);
        rl_select_price.setOnClickListener(this);
        rl_select_recommend_theme.setOnClickListener(this);
        rl_select_type.setOnClickListener(this);
        ll_price_root.setOnClickListener(this);
        ll_theme_root.setOnClickListener(this);
        ll_type_root.setOnClickListener(this);
        btn_drawer_theme_cancel.setOnClickListener(this);
        btn_drawer_type_cancel.setOnClickListener(this);
        btn_drawer_layout_cancel.setOnClickListener(this);
    }



    /**
     * 网络请求数据
     * @param position
     */
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
