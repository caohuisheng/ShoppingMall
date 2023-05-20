package com.atguigu.shoppingmall.app;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.community.fragment.CommunityFragment;
import com.atguigu.shoppingmall.home.fragment.HomeFragment;
import com.atguigu.shoppingmall.shoppingcart.fragment.ShoppingFragment;
import com.atguigu.shoppingmall.type.fragment.TypeFragment;
import com.atguigu.shoppingmall.user.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.rg_main)
    RadioGroup rg_main;
    @BindView(R.id.rb_home)
    RadioButton rb_home;
    @BindView(R.id.rb_type)
    RadioButton rb_type;
    @BindView(R.id.rb_cart)
    RadioButton rb_cart;
    @BindView(R.id.rb_community)
    RadioButton rb_community;


    private List<BaseFragment> fragments;
    private int position;
    private BaseFragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ButterKnife和activity绑定
        ButterKnife.bind(this);
        Log.e("TAG","tets"+(rg_main));
        Log.e("TAG","tets"+(frameLayout));
        initFragment();
        initListener();
    }

    /**
     * 添加监听器
     */
    private void initListener() {
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_type:
                        position = 1;
                        break;
                    case R.id.rb_community:
                        position = 2;
                        break;
                    case R.id.rb_cart:
                        position = 3;
                        break;
                    case R.id.rb_user:
                        position = 4;
                        break;
                    default:
                        position = 0;
                        break;
                }
                BaseFragment nextFragment = getFragment(position);
                switchFragment(tempFragment,nextFragment);
            }
        });

        rg_main.check(R.id.rb_home);
    }

    /**
     * 切换fragment
     * @param fromFragment
     * @param nextFragment
     */
    private void switchFragment(BaseFragment fromFragment,BaseFragment nextFragment){
        if(tempFragment != nextFragment){
            tempFragment = nextFragment;
            //判断下一个fragment是否为空
            if(nextFragment != null){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断下一个fragment是否已经添加
                if(!nextFragment.isAdded()){
                    //隐藏当前fragment
                    if(fromFragment != null){
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.frameLayout,nextFragment).commit();
                }else{
                    //隐藏当前fragment
                    if(fromFragment != null){
                        Log.e(TAG,"hide");
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                    Log.e(TAG,"show");
                }
            }
        }
    }

    /**
     * 获取position对应位置的fragment
     * @param position
     * @return
     */
    private BaseFragment getFragment(int position) {
        if(fragments != null && fragments.size()>0){
            return fragments.get(position);
        }
        return null;
    }

    /**
     * 初始化fragment集合
     */
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingFragment());
        fragments.add(new UserFragment());
    }


}