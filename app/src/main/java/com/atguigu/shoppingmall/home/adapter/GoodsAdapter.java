package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.bean.GoodsListBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder> {


    private final Context context;
    private final List<GoodsListBean.ResultBean.PageDataBean> data;

    public GoodsAdapter(Context context, List<GoodsListBean.ResultBean.PageDataBean> result) {
        this.context = context;
        this.data = result;
    }

    @NonNull
    @Override
    public GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_goods_list_home,parent,false);
        GoodsViewHolder viewHolder = new GoodsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class GoodsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_hot)
        ImageView iv_hot;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_price)
        TextView tv_price;

        public GoodsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        /**
         * 设置数据
         * @param position
         */
        public void setData(int position){
            GoodsListBean.ResultBean.PageDataBean goodsData = data.get(position);
            Log.e("HOME","imageUrl==="+goodsData.getFigure());
            Glide.with(context).load(Constants.BASE_URL_IMAGE+goodsData.getFigure()).into(iv_hot);
            tv_name.setText(goodsData.getName());
            tv_price.setText("￥"+goodsData.getCover_price());
            //回调监听，触发监听器
            if(onItemClickListener!=null){
                onItemClickListener.onItemClick(position);
            }
        }


    }

    /**
     * 点击某一条的监听器
     */
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
