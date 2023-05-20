package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.app.GoodsInfoActivity;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.home.bean.ResultBeanData;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.transformer.AlphaPageTransformer;
import com.youth.banner.transformer.ScaleInTransformer;

import java.nio.channels.Channel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 主页整体RecyclerView的适配器
 */
public class HomeFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    /**
     * 五种类型
     */
    /**
     * 横幅广告
     */
    public static final int BANNER = 0;
    /**
     * 频道
     */
    public static final int CHANNEL = 1;
    /**
     * 活动
     */
    public static final int ACT = 2;
    /**
     * 秒杀
     */
    public static final int SECKILL = 3;
    /**
     * 推荐
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖
     */
    public static final int HOT = 5;
    private static final String GOODS_BEAN = "goodsBean";

    /**
     * 当前类型
    */
    public int currentType = BANNER;

    /**
     * 数据对象
     */
    private ResultBeanData.ResultBean resultBean;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public HomeFragmentAdapter(Context context, ResultBeanData.ResultBean resultBean){
        this.mContext = context;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 获取类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch(position){
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == BANNER){
            View view = mLayoutInflater.inflate(R.layout.banner_viewpager,null);
            return new BannerViewHolder(view, mContext,resultBean);
        }else if(viewType == CHANNEL){
            View view = mLayoutInflater.inflate(R.layout.channel_item,null);
            return new ChannelViewHolder(mContext,view);
        }else if(viewType == ACT){
            View view = mLayoutInflater.inflate(R.layout.item_act,null);
            return new ActViewHolder(mContext,view);
        }else if(viewType == SECKILL){
            View view = mLayoutInflater.inflate(R.layout.seckill_item,null);
            return new SeckillViewHolder(mContext,view);
        }else if(viewType == RECOMMEND){
            View view = mLayoutInflater.inflate(R.layout.recommend_item,null);
            return new RecommendViewHolder(mContext,view);
        }else if(viewType == HOT){
            View view = mLayoutInflater.inflate(R.layout.hot_item,null);
            return new HotViewHolder(mContext,view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==BANNER){
            BannerViewHolder viewHolder = (BannerViewHolder) holder;
            viewHolder.setBannerData(resultBean.getBanner_info());
        }else if(getItemViewType(position)==CHANNEL){
            ChannelViewHolder viewHolder = (ChannelViewHolder) holder;
            viewHolder.setChannelData(resultBean.getChannel_info());
        }else if(getItemViewType(position)==ACT){
            ActViewHolder viewHolder = (ActViewHolder) holder;
            viewHolder.setData(resultBean.getAct_info());
        }else if(getItemViewType(position)==SECKILL){
            SeckillViewHolder viewHolder = (SeckillViewHolder) holder;
            viewHolder.setData(resultBean.getSeckill_info());
        }else if(getItemViewType(position)==RECOMMEND){
            RecommendViewHolder viewHolder = (RecommendViewHolder) holder;
            viewHolder.setData(resultBean.getRecommend_info());
        }else if(getItemViewType(position)==HOT){
            HotViewHolder viewHolder = (HotViewHolder) holder;
            viewHolder.setData(resultBean.getHot_info());
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    private void startGoodsInfoActivity(GoodsBean goodsBean){
        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
        intent.putExtra(GOODS_BEAN,goodsBean);
        mContext.startActivity(intent);
    }

    /**
     * BannerViewHolder
     */
    class BannerViewHolder extends RecyclerView.ViewHolder{
        public Banner banner;
        public Context mContext;
        public ResultBeanData.ResultBean resultBean;

        public BannerViewHolder(View itemView,Context context,ResultBeanData.ResultBean resultBean){
            super(itemView);
            this.banner = itemView.findViewById(R.id.banner);
            this.mContext = context;
            this.resultBean = resultBean;
        }

        private void setBannerData(List<ResultBeanData.ResultBean.BannerInfoBean> bannerInfo){

            List<String> imageUrls = new ArrayList<>();
            for(int i=0;i<bannerInfo.size();i++){
                imageUrls.add(bannerInfo.get(i).getImage());
            }
            banner.setAdapter(new BannerImageAdapter<String>(imageUrls){
                @Override
                public void onBindView(BannerImageHolder holder, String url, int position, int size) {
                    Glide.with(holder.itemView)
                            .load(Constants.BASE_URL_IMAGE+url)
                            .into(holder.imageView);
                }
            });
            //添加圆点
            banner.setIndicator(new CircleIndicator(mContext));
        }
    }

    /**
     * 频道ChannelViewHolder
     */
    class ChannelViewHolder extends RecyclerView.ViewHolder{
        private Context context;
        private GridView gv_channel;

        public ChannelViewHolder(Context context,View itemView){
            super(itemView);
            this.context = context;
            this.gv_channel = itemView.findViewById(R.id.gv_channel);

            //设置item的点击事件
            gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext,"position:"+position,Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setChannelData(List<ResultBeanData.ResultBean.ChannelInfoBean> channelInfo){
            //设置适配器
            BaseAdapter adapter = new ChannelAdapter(context,channelInfo);
            gv_channel.setAdapter(adapter);
        }
    }



    /**
     * 活动ActViewHolder
     */
    class ActViewHolder extends RecyclerView.ViewHolder{
        private Context mContext;
        private ViewPager vp_act;

        public ActViewHolder(Context context,View itemView) {
            super(itemView);
            this.mContext = context;
            vp_act = itemView.findViewById(R.id.vp_act);
        }

        public void setData(List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
            ViewPager2 vp;

            // 设置每个页面的间距
            vp_act.setPageMargin(20);
            //>=3
            vp_act.setOffscreenPageLimit(3);
            // 设置动画
            //vp_act.setPageTransformer(true, );

            vp_act.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return act_info.size();
                }

                /**
                 *
                 * @param view 页面
                 * @param object 对应instantiateItem的返回值
                 * @return
                 */
                @Override
                public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                    return view == object;
                }

                /**
                 *
                 * @param container viewPager
                 * @param position
                 * @return
                 */
                @NonNull
                @Override
                public Object instantiateItem(@NonNull ViewGroup container, int position) {
                    ImageView imageView = new ImageView(mContext);
                    container.addView(imageView);

                    ResultBeanData.ResultBean.ActInfoBean actInfoBean = act_info.get(position);
                    //设置图片
                    Glide.with(mContext)
                            .load(Constants.BASE_URL_IMAGE+ actInfoBean.getIcon_url())
                            .into(imageView);

                    imageView.setOnClickListener(view -> {
                        Toast.makeText(mContext,"position:"+position,Toast.LENGTH_SHORT).show();

                    });
                    return imageView;
                }

                @Override
                public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                    container.removeView((View) object);
                }
            });
        }
    }

    /**
     * 秒杀SeckillViewHolder
     */
    class SeckillViewHolder extends RecyclerView.ViewHolder{
        private final Context mContext;
        private final TextView tv_time_seckill;
        private final TextView tv_more_seckill;
        private final RecyclerView rv_seckill;

        private long cur_time;

        private Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 0){
                    //倒计时减一秒
                    cur_time-=1000;
                    //设置时间
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                    String time = formatter.format(new Date(cur_time));
                    tv_time_seckill.setText(time);

                    handler.removeMessages(0);
                    handler.sendEmptyMessageDelayed(0,1000);
                    if(cur_time<=0){
                        //移除消息
                        removeCallbacksAndMessages(null);
                    }
                }
            }
        };

        public SeckillViewHolder(Context context, View itemView){
            super(itemView);
            this.mContext = context;
            this.tv_time_seckill = itemView.findViewById(R.id.tv_time_seckill);
            this.tv_more_seckill = itemView.findViewById(R.id.tv_more_seckill);
            this.rv_seckill = itemView.findViewById(R.id.rv_seckill);
        }

        public void setData(ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {
            SeckillAdapter adapter = new SeckillAdapter(mContext,seckill_info.getList());
            //设置方向
            LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            rv_seckill.setLayoutManager(manager);
            //设置适配器
            rv_seckill.setAdapter(adapter);

            //设置监听
            adapter.setOnItemClickListener(position -> {
                //RecommendInfoBean
                ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = seckill_info.getList().get(position);
                //创建GoodsBean
                GoodsBean goodsBean = new GoodsBean();
                goodsBean.setFigure(listBean.getFigure());
                goodsBean.setCover_price(listBean.getCover_price());
                goodsBean.setName(listBean.getName());
                goodsBean.setProduct_id(listBean.getProduct_id());
                startGoodsInfoActivity(goodsBean);
            });

            cur_time = Integer.valueOf(seckill_info.getEnd_time()) - Integer.valueOf(seckill_info.getStart_time());
            //发送倒计时消息
            handler.sendEmptyMessage(0);
        }
    }


    /**
     * 推荐RecommendViewHolder
     */
    class RecommendViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private TextView tv_more_recommend;
        private GridView gv_recommend;


        public RecommendViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            tv_more_recommend = itemView.findViewById(R.id.tv_more_recommend);
            gv_recommend = itemView.findViewById(R.id.gv_recommend);
        }

        public void setData(List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
            //设置适配器
            BaseAdapter adapter = new RecommendGVAdapter(context,recommend_info);
            gv_recommend.setAdapter(adapter);

            //设置监听
            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext,"position==="+position,Toast.LENGTH_SHORT).show();
                    //RecommendInfoBean
                    ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get(position);
                    //创建GoodsBean
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setFigure(recommendInfoBean.getFigure());
                    goodsBean.setCover_price(recommendInfoBean.getCover_price());
                    goodsBean.setName(recommendInfoBean.getName());
                    goodsBean.setProduct_id(recommendInfoBean.getProduct_id());
                    //启动商品详情页面
                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }

    /**
     * 热卖HotViewHolder
     */
    private class HotViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_more_hot;
        private GridView gv_hot;

        public HotViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.tv_more_hot = itemView.findViewById(R.id.tv_more_hot);
            this.gv_hot = itemView.findViewById(R.id.gv_hot);

        }

        public void setData(List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
            BaseAdapter adapter = new HotGridViewAdapter(mContext,hot_info);
            gv_hot.setAdapter(adapter);

            //设置监听
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext,"position=="+position,Toast.LENGTH_SHORT).show();
                    //获取HotInfoBean
                    ResultBeanData.ResultBean.HotInfoBean hotInfoBean = hot_info.get(position);
                    //创建GoodsBean
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setFigure(hotInfoBean.getFigure());
                    goodsBean.setCover_price(hotInfoBean.getCover_price());
                    goodsBean.setName(hotInfoBean.getName());
                    goodsBean.setProduct_id(hotInfoBean.getProduct_id());

                    //启动商品详情页面
                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }
}
