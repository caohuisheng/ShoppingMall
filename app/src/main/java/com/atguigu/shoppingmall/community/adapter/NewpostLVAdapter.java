package com.atguigu.shoppingmall.community.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.community.bean.NewPostBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;
import com.opendanmaku.DanmakuItem;
import com.opendanmaku.DanmakuView;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewpostLVAdapter extends BaseAdapter {

    private final Context context;
    private final List<NewPostBean.ResultBean> data;

    public NewpostLVAdapter(Context mContext, List<NewPostBean.ResultBean> result) {
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
        NewViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(context,R.layout.item_listview_newpost,null);
            viewHolder = new NewViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (NewViewHolder) convertView.getTag();
        }
        //设置数据
        viewHolder.setData(data.get(position));
        return convertView;
    }

    class NewViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_community_username)
        TextView tv_community_username;
        @BindView(R.id.tv_community_addtime)
        TextView tv_community_addtime;
        @BindView(R.id.iv_community_figure)
        ImageView iv_community_figure;
        @BindView(R.id.tv_community_saying)
        TextView tv_community_saying;
        @BindView(R.id.tv_community_likes)
        TextView tv_community_likes;
        @BindView(R.id.tv_community_comments)
        TextView tv_community_comments;
        @BindView(R.id.ib_new_post_avatar)
        ImageButton ib_new_post_avatar;
        @BindView(R.id.danmaku_newpost)
        DanmakuView danmaku_newpost;

        public NewViewHolder(View view){
            ButterKnife.bind(this,view);
        }

        /**
         * 设置数据
         * @param resultBean
         */
        public void setData(NewPostBean.ResultBean resultBean){
            tv_community_username.setText(resultBean.getUsername());
            //设置添加时间
            String msStr = resultBean.getAdd_time();
            int h = Integer.parseInt(msStr)/1000/60/3600;
            tv_community_addtime.setText(h+"小时前");

            Glide.with(context).load(Constants.BASE_URL_IMAGE+resultBean.getFigure()).into(iv_community_figure);
            tv_community_saying.setText(resultBean.getSaying());
            tv_community_likes.setText(resultBean.getLikes());
            tv_community_comments.setText(resultBean.getComments());
            setDamaku(resultBean.getComment_list());
            tv_community_likes.setOnClickListener(this);
            tv_community_comments.setOnClickListener(this);
            ib_new_post_avatar.setOnClickListener(this);
        }

        private String getHfromDate(Date date){
            SimpleDateFormat sdf = new SimpleDateFormat("HH");
            return sdf.format(date);
        }

        /**
         * 设置弹幕
         * @param comment_list
         */
        private void setDamaku(List<String> comment_list) {
            for(int i=0;i<comment_list.size();i++){
                String text = comment_list.get(i);
                DanmakuItem danmaku = new DanmakuItem(context,text,danmaku_newpost.getWidth());
                danmaku_newpost.addItem(danmaku);
                danmaku_newpost.show();
            }
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.tv_community_likes:
                    Toast.makeText(context, "点赞", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tv_community_comments:
                    Toast.makeText(context, "评论", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.ib_new_post_avatar:
                    Toast.makeText(context, "头像", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
