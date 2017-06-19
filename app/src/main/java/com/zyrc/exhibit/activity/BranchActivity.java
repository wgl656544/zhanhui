package com.zyrc.exhibit.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mylibrary.base.BaseActivity;
import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.HomeRollPagerAdapter;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.entity.HomePageBean;
import com.zyrc.exhibit.fragment.HonorFragment;
import com.zyrc.exhibit.fragment.IntroducedFragment;
import com.zyrc.exhibit.fragment.PlanFragment;
import com.zyrc.exhibit.model.Model;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 分论坛页面
 * Created by Administrator on 2017/4/21 0021.
 */

public class BranchActivity extends BaseActivity implements OnClickListener {
    @ViewInject(R.id.btn_branch_introduced)
    private Button btnIntroduced;//介绍
    @ViewInject(R.id.btn_branch_agenda)
    private Button btnAgenda;//议程
    @ViewInject(R.id.btn_branch_honor)
    private Button btnHonor;//嘉宾
    @ViewInject(R.id.iv_branch_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.rpv_branch)
    private RollPagerView rpvBranch;

    private IntroducedFragment fgIntroduced;
    private PlanFragment fgBranch;
    private HonorFragment fgHonor;

    private Model model;
    private List<CommonBean.Data> datas;
    private int exhibId;


    private Fragment[] fragments = new Fragment[3];
    private Button[] buttons = new Button[3];

    private int clickIndex = -1;//点击的位置
    private int currentIndex = 0;//当前的位置

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.HOME_PAGE_SUCCESS:
                    String json = (String) msg.obj;
                    HomePageBean bean = new Gson().fromJson(json, HomePageBean.class);
                    showAdvertisement(bean);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);
        x.view().inject(this);
        exhibId = getIntent().getIntExtra("entityId", 1);
        initview();
//        initData();
        setListeners();
        showFragment();
    }

    private void initData() {
        if (model == null) {
            model = new Model();
        }
        //获取广告
        String adversParam = "?name=ex-home-top&exhibId="+1;
        model.getData(handler, UrlConstant.HTTP_URL_DETAIL_EX_ADVERT, HandlerConstant.HOME_PAGE_SUCCESS, adversParam);
    }

    //初始化控件
    private void initview() {
        fgIntroduced = new IntroducedFragment();
        fgBranch = new PlanFragment();
        fgHonor = new HonorFragment();

        fragments[0] = fgIntroduced;
        fragments[1] = fgBranch;
        fragments[2] = fgHonor;

        buttons[0] = btnIntroduced;
        buttons[1] = btnAgenda;
        buttons[2] = btnHonor;
    }

    //设置监听器
    private void setListeners() {
        ivBack.setOnClickListener(this);//返回
        btnIntroduced.setOnClickListener(this);
        btnAgenda.setOnClickListener(this);
        btnHonor.setOnClickListener(this);
    }

    /**
     * 显示默认界面
     */
    private void showFragment() {
        FragmentManager manager = getSupportFragmentManager();
        //Transaction 事务：包含多个动作，多个动作有一个出了问题，全部取消，都执行成功，提交事务
        FragmentTransaction transaction = manager.beginTransaction();
        //动作1：添加
        transaction.add(R.id.ll_branch_container, fgIntroduced);
        //动作2：显示
        transaction.show(fgIntroduced);
        //两个动作都成功了,提交事务
        transaction.commit();
        //显示样式
//        buttons[currentIndex].setSelected(true);
        btnIntroduced.setSelected(true);
//        buttons[currentIndex].setTextColor(ContextCompat.getColor(this, R.color.colorOrange));
        btnIntroduced.setTextColor(ContextCompat.getColor(this, R.color.colorOrange));
    }

    //显示点击的fragment
    public void showClickFragment(int clickIndex) {
        //判断点击的不是当前显示的按钮
        if (clickIndex != currentIndex) {
            //开始事务
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            //隐藏以前的事务
            Fragment hideFragment = fragments[currentIndex];
            transaction.hide(hideFragment);
            //添加新的事务
            Fragment showFragment = fragments[clickIndex];
            if (!showFragment.isAdded()) {
                transaction.add(R.id.ll_branch_container, showFragment);
            }
            //显示新的事务
            transaction.show(showFragment);
            //提交事务
            transaction.commit();
            //变颜色
            buttons[currentIndex].setSelected(false);
            buttons[currentIndex].setTextColor(ContextCompat.getColor(this, R.color.black));
            buttons[clickIndex].setSelected(true);
            buttons[clickIndex].setTextColor(ContextCompat.getColor(this, R.color.colorOrange));
            //把点击的fragment赋值给当前的fragment
            currentIndex = clickIndex;
        }
    }


    /**
     * 显示首页顶部与中部广告
     */
    private void showAdvertisement(HomePageBean bean) {
        String name = "ex-home-top";
        for (int i = 0; i < bean.getData().size(); i++) {
            if (bean.getData().get(i).getName().equals(name)) {
                datas = bean.getData().get(i).getDataList();
            }
        }

        if (rpvBranch != null) {
            //顶部广告
            //设置轮播动画的速度(切换的速度)
            rpvBranch.setAnimationDurtion(500);
            //设置指示器的颜色
            rpvBranch.setHintView(new ColorPointHintView(this, Color.WHITE, Color.GRAY));
            HomeRollPagerAdapter adapter = new HomeRollPagerAdapter(rpvBranch, datas);
            rpvBranch.setAdapter(adapter);
        }
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_branch_back:
                finish();
                break;
            case R.id.btn_branch_introduced://介绍
                clickIndex = 0;
                showClickFragment(clickIndex);
                break;
            case R.id.btn_branch_agenda://议程
                clickIndex = 1;
                showClickFragment(clickIndex);
                break;
            case R.id.btn_branch_honor://嘉宾
                clickIndex = 2;
                showClickFragment(clickIndex);
                break;
        }
    }

}
