package com.atguigu.shoppingmall.community.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.community.bean.HotPostBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;
import com.opendanmaku.DanmakuItem;
import com.opendanmaku.DanmakuView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发现页面新帖的ListView的适配器
 */
public class HotpostLVAdapter extends BaseAdapter {

    private final Context context;
    private final List<HotPostBean.ResultBean> data;

    public HotpostLVAdapter(Context mContext, List<HotPostBean.ResultBean> result) {
        this.context = mContext;
        this.data = result;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HotpostViewHolder viewHolder;
        if(convertView==null){
            convertView = View.inflate(context,R.layout.item_hotpost_listview,null);
            viewHolder = new HotpostViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (HotpostViewHolder) convertView.getTag();
        }
        //设置数据
        viewHolder.setData(data.get(position));
        return convertView;
    }

    class HotpostViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_hot_username)
        TextView tv_hot_username;
        @BindView(R.id.tv_hot_addtime)
        TextView tv_hot_addtime;
        @BindView(R.id.iv_hot_figure)
        ImageView iv_hot_figure;
        @BindView(R.id.tv_hot_saying)
        TextView tv_hot_saying;
        @BindView(R.id.tv_hot_likes)
        TextView tv_hot_likes;
        @BindView(R.id.tv_hot_comments)
        TextView tv_hot_comments;
        @BindView(R.id.ib_new_post_avatar)
        ImageView ib_new_post_avatar;
        @BindView(R.id.danmaku)
        DanmakuView danmakuView;

        public HotpostViewHolder(View view){
            Log.e("community","maxHeight"+view.getHeight());
            ButterKnife.bind(this,view);
        }

        /**
         * 设置数据
         * @param resultBean
         */
        public void setData(HotPostBean.ResultBean resultBean){
            tv_hot_username.setText(resultBean.getUsername());
            //设置添加时间
            //设置添加时间
            String msStr = resultBean.getAdd_time();
            int h = Integer.parseInt(msStr)/1000/60/3600;
            tv_hot_addtime.setText(h+"小时前");

            Glide.with(context).load(Constants.BASE_URL_IMAGE+resultBean.getFigure()).into(iv_hot_figure);
            tv_hot_saying.setText(resultBean.getSaying());
            tv_hot_likes.setText(resultBean.getLikes());
            tv_hot_comments.setText(resultBean.getComments());
            setdanma(resultBean.getComment_list());
            ib_new_post_avatar.setOnClickListener(this);
            tv_hot_likes.setOnClickListener(this);
            tv_hot_comments.setOnClickListener(this);
        }

        /**
         * 将日期Date转换为小时
         * @param date
         * @return
         */
        private String getHfromDate(Date date){
            SimpleDateFormat sdf = new SimpleDateFormat("HH");
            return sdf.format(date);
        }

        /**
         * 设置弹幕
         * @param comment_list
         */
        private void setdanma(List<String> comment_list) {
            Log.e("community","comments"+comment_list.toString());
            Log.e("community","height"+danmakuView.getHeight());
            Log.e("community","width"+danmakuView.getWidth());

            for(int i=0;i<comment_list.size();i++){
                String text = comment_list.get(i);
                DanmakuItem danmaku = new DanmakuItem(context,text,danmakuView.getWidth());
                danmakuView.addItem(danmaku);
                danmakuView.show();
            }
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.ib_new_post_avatar:
                    Toast.makeText(context, "avatar", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tv_hot_likes:
                    Toast.makeText(context, "点赞", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tv_hot_comments:
                    Toast.makeText(context, "评论", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
