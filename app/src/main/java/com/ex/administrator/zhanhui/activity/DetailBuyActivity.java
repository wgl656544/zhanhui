package com.ex.administrator.zhanhui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.util.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/4/5 0005.
 */

public class DetailBuyActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_detail_buy_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.btn_detail_buy)
    private Button btnBuy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_buy);
        x.view().inject(this);
        //设置监听器
        setListeners();
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        ivBack.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.iv_detail_buy_back:
                finish();
                break;
            //购买
            case R.id.btn_detail_buy:
                ToastUtil.show(this, "点击购买");
                break;
        }
    }
}
