package com.atguigu.shoppingmall.user.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.shoppingmall.base.BaseFragment;

/**
 * 个人中心的fragment
 */
public class UserFragment extends BaseFragment {
    private TextView textView;
    private static final String TAG = UserFragment.class.getSimpleName();

    @Override
    public View initView() {
        Log.e(TAG,"个人中心视图被初始化了");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG,"个人中心数据被初始化了");
        textView.setText("个人中心");
    }
}
