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
import com.zyrc.exhibit.fragment.ConfirmFragment;
import com.zyrc.exhibit.fragment.UnConfirmFragment;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 我的报名页面
 * Created by Administrator on 2017/4/1 0001.
 */

public class MyApplyActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_my_apply_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.tl_apply)
    TabLayout tlApply;//选项卡tab
    @ViewInject(R.id.vp_apply)
    private ViewPager vpApply;//选项卡vp

    private ArrayList<String> titleList = new ArrayList<String>() {{
        add("已确定");
        add("未确定");
    }};
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>() {{
        add(new ConfirmFragment());
        add(new UnConfirmFragment());
    }};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apply);
        x.view().inject(this);
        setListeners();//设置监听器
        AllOrderPageAdapter adapter = new AllOrderPageAdapter(getSupportFragmentManager(), titleList, fragmentList);
        vpApply.setAdapter(adapter);
        tlApply.setTabsFromPagerAdapter(adapter);
        tlApply.setupWithViewPager(vpApply);
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
            case R.id.iv_my_apply_back://返回
                finish();
                break;
        }
    }
}
