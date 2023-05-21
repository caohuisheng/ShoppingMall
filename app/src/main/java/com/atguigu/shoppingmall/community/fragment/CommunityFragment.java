package com.atguigu.shoppingmall.community.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.community.adapter.VPAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现的fragment
 */
public class CommunityFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = CommunityFragment.class.getSimpleName();
    private ImageButton ib_community_icon;
    private TabLayout tab_layout;
    private ImageButton ib_community_message;
    private ViewPager vp_community;

    private List<Fragment> fragmentList;

    @Override
    public View initView() {
        Log.e(TAG,"发现视图被初始化了");
        /*textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);*/
        View view = View.inflate(mContext, R.layout.fragment_community,null);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        ib_community_icon = view.findViewById(R.id.ib_community_icon);
        tab_layout = view.findViewById(R.id.tab_layout);
        ib_community_message = view.findViewById(R.id.ib_community_message);
        vp_community = view.findViewById(R.id.vp_community);
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG,"发现数据被初始化了");
        //创建fragment数组
        fragmentList = new ArrayList<>();
        fragmentList.add(new HotpostFragment());
        fragmentList.add(new NewpostFragment());
        //设置适配器
        VPAdapter adapter = new VPAdapter(getFragmentManager(),fragmentList);
        vp_community.setAdapter(adapter);

        //将TabLayout与ViewPager绑定
        tab_layout.setupWithViewPager(vp_community);

        initListener();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        ib_community_icon.setOnClickListener(this);
        ib_community_message.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ib_community_icon:
                Toast.makeText(mContext,"icon",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_community_message:
                Toast.makeText(mContext,"message",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
