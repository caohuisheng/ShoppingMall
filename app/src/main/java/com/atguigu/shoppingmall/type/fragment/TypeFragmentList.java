package com.atguigu.shoppingmall.type.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.app.GoodsInfoActivity;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.type.adapter.LeftLVAdapter;
import com.atguigu.shoppingmall.type.adapter.RightRVAdapter;
import com.atguigu.shoppingmall.type.bean.TypeBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TypeFragmentList extends Fragment {
    private TextView textView;
    private static final String TAG = TypeFragment.class.getSimpleName();
    private ListView lv_left;
    private RecyclerView rv_type_right;
    private Context mContext;

    private String[] urls = {Constants.SKIRT_URL,Constants.JACKET_URL,Constants.PANTS_URL,
            Constants.OVERCOAT_URL,Constants.ACCESSORY_URL,Constants.BAG_URL,Constants.DRESS_UP_URL,
            Constants.HOME_PRODUCTS_URL,Constants.STATIONERY_URL,Constants.DIGIT_URL,Constants.GAME_URL};

    private String[] typeList = {"小裙子","上衣","下装","外套","配件","包包","装饰","居家用品"};

    public TypeFragmentList(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_list,null);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        lv_left = (ListView) view.findViewById(R.id.lv_left);
        rv_type_right = view.findViewById(R.id.rv_type_right);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private LeftLVAdapter leftLVAdapter;

    public void initData() {
        Log.e(TAG,"分类数据被初始化了");
        //设置分类左侧菜单列表的监听器
        leftLVAdapter = new LeftLVAdapter(mContext,typeList);
        lv_left.setAdapter(leftLVAdapter);
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDataFromNet(position);//重新获取对应分类的数据
                leftLVAdapter.setCurrentPos(position);//设置当前选中的分类
            }
        });
        //网络请求数据
        getDataFromNet(0);
    }

    /**
     * 网络请求数据
     */
    private void getDataFromNet(int position){
        String url = urls[position];
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //创建请求
        Request request = new Request.Builder()
                .url(url)
                .build();
        //发起异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                Log.e("TYPE","data==="+json);
                processData(json);
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(mContext,"请求数据失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private RightRVAdapter rightRVAdapter;
    private TypeBean.ResultBean resultBean;
    /**
     * 处理数据
     * @param json
     */
    private void processData(String json) {
        //解析json数据成TypeBean对象
        TypeBean typeBean = new Gson().fromJson(json,new TypeToken<TypeBean>(){}.getType());
        //得到分类数据
        resultBean = typeBean.getResult().get(0);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rightRVAdapter = new RightRVAdapter(mContext,resultBean);
                rv_type_right.setAdapter(rightRVAdapter);
                LinearLayoutManager manager = new LinearLayoutManager(mContext);
                manager.setOrientation(RecyclerView.VERTICAL);
                rv_type_right.setLayoutManager(manager);
            }
        });
    }


}
