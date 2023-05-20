package com.atguigu.shoppingmall.shoppingcart.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.atguigu.shoppingmall.R;

public class AddSubView extends FrameLayout implements View.OnClickListener {

    private ImageView btn_add;
    private ImageView btn_sub;
    private TextView tv_value;

    private int maxValue = 5;
    private int minValue = 1;
    private int value = 1;

    public AddSubView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.add_sub_layout,this);
        btn_add = findViewById(R.id.btn_add);
        btn_sub = findViewById(R.id.btn_sub);
        tv_value = findViewById(R.id.tv_count);

        btn_add.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_add:
                addNum();
                break;
            case R.id.btn_sub:
                subNum();
                break;
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        tv_value.setText(String.valueOf(value));
    }

    /**
     * 增加
     */
    private void addNum(){
        if(value<maxValue){
            value++;
            setValue(value);
            //调用监听器方法
            if(numberChangeListener != null){
                numberChangeListener.onNumberChange(value);
            }
        }
    }

    /**
     * 减少
     */
    private void subNum(){
        if(value>minValue){
            value--;
            setValue(value);
            //调用监听器方法
            if(numberChangeListener != null){
                numberChangeListener.onNumberChange(value);
            }
        }
    }

    /**
     * 数值改变监听器
     */
    public interface OnNumberChangeListener{
        void onNumberChange(int value);
    }

    private OnNumberChangeListener numberChangeListener;

    public void setOnNumberChangeListener(OnNumberChangeListener listener){
        this.numberChangeListener = listener;
    }
}
