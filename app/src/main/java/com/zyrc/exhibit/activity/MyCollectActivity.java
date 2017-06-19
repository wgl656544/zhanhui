package com.zyrc.exhibit.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.mylibrary.base.BaseActivity;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.AllOrderPageAdapter;
import com.zyrc.exhibit.fragment.collect.BlogFragment;
import com.zyrc.exhibit.fragment.collect.ProductFragment;
import com.zyrc.exhibit.fragment.collect.ExhibitFragment;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 所有的订单页面
 * Created by Administrator on 2017/4/1 0001.
 */

public class MyCollectActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_my_collect_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.tl_collect)
    TabLayout tlOrder;//选项卡tab
    @ViewInject(R.id.vp_collect)
    private ViewPager vpOrder;//选项卡vp

    private ArrayList<String> titleList = new ArrayList<String>() {{
        add("新闻");
        add("产品");
        add("展会");
    }};
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>() {{
        add(new BlogFragment());
        add(new ProductFragment());
        add(new ExhibitFragment());
    }};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        x.view().inject(this);
        setListeners();//设置监听器
        AllOrderPageAdapter adapter = new AllOrderPageAdapter(getSupportFragmentManager(), titleList, fragmentList);
        vpOrder.setAdapter(adapter);
        tlOrder.setTabsFromPagerAdapter(adapter);
        tlOrder.setupWithViewPager(vpOrder);
        vpOrder.setOffscreenPageLimit(2);
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
            case R.id.iv_my_collect_back://返回
                finish();
                break;
        }
    }
}
