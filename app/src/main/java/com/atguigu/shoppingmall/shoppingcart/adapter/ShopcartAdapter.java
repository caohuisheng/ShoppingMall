package com.atguigu.shoppingmall.shoppingcart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.app.MyApplication;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.shoppingcart.utils.CartStorage;
import com.atguigu.shoppingmall.shoppingcart.view.AddSubView;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.Iterator;
import java.util.List;

/**
 * 购物车RecyclerView的适配器
 */
public class ShopcartAdapter extends RecyclerView.Adapter<ShopcartAdapter.ShopcartViewHolder> {

    private final CheckBox checkbox_all;
    private final TextView tv_shopcart_total;
    private final CheckBox cb_all;
    private Context context = MyApplication.getContext();
    private List<GoodsBean> goodsList;
    private CartStorage cartStorage = CartStorage.getInstance();

    public ShopcartAdapter(List<GoodsBean> goodsList, CheckBox checkbox_all, TextView tv_shopcart_total, CheckBox cb_all){
        this.goodsList = goodsList;
        this.checkbox_all = checkbox_all;
        this.tv_shopcart_total = tv_shopcart_total;
        this.cb_all = cb_all;

        showTotalPrice();
        setListener();
        //检查是否都选中
        checkAll();

    }

    /**
     * 检查是否全部选中
     */
    public void checkAll(){
        for(GoodsBean goods:goodsList){
            if(!goods.isChecked()){
                checkbox_all.setChecked(false);
                cb_all.setChecked(false);
                return;
            }
            checkbox_all.setChecked(true);
            cb_all.setChecked(true);
        }
    }

    /**
     * 设置全选或全部选
     */
    public void setAll_none(boolean checked){
        for(int i=0;i<goodsList.size();i++){
            GoodsBean goods = goodsList.get(i);
            goods.setChecked(checked);
            //更新某一条数据视图
            notifyItemChanged(i);
        }
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        //设置每一条数据的监听
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                GoodsBean goods = goodsList.get(position);
                //设置取反状态
                goods.setChecked(!goods.isChecked());
                //刷新状态
                notifyItemChanged(position);
                //重新计算总价格
                showTotalPrice();
            }
        });

        //设置结算全选的监听
        checkbox_all.setOnClickListener(view -> {
            boolean checked = checkbox_all.isChecked();
            setAll_none(checked);
            showTotalPrice();
        });

        //设置编辑全选的监听
        cb_all.setOnClickListener(view -> {
            boolean checked = cb_all.isChecked();
            setAll_none(checked);
        });
    }

    /**
     * 删除选中的数据
     */
    public void deleteData() {
        for(int i=0;i<goodsList.size();i++){
            GoodsBean goods = goodsList.get(i);
            if(goods.isChecked()){
                goodsList.remove(goods);
                //从本地删除
                cartStorage.deleteData(goods);
                //刷新
                notifyItemRemoved(i);
                i--;//由于删除了一条数据，索引需要-1
            }
        }
    }


    /**
     * 某一条的点击监听器
     */
    interface OnItemClickListener{
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    /**
     * 设置某一条的点击监听器
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private void showTotalPrice(){
        tv_shopcart_total.setText(String.valueOf(getTotalPrice()));
    }

    /**
     * 计算总价
     * @return
     */
    private float getTotalPrice(){
        float totalPrice = 0;
        for(GoodsBean goods:goodsList){
            //判断是否选中
            if(goods.isChecked()){
                totalPrice += Float.parseFloat(goods.getCover_price())*goods.getNumber();
            }
        }
        return totalPrice;
    }

    @NonNull
    @Override
    public ShopcartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop_cart,parent,false);
        ShopcartViewHolder viewHolder = new ShopcartViewHolder(context,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopcartViewHolder holder, int position) {
        GoodsBean goodsBean = goodsList.get(position);
        holder.setData(goodsBean);
        //设置监听器
        holder.addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                //1.重新显示总价
                showTotalPrice();
                //2.更新list数据
                goodsBean.setNumber(value);
                //3.更新本地数据
                //cartStorage.
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("cart","list2==="+goodsList.toString());
        Log.e("cart","adapter  size==="+goodsList.size());
        return goodsList.size();
    }

    public class ShopcartViewHolder extends RecyclerView.ViewHolder{
        private CheckBox cb_cart;
        private ImageView iv_cart;
        private TextView tv_cart_desc;
        private TextView tv_cart_price;
        private AddSubView addSubView;
        private Context context;

        public ShopcartViewHolder(Context context,@NonNull View itemView) {
            super(itemView);
            cb_cart = itemView.findViewById(R.id.cb_cart);
            iv_cart = itemView.findViewById(R.id.iv_cart);
            tv_cart_desc = itemView.findViewById(R.id.tv_cart_desc);
            tv_cart_price = itemView.findViewById(R.id.tv_cart_price);
            addSubView = itemView.findViewById(R.id.AddSubView);
            this.context = context;
            itemView.setOnClickListener(view -> {
                 if(listener != null){
                     listener.onItemClick(getLayoutPosition());
                     checkAll();
                 }
            });
        }

        /**
         * 设置数据
         * @param goodsBean
         */
        public void setData(GoodsBean goodsBean){
            cb_cart.setChecked(goodsBean.isChecked());
            Glide.with(context).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(iv_cart);
            tv_cart_desc.setText(goodsBean.getName());
            tv_cart_price.setText(goodsBean.getCover_price());
            addSubView.setValue(goodsBean.getNumber());

        }
    }
}
