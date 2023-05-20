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

public class RecommendGVAdapter extends BaseAdapter {

    private final Context context;
    private final List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info;

    public RecommendGVAdapter(Context context, List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
        this.context = context;
        this.recommend_info = recommend_info;
    }

    @Override
    public int getCount() {
        return recommend_info.size();
    }

    @Override
    public Object getItem(int position) {
        return recommend_info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.item_recommend_grid_view,null);
            viewHolder = new MyViewHolder(convertView);
        }else{
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        //得到数据
        ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get(position);
        //设置数据
        Glide.with(context).load(Constants.BASE_URL_IMAGE+recommendInfoBean.getFigure()).into(viewHolder.iv_recommend);
        viewHolder.tv_name.setText(recommendInfoBean.getName());
        viewHolder.tv_price.setText(recommendInfoBean.getCover_price());

        //保存viewHolder
        convertView.setTag(viewHolder);
        return convertView;
    }

    static class MyViewHolder{
        ImageView iv_recommend;
        TextView tv_name;
        TextView tv_price;

        public MyViewHolder(View itemView){
            iv_recommend = itemView.findViewById(R.id.iv_recommend);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);
        }
    }
}
