package com.atguigu.shoppingmall.app;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.atguigu.shoppingmall.R;

public class MessageActivity extends AppCompatActivity {
    private ImageButton ib_login_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaage_center);
        ib_login_back = (ImageButton) findViewById(R.id.ib_login_back);
        ib_login_back.setOnClickListener(view -> {
            finish();
        });
    }
}
