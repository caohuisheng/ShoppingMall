package com.atguigu.shoppingmall.user.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.app.LoginActivity;
import com.atguigu.shoppingmall.app.MessageActivity;
import com.atguigu.shoppingmall.base.BaseFragment;

import org.w3c.dom.Text;

import butterknife.BindView;

/**
 * 个人中心的fragment
 */
public class UserFragment extends BaseFragment implements View.OnClickListener {
    private ScrollView scrollview;
    private ImageButton ibUserIconAvator;
    private TextView tvUsername;
    private TextView tvAllOrder;
    private TextView tvUserPay;
    private TextView tvUserReceive;
    private TextView tvUserFinish;
    private TextView tvUserDrawback;
    private TextView tvUserLocation;
    private TextView tvUserCollect;
    private TextView tvUserCoupon;
    private TextView tvUserScore;
    private TextView tvUserPrize;
    private TextView tvUserTicket;
    private TextView tvUserInvitation;
    private TextView tvUserCallcenter;
    private TextView tvUserFeedback;
    private TextView tvUsercenter;
    private ImageButton ibUserSetting;
    private ImageButton ibUserMessage;

    @Override
    public View initView() {
        //Log.e(TAG,"个人中心视图被初始化了");
        /*textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);*/
        View view = View.inflate(mContext, R.layout.fragment_user,null);
        findViews(view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2023-05-22 20:46:11 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews(View view) {
        scrollview = (ScrollView)view.findViewById( R.id.scrollview );
        ibUserIconAvator = (ImageButton)view.findViewById( R.id.ib_user_icon_avator );
        tvUsername = (TextView)view.findViewById( R.id.tv_username );
        tvAllOrder = (TextView)view.findViewById( R.id.tv_all_order );
        tvUserPay = (TextView)view.findViewById( R.id.tv_user_pay );
        tvUserReceive = (TextView)view.findViewById( R.id.tv_user_receive );
        tvUserFinish = (TextView)view.findViewById( R.id.tv_user_finish );
        tvUserDrawback = (TextView)view.findViewById( R.id.tv_user_drawback );
        tvUserLocation = (TextView)view.findViewById( R.id.tv_user_location );
        tvUserCollect = (TextView)view.findViewById( R.id.tv_user_collect );
        tvUserCoupon = (TextView)view.findViewById( R.id.tv_user_coupon );
        tvUserScore = (TextView)view.findViewById( R.id.tv_user_score );
        tvUserPrize = (TextView)view.findViewById( R.id.tv_user_prize );
        tvUserTicket = (TextView)view.findViewById( R.id.tv_user_ticket );
        tvUserInvitation = (TextView)view.findViewById( R.id.tv_user_invitation );
        tvUserCallcenter = (TextView)view.findViewById( R.id.tv_user_callcenter );
        tvUserFeedback = (TextView)view.findViewById( R.id.tv_user_feedback );
        tvUsercenter = (TextView)view.findViewById( R.id.tv_usercenter );
        ibUserSetting = (ImageButton)view.findViewById( R.id.ib_user_setting );
        ibUserMessage = (ImageButton)view.findViewById( R.id.ib_user_message );

        ibUserIconAvator.setOnClickListener( this );
        ibUserSetting.setOnClickListener( this );
        ibUserMessage.setOnClickListener( this );
        tvAllOrder.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2023-05-22 20:46:11 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ib_user_icon_avator:
                startActivity(new Intent(mContext, LoginActivity.class));
                break;
            case R.id.ib_user_message:
                startActivity(new Intent(mContext, MessageActivity.class));
                break;
            case R.id.ib_user_setting:
                Toast.makeText(mContext, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_all_order:
                Toast.makeText(mContext, "全部订单", Toast.LENGTH_SHORT).show();
        }
    }
}
