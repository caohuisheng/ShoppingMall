package com.atguigu.shoppingmall.type.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.type.bean.TypeBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

/**
 * 分类页面右边的热卖推荐RecyclerView的适配器
 */
public class HotAdapter extends RecyclerView.Adapter {

    private List<TypeBean.ResultBean.HotProductListBean> hotList;
    private Context mContext;


    public HotAdapter(Context mContext,List<TypeBean.ResultBean.HotProductListBean> list) {
        this.mContext = mContext;
        this.hotList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_type_hot,parent,false);
        RecyclerView.ViewHolder viewHolder = new HotViewHolder(mContext,view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HotViewHolder viewHolder = (HotViewHolder) holder;
        viewHolder.setData(hotList.get(position));
        //设置监听器
        viewHolder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return hotList.size();
    }

    static class HotViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private ImageView iv_type_hot;
        private TextView tv_price;

        public HotViewHolder(Context mContext, @NonNull View itemView) {
            super(itemView);
            iv_type_hot = (ImageView) itemView.findViewById(R.id.iv_type_hot);
            tv_price = (TextView) itemView.findViewById(R.id.tv_hot_price);
            this.mContext = mContext;
        }

        public void setData(TypeBean.ResultBean.HotProductListBean hotBean){
            Glide.with(mContext).load(Constants.BASE_URL_IMAGE+hotBean.getFigure()).into(iv_type_hot);
            //Log.e("TYPE","hotBean==="+hotBean.getCover_price());
            tv_price.setText(hotBean.getCover_price());

        }
    }

    /**
     * 点击每一项的监听器
     */
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    /**
     * 设置监听器的方法
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
