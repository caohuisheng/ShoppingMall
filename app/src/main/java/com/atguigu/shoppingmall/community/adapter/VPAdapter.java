package com.atguigu.shoppingmall.community.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 社区页面的Viewpager的适配器
 */
public class VPAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private String[] titles = {"热帖","新帖"};

    public VPAdapter(FragmentManager manager,List<Fragment> fragmentList){
        super(manager);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    /**
     * 得到对应fragment的标题
     * @param position
     * @return
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
