package com.ex.administrator.zhanhui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ex.administrator.zhanhui.R;


/**
 * Created by Administrator on 2017/3/24.
 */

public class NumberPicker extends LinearLayout {
    private TextView tvShow;
    private RelativeLayout plus;
    private RelativeLayout minus;
    private int defaultValue = 1;

    public InterfaceOnNumChange listener;
    private static  NumberPicker sInstance;
    public interface InterfaceOnNumChange {
        void onNumChange(NumberPicker numberPicker);
    }
    public void setOnNumChangeListener(InterfaceOnNumChange listener){
        this.listener = listener;
    }


    public NumberPicker(Context context) {
        super(context);
    }

    public NumberPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        sInstance = this;
        init(context);

    }

    private void init(Context context) {

        LayoutInflater.from(context).inflate(R.layout.num_picker,this);

        tvShow = (TextView) findViewById(R.id.tv_show);
        plus = (RelativeLayout) findViewById(R.id.tv_plus);
        minus = (RelativeLayout) findViewById(R.id.tv_minus);

        plus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tvShow.setText(String.valueOf(++defaultValue));

                if(listener != null)
                    listener.onNumChange(sInstance);
            }
        });

        minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(defaultValue > 1){
                    defaultValue--;
                    tvShow.setText(String.valueOf(defaultValue));
                    if(listener != null)
                        listener.onNumChange(sInstance);
                }
            }
        });

    }

    public NumberPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getValue(){
        return Integer.valueOf(tvShow.getText().toString());
    }
    public void setValue(int value){
        tvShow.setText(String.valueOf(value));
        defaultValue = value;
    }
}
