package com.atguigu.shoppingmall.community.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.shoppingmall.base.BaseFragment;

/**
 * 发现的fragment
 */
public class CommunityFragment extends BaseFragment {
    private TextView textView;
    private static final String TAG = CommunityFragment.class.getSimpleName();

    @Override
    public View initView() {
        Log.e(TAG,"发现视图被初始化了");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG,"发现数据被初始化了");
        textView.setText("hello,world");
    }
}
