package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.home.bean.ResultBeanData;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 秒杀适配器
 */
public class SeckillAdapter extends RecyclerView.Adapter<SeckillAdapter.MyViewHolder> {

    private final Context mContext;
    private final List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list;

    public SeckillAdapter(Context context, List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list) {
        this.mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_seckill,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ResultBeanData.ResultBean.SeckillInfoBean.ListBean item = list.get(position);
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_figure;
        private final TextView tv_cover_price;
        private final TextView tv_origin_price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_figure = itemView.findViewById(R.id.iv_figure);
            this.tv_cover_price = itemView.findViewById(R.id.tv_cover_price);
            this.tv_origin_price = itemView.findViewById(R.id.tv_origin_price);
        }

        public void setData(int position) {
            ResultBeanData.ResultBean.SeckillInfoBean.ListBean item = list.get(position);
            //设置数据
            Glide.with(mContext).load(Constants.BASE_URL_IMAGE + item.getFigure()).into(iv_figure);
            tv_cover_price.setText(item.getCover_price());
            tv_origin_price.setText(item.getOrigin_price());

            //设置监听
            itemView.setOnClickListener(view -> {
                Toast.makeText(mContext,"position==="+position,Toast.LENGTH_SHORT).show();
                listener.onItemClick(position);
            });
        }
    }

    public interface MyOnItemClickLitener{
        void onItemClick(int position);
    }

    private MyOnItemClickLitener listener;

    public void setOnItemClickListener(MyOnItemClickLitener listener){
        this.listener = listener;
    }

}
