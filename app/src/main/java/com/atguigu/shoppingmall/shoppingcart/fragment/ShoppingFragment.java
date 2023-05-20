package com.atguigu.shoppingmall.shoppingcart.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.shoppingcart.adapter.ShopcartAdapter;
import com.atguigu.shoppingmall.shoppingcart.utils.CartStorage;
import com.atguigu.shoppingmall.utils.log;

import java.util.List;

/**
 * 购物中心的fragment
 */
public class ShoppingFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = ShoppingFragment.class.getSimpleName();

    private TextView tv_shopcart_edit;
    private RecyclerView rv_shopcart;
    private LinearLayout ll_check_all;
    private CheckBox checkbox_all;
    private TextView tv_shopcart_total;
    private Button btn_check_out;

    private CheckBox cb_all;
    private Button btn_delete;
    private Button btn_collection;
    private LinearLayout ll_delete;
    private LinearLayout ll_empty_shopcart;

    //购物车的本地商品数据
    private CartStorage cartStorage = CartStorage.getInstance();

    private List<GoodsBean> goodsList;
    private ShopcartAdapter adapter;

    //当前状态，默认为结算状态
    private boolean isEdit = false;


    @Override
    public View initView() {
        /*Log.e(TAG,"购物中心视图被初始化了");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);*/
        View view = View.inflate(mContext, R.layout.fragment_shopping_cart,null);
        findView(view);
        return view;
    }

    private void findView(View v){
        tv_shopcart_edit = v.findViewById(R.id.tv_shopcart_edit);
        rv_shopcart = v.findViewById(R.id.rv_shopcart);
        ll_check_all = v.findViewById(R.id.ll_check_all);
        checkbox_all = v.findViewById(R.id.checkbox_all);
        btn_check_out = v.findViewById(R.id.btn_check_out);
        tv_shopcart_total = v.findViewById(R.id.tv_shopcart_total);
        tv_shopcart_edit = v.findViewById(R.id.tv_shopcart_edit);
        cb_all = v.findViewById(R.id.cb_all);
        btn_delete = v.findViewById(R.id.btn_delete);
        btn_collection = v.findViewById(R.id.btn_collection);
        ll_delete = v.findViewById(R.id.ll_delete);
        ll_empty_shopcart = v.findViewById(R.id.ll_empty_shopcart);
        btn_check_out.setOnClickListener(this);
        tv_shopcart_edit.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_collection.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_check_out:
                goodsList = cartStorage.getAllData();
                Log.e("cart","size1==="+goodsList.size());
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_shopcart_edit:
                if(isEdit) {
                    isEdit = !isEdit;
                    switchToBuy();
                }
                else {
                    isEdit = !isEdit;
                    switchToEdit();
                }
                break;
            case R.id.btn_delete:
                adapter.deleteData();
                checkEmpty();
                break;
            case R.id.btn_collection:
                break;
        }
    }

    /**
     * 显示空页面
     */
    public void checkEmpty(){
        goodsList = cartStorage.getAllData();
        if(goodsList.size()<=0){
            ll_check_all.setVisibility(View.GONE);
            ll_empty_shopcart.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 切换到编辑状态
     */
    private void switchToEdit() {
        tv_shopcart_edit.setText("保存");
        ll_check_all.setVisibility(View.GONE);
        ll_delete.setVisibility(View.VISIBLE);
        //设置全不选
        adapter.setAll_none(false);
    }

    /**
     * 切换到结算状态
     */
    private void switchToBuy() {
        tv_shopcart_edit.setText("编辑");
        ll_check_all.setVisibility(View.VISIBLE);
        ll_delete.setVisibility(View.GONE);
        //设置全选
        adapter.setAll_none(true);
    }


    @Override
    public void onResume() {
        super.onResume();
        showData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG,"购物中心数据被初始化了");
        showData();
    }

    private void showData(){
        ll_check_all.setVisibility(View.VISIBLE);
        ll_empty_shopcart.setVisibility(View.GONE);

        goodsList = cartStorage.getAllData();

        //设置适配器
        adapter = new ShopcartAdapter(goodsList,checkbox_all,tv_shopcart_total,cb_all);
        rv_shopcart.setLayoutManager(new LinearLayoutManager(mContext));
        rv_shopcart.setAdapter(adapter);
        //检查是否为空页面
        checkEmpty();
    }


}
