package com.atguigu.shoppingmall.type.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.type.bean.TypeBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 分类页面常用分类的GridView的适配器
 */
public class OrdinaryGVAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<TypeBean.ResultBean.ChildBean> ordinaryList;

    public OrdinaryGVAdapter(Context mContext, List<TypeBean.ResultBean.ChildBean> recommendList) {
        this.mContext = mContext;
        this.ordinaryList = recommendList;
    }


    @Override
    public int getCount() {
        return ordinaryList.size();
    }

    @Override
    public Object getItem(int position) {
        return ordinaryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecommendViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.item_ordinary_right,null);
            viewHolder = new RecommendViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (RecommendViewHolder) convertView.getTag();
        }
        TypeBean.ResultBean.ChildBean ordinaryBean = ordinaryList.get(position);
        //设置数据
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+ordinaryBean.getPic()).into(viewHolder.iv_ordinary_right);
        viewHolder.tv_ordinary_right.setText(ordinaryBean.getName());
        return convertView;
    }

    static class RecommendViewHolder{
        ImageView iv_ordinary_right;
        TextView tv_ordinary_right;
        public RecommendViewHolder(View itemView){
            iv_ordinary_right = itemView.findViewById(R.id.iv_ordinary_right);
            tv_ordinary_right = itemView.findViewById(R.id.tv_ordinary_right);
        }
    }
}
