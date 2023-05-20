package com.atguigu.shoppingmall.type.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.type.bean.TagBean;

import java.util.List;

public class TagGridViewAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<TagBean.ResultBean> resultBean;

    //颜色数组
    private int[] colors = {Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"),
            Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"), Color.parseColor("#f0a420"),
            Color.parseColor("#f0839a"), Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2")
    };

    public TagGridViewAdapter(Context mContext, List<TagBean.ResultBean> resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
    }


    @Override
    public int getCount() {
        return resultBean.size();
    }

    @Override
    public Object getItem(int position) {
        return resultBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TagViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(mContext,R.layout.item_type_tag,null);
            viewHolder = new TagViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (TagViewHolder) convertView.getTag();
        }
        TagBean.ResultBean resultBean = this.resultBean.get(position);
        //设置数据
        viewHolder.tv_type_tag.setText(resultBean.getName());
        viewHolder.tv_type_tag.setTextColor(colors[position%colors.length]);
        return convertView;
    }

    static class TagViewHolder{
        TextView tv_type_tag;
        public TagViewHolder(View itemView){
            tv_type_tag = itemView.findViewById(R.id.tv_type_tag);
            //itemView.setOnClickListener();
        }
    }
}
