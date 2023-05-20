package com.atguigu.shoppingmall.type.fragment;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.type.adapter.LeftLVAdapter;
import com.atguigu.shoppingmall.type.adapter.RightRVAdapter;
import com.atguigu.shoppingmall.type.bean.TypeBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 分类的fragment
 */
public class TypeFragment extends BaseFragment {

    private FrameLayout fl_type;
    private SegmentTabLayout segTab;
    private ImageView iv_type_search;
    private Fragment tempFragment;
    private List<Fragment> fragmentList;

    @Override
    public View initView() {
        View view = View.inflate(mContext,R.layout.fragment_type,null);
        fl_type = view.findViewById(R.id.fl_type);
        segTab = view.findViewById(R.id.segTab);
        iv_type_search = view.findViewById(R.id.iv_type_search);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        fragmentList = new ArrayList<>();
        fragmentList.add(new TypeFragmentList(mContext));
        fragmentList.add(new TypeFragmentTag(mContext));
        switchToFragment(tempFragment,fragmentList.get(0));
        initListener();
    }

    /**
     * 初始化监听器
     */
    public void initListener(){
        //设置搜索点击事件
        iv_type_search.setOnClickListener(view -> {
            Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
        });
        String[] titles = {"分类","标签"};
        segTab.setTabData(titles);
        //设置顶部SegmentTabLayout点击事件
        segTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                Fragment nextFragment = fragmentList.get(position);
                switchToFragment(tempFragment,nextFragment);
                Toast.makeText(mContext, titles[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    /**
     * 切换到对应的fragment
     * @param fromFragment
     * @param nextFragment
     */
    private void switchToFragment(Fragment fromFragment, Fragment nextFragment) {
        //如果点击的不是当前fragment
        if(tempFragment != nextFragment){
            tempFragment = nextFragment;
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            //如果下一个fragment还未被添加
            if(!nextFragment.isAdded()){
                //隐藏当前fragment
                if(fromFragment != null){
                    transaction.hide(fromFragment);
                }
                transaction.add(R.id.fl_type,nextFragment,"typeFragment").commit();
            }else{
                //隐藏当前fragment
                if(fromFragment!=null){
                    transaction.hide(fromFragment);
                }
                transaction.show(nextFragment).commit();
            }
        }
    }

}
