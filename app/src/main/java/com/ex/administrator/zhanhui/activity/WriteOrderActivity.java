package com.ex.administrator.zhanhui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.view.NumberPicker;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/4/5 0005.
 */

public class WriteOrderActivity extends BaseActivity implements View.OnClickListener, NumberPicker.InterfaceOnNumChange {
    @ViewInject(R.id.iv_write_order_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.numberPicker)
    private NumberPicker numBerPicker;
    @ViewInject(R.id.tv_write_order_total)
    private TextView tvTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_order);
        x.view().inject(this);
        setListeners();//设置监听器
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        ivBack.setOnClickListener(this);//返回
        numBerPicker.setOnNumChangeListener(this);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.iv_write_order_back:
                finish();
                break;
        }
    }

    @Override
    public void onNumChange(NumberPicker numberPicker) {
        tvTotal.setText("￥" + numberPicker.getValue() * 2800 + "");
    }
}
