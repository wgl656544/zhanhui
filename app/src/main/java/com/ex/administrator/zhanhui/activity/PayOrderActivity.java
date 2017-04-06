package com.ex.administrator.zhanhui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.util.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/4/5 0005.
 */

public class PayOrderActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_pay_order_wx)
    private LinearLayout llWX;//微信
    @ViewInject(R.id.ll_pay_order_zfb)
    private LinearLayout llZFB;//支付宝
    @ViewInject(R.id.iv_pay_order_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.iv_pay_order_wx)
    private ImageView ivWX;
    @ViewInject(R.id.iv_pay_order_zfb)
    private ImageView ivZFB;
    @ViewInject(R.id.btn_pay_order)
    private Button btnPay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);
        x.view().inject(this);
        setListeners();//设置监听器
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        ivBack.setOnClickListener(this);
        llWX.setOnClickListener(this);
        llZFB.setOnClickListener(this);
        btnPay.setOnClickListener(this);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.iv_pay_order_back:
                finish();
                break;
            case R.id.ll_pay_order_wx://选择微信支付
                ivWX.setSelected(true);
                ivZFB.setSelected(false);
                break;
            case R.id.ll_pay_order_zfb://选择支付宝支付
                ivZFB.setSelected(true);
                ivWX.setSelected(false);
                break;
            case R.id.btn_pay_order:
                if (ivWX.isSelected()) {
                    ToastUtil.show(this, "微信支付");
                }
                if (ivZFB.isSelected()) {
                    ToastUtil.show(this, "支付宝支付");
                }
                break;
        }
    }
}
