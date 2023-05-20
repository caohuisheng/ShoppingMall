package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.bean.ResultBeanData;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

public class HotGridViewAdapter extends BaseAdapter {

    private final Context context;
    private final List<ResultBeanData.ResultBean.HotInfoBean> hot_info;

    public HotGridViewAdapter(Context mContext, List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
        this.context = mContext;
        this.hot_info = hot_info;
    }

    @Override
    public int getCount() {
        return hot_info.size();
    }

    @Override
    public Object getItem(int position) {
        return hot_info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if(convertView == null){
            convertView = View.inflate(context,R.layout.item_hot_grid_view,null);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (MyViewHolder) convertView.getTag();
        }
        //获取数据
        ResultBeanData.ResultBean.HotInfoBean hotInfoBean = hot_info.get(position);
        //设置数据
        Glide.with(context).load(Constants.BASE_URL_IMAGE+hotInfoBean.getFigure()).into(holder.iv_hot);
        holder.tv_name.setText(hotInfoBean.getName());
        holder.tv_price.setText(hotInfoBean.getCover_price());
        return convertView;
    }

    static class MyViewHolder{
        ImageView iv_hot;
        TextView tv_name;
        TextView tv_price;
        public MyViewHolder(View itemView){
            iv_hot = itemView.findViewById(R.id.iv_hot);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);
        }
    }
}
