package com.ex.administrator.zhanhui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.adapter.AllOrderAdapter;
import com.ex.administrator.zhanhui.fragment.OneOrderFragment;
import com.ex.administrator.zhanhui.fragment.ThreeOrderFragment;
import com.ex.administrator.zhanhui.fragment.TwoOrderFragment;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/1 0001.
 */

public class AllOrderActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_my_order_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.tl_order)
    TabLayout tlOrder;//选项卡tab
    @ViewInject(R.id.vp_order)
    private ViewPager vpOrder;//选项卡vp

    private AllOrderAdapter adapter;
    private ArrayList<String> titleList = new ArrayList<String>() {{
        add("全部订单");
        add("待付款");
        add("退款/售后");
    }};
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>() {{
        add(new OneOrderFragment());
        add(new TwoOrderFragment());
        add(new ThreeOrderFragment());
    }};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_order);
        x.view().inject(this);
        setListeners();//设置监听器
        adapter = new AllOrderAdapter(getSupportFragmentManager(),titleList,fragmentList);
        vpOrder.setAdapter(adapter);
        tlOrder.setTabsFromPagerAdapter(adapter);
        tlOrder.setupWithViewPager(vpOrder);
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        ivBack.setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_my_order_back://返回
                finish();
                break;
        }
    }
}
