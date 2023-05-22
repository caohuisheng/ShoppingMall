package com.atguigu.shoppingmall.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.atguigu.shoppingmall.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton ibLoginBack;
    private EditText etLoginPhone;
    private EditText etLoginPwd;
    private ImageButton ibLoginVisible;
    private Button btnLogin;
    private TextView tvLoginRegister;
    private TextView tvLoginForgetPwd;
    private ImageButton ibWeibo;
    private ImageButton ibQq;
    private ImageButton ibWechat;

    //密码是否可见
    private boolean isPwdVisible = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2023-05-22 20:34:13 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        ibLoginBack = (ImageButton)findViewById( R.id.ib_login_back );
        etLoginPhone = (EditText)findViewById( R.id.et_login_phone );
        etLoginPwd = (EditText)findViewById( R.id.et_login_pwd );
        ibLoginVisible = (ImageButton)findViewById( R.id.ib_login_visible );
        btnLogin = (Button)findViewById( R.id.btn_login );
        tvLoginRegister = (TextView)findViewById( R.id.tv_login_register );
        tvLoginForgetPwd = (TextView)findViewById( R.id.tv_login_forget_pwd );
        ibWeibo = (ImageButton)findViewById( R.id.ib_weibo );
        ibQq = (ImageButton)findViewById( R.id.ib_qq );
        ibWechat = (ImageButton)findViewById( R.id.ib_wechat );

        ibLoginBack.setOnClickListener( this );
        ibLoginVisible.setOnClickListener( this );
        btnLogin.setOnClickListener( this );
        ibWeibo.setOnClickListener( this );
        ibQq.setOnClickListener( this );
        ibWechat.setOnClickListener( this );
    }


    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2023-05-22 20:34:13 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == ibLoginBack ) {
            finish();
        } else if ( v == ibLoginVisible ) {
            isPwdVisible = !isPwdVisible;
            if(isPwdVisible){
                //如果密码可见
                ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_visible);
                etLoginPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }else{
                ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_invisible);
                etLoginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        } else if ( v == btnLogin ) {
            String phone = etLoginPhone.getText().toString();
            String password = etLoginPwd.getText().toString();
            if(phone.equals("admin")&&password.equals("123456")){
                Toast.makeText(this,"登陆成功",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this,"密码或账号错误，请重试!",Toast.LENGTH_SHORT).show();
            }
        } else if ( v == ibWeibo ) {
            // Handle clicks for ibWeibo
            Toast.makeText(this,"微博",Toast.LENGTH_SHORT).show();
        } else if ( v == ibQq ) {
            // Handle clicks for ibQq
        } else if ( v == ibWechat ) {
            // Handle clicks for ibWechat
        }
    }





}
