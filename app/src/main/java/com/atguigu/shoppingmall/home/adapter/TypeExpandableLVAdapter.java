package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.activity.GoodsListActivity;

import java.util.List;

public class TypeExpandableLVAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final List<String> group;
    private final List<List<String>> child;

    public TypeExpandableLVAdapter(Context context, List<String> group, List<List<String>> child){
        this.context = context;
        this.group = group;
        this.child = child;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TypeViewHolder viewHolder;
        if(convertView==null){
            convertView = View.inflate(context, R.layout.group_list_item,null);
            //viewHolder = new TypeViewHolder(convertView);
            //convertView.setTag(viewHolder);
        }else{
            viewHolder = (TypeViewHolder) convertView.getTag();
        }
        //viewHolder.
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder{
        ImageView iv_group;
        TextView tv_group;

        public GroupViewHolder(View view){
            iv_group = view.findViewById(R.id.iv_group);
        }
    }

    class TypeViewHolder{
        ImageView iv_child;
        TextView tv_child;
    }
}
