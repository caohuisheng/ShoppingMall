package com.atguigu.shoppingmall.type.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.type.adapter.TagGridViewAdapter;
import com.atguigu.shoppingmall.type.bean.TagBean;
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

public class TypeFragmentTag extends Fragment {
    private Context mContext;
    private GridView gv_type_tag;

    public TypeFragmentTag(Context mContext){
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(mContext, R.layout.fragment_type_tag,null);
        gv_type_tag = view.findViewById(R.id.gv_type_tag);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDataFromNet();

    }

    /**
     * 网络请求数据
     */
    private void getDataFromNet(){
        String url = Constants.TAG_URL;
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
                Log.e("TYPE","tag==="+json);
                processData(json);
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(mContext,"请求数据失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 处理数据
     * @param json
     */
    private void processData(String json) {
        //将json数据转换为TagBean对象
        TagBean tagBean = new Gson().fromJson(json,new TypeToken<TagBean>(){}.getType());
        List<TagBean.ResultBean> resultBean = tagBean.getResult();
        //在主线程显示数据到视图上
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gv_type_tag.setAdapter(new TagGridViewAdapter(mContext,resultBean));
            }
        });
        //设置监听器
        gv_type_tag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, resultBean.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
