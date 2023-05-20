package com.atguigu.shoppingmall.type.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;

import java.util.List;

/**
 * 分类Fragment左边的分类ListView的适配器
 */
public class LeftLVAdapter extends BaseAdapter {

    private final Context mContext;
    private final String[] typeList;
    private int currentPos;

    public void setCurrentPos(int position){
        this.currentPos = position;
        notifyDataSetChanged();
    }

    public LeftLVAdapter(Context mContext, String[] typeList) {
        this.mContext = mContext;
        this.typeList = typeList;
    }


    @Override
    public int getCount() {
        return typeList.length;
    }

    @Override
    public Object getItem(int position) {
        return typeList[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = View.inflate(mContext, R.layout.item_type,null);
        }
        TextView tv_type = convertView.findViewById(R.id.tv_type);
        //设置类型
        tv_type.setText(typeList[position]);
        if(currentPos == position){
            tv_type.setTextColor(Color.parseColor("#ff0000"));
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
        }else{
            tv_type.setTextColor(Color.parseColor("#000000"));
            convertView.setBackgroundColor(Color.parseColor("#eeeeee"));
        }
        return convertView;
    }
}
