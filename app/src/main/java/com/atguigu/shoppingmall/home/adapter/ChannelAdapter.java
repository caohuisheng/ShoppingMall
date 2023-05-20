package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.bean.ResultBeanData;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 频道GridView的适配器
 */
public class ChannelAdapter extends BaseAdapter {
    private Context mContext;
    private List<ResultBeanData.ResultBean.ChannelInfoBean> channelInfo;
    public ChannelAdapter(Context context, List<ResultBeanData.ResultBean.ChannelInfoBean> channelInfo){
        this.mContext = context;
        this.channelInfo = channelInfo;
    }

    @Override
    public int getCount() {
        Log.e("Home","size==="+channelInfo.size());
        return channelInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return channelInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = View.inflate(mContext, R.layout.item_channel,null);
        }
        ImageView iv_channel = convertView.findViewById(R.id.iv_channel);
        TextView tv_channel = convertView.findViewById(R.id.tv_channel);

        //设置数据
        ResultBeanData.ResultBean.ChannelInfoBean info = channelInfo.get(position);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+info.getImage()).into(iv_channel);
        tv_channel.setText(info.getChannel_name());

        return convertView;
    }
}
