package com.atguigu.shoppingmall.type.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.type.bean.TypeBean;
import com.atguigu.shoppingmall.utils.myUtils;

import java.util.List;

/**
 * 分类fragment的分类页面右侧ListView的适配器
 */
public class RightRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int HOT = 0;
    private static final int ORDINARY = 1;
    private int currentType;

    private final Context mContext;
    private final TypeBean.ResultBean resultBean;

    private LayoutInflater layoutInflater;

    public RightRVAdapter(Context mContext, TypeBean.ResultBean resultBean){
        this.mContext = mContext;
        this.resultBean = resultBean;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        switch(position){
            case HOT:
                currentType = HOT;
                break;
            case ORDINARY:
                currentType = ORDINARY;
                break;
        }
        return currentType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==HOT){
            View view = layoutInflater.inflate(R.layout.item_hot_right,parent,false);
            return new HotViewHolder(view);
        }else if(viewType == ORDINARY){
            View view  = layoutInflater.inflate(R.layout.type_recommend_item,parent,false);
            return new OrdinaryViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==HOT){
            HotViewHolder viewHolder = (HotViewHolder) holder;
            viewHolder.setData(resultBean);
        }else if(getItemViewType(position) == ORDINARY){
            OrdinaryViewHolder viewHolder = (OrdinaryViewHolder) holder;
            viewHolder.setData(resultBean);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    /**
     * 常用分类的ViewHolder
     */
    class OrdinaryViewHolder extends RecyclerView.ViewHolder{
        private GridView gv_type_recommend;

        //private
        public OrdinaryViewHolder(@NonNull View itemView) {
            super(itemView);
            gv_type_recommend = itemView.findViewById(R.id.gv_type_recommend);
        }
        public void setData(TypeBean.ResultBean resultBean){
            List<TypeBean.ResultBean.ChildBean> recommendList = resultBean.getChild();
            //设置适配器
            gv_type_recommend.setAdapter(new OrdinaryGVAdapter(mContext,recommendList));
            //设置店家每一项的监听器
            gv_type_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext,"position==="+position,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * 热卖推荐的ViewHolder
     */
    class HotViewHolder extends RecyclerView.ViewHolder {
        private TypeBean.ResultBean resultBean;
        private RecyclerView rv_type_hot;
        private HotAdapter adapter;
        public HotViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_type_hot = itemView.findViewById(R.id.rv_type_hot);
        }

        public void setData(TypeBean.ResultBean resultBean){
            List<TypeBean.ResultBean.HotProductListBean> list = resultBean.getHot_product_list();
            //设置适配器
            adapter = new HotAdapter(mContext,list);
            rv_type_hot.setAdapter(adapter);
            //设置布局管理器
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            manager.setOrientation(RecyclerView.HORIZONTAL);
            rv_type_hot.setLayoutManager(manager);

            //设置点击的每一项的监听器
            adapter.setOnItemClickListener(position -> {
                Toast.makeText(mContext,"position==="+position,Toast.LENGTH_SHORT).show();
                TypeBean.ResultBean.HotProductListBean hotProduct = list.get(position);
                GoodsBean goodsBean = new GoodsBean();
                goodsBean.setName(hotProduct.getName());
                goodsBean.setCover_price(hotProduct.getCover_price());
                goodsBean.setFigure(hotProduct.getFigure());
                //启动商品详情页面
                myUtils.startGoodsInfoActivity(mContext,goodsBean);
            });
        }
    }
}
