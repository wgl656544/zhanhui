package com.zyrc.exhibit.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyrc.exhibit.R;
import com.zyrc.exhibit.fragment.FindFragment;
import com.zyrc.exhibit.fragment.HomeFragment;
import com.zyrc.exhibit.fragment.MyFragment;
import com.zyrc.exhibit.fragment.TrendsFragment;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_ex_home)
    private LinearLayout homeLinearLayout;
    @ViewInject(R.id.ll_ex_trends)
    private LinearLayout trendsLinearLayout;
    @ViewInject(R.id.ll_ex_find)
    private LinearLayout findLinearLayout;
    @ViewInject(R.id.ll_ex_my)
    private LinearLayout myLinearLayout;
    @ViewInject(R.id.tv_ex_home)
    private TextView tvHome;
    @ViewInject(R.id.tv_ex_trends)
    private TextView tvTrends;
    @ViewInject(R.id.tv_ex_find)
    private TextView tvFind;
    @ViewInject(R.id.tv_ex_my)
    private TextView tvMy;
    @ViewInject(R.id.iv_ex_home)
    private ImageView ivHome;
    @ViewInject(R.id.iv_ex_trends)
    private ImageView ivTrends;
    @ViewInject(R.id.iv_ex_find)
    private ImageView ivFind;
    @ViewInject(R.id.iv_ex_my)
    private ImageView ivMy;


    private HomeFragment mHomeFragment;
    private TrendsFragment mTrendsFragment;
    private FindFragment mFindFragment;
    private MyFragment mMyfragment;


    private TextView[] textViews = new TextView[4];
    private ImageView[] imageViews = new ImageView[4];
    private Fragment[] fragments = new Fragment[4];

    private int clickIndex;
    private int currentIndexFragment = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        //显示默认的界面
        showFragment();
        //把控件都添加到数组中;
        addFragments();
        //设置监听器
        setListeners();
    }

    /**
     * 为控件设置监听器
     */
    private void setListeners() {
        homeLinearLayout.setOnClickListener(this);
        trendsLinearLayout.setOnClickListener(this);
        findLinearLayout.setOnClickListener(this);
        myLinearLayout.setOnClickListener(this);
    }

    /**
     * 把控件都添加到数组中
     */
    private void addFragments() {
        mTrendsFragment = new TrendsFragment();
        mFindFragment = new FindFragment();
        mMyfragment = new MyFragment();
        //fragment
        fragments[0] = mHomeFragment;
        fragments[1] = mTrendsFragment;
        fragments[2] = mFindFragment;
        fragments[3] = mMyfragment;
        //textview
        textViews[0] = tvHome;
        textViews[1] = tvTrends;
        textViews[2] = tvFind;
        textViews[3] = tvMy;
        //imageview
        imageViews[0] = ivHome;
        imageViews[1] = ivTrends;
        imageViews[2] = ivFind;
        imageViews[3] = ivMy;
    }

    /**
     * 显示默认的界面
     */
    private void showFragment() {
        mHomeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        //Transaction 事务：包含多个动作，多个动作有一个出了问题，全部取消，都执行成功，提交事务
        FragmentTransaction transaction = manager.beginTransaction();
        //动作1：添加
        transaction.add(R.id.fragment_container, mHomeFragment);
        //动作2：显示
        transaction.show(mHomeFragment);
        //两个动作都成功了,提交事务
        transaction.commit();
//        //设置图片
        ivHome.setSelected(true);
//        //设置文字颜色
        tvHome.setTextColor(ContextCompat.getColor(this, R.color.colorOrange));
    }

    /**
     * 控件的监听事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_ex_home:
                clickIndex = 0;
                break;
            case R.id.ll_ex_trends:
                clickIndex = 1;
                break;
            case R.id.ll_ex_find:
                clickIndex = 2;
                break;
            case R.id.ll_ex_my:
                clickIndex = 3;
                break;
        }
        //显示点击的fragment
        showClickFragment(clickIndex);
    }

    @Override
    public void recreate() {
        try {//避免重启太快 恢复
            //重启activity后，要移除所有的fragmeny，然后系统才会重新创建
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            for (Fragment fragment : fragments) {
                fragmentTransaction.remove(fragment);
            }
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.recreate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //显示点击的fragment
    public void showClickFragment(int clickIndex) {
        //判断点击的不是当前显示的按钮
        if (clickIndex != currentIndexFragment) {
            //开始事务
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            //隐藏以前的事务
            Fragment hideFragment = fragments[currentIndexFragment];
            transaction.hide(hideFragment);
            //添加新的事务
            Fragment showFragment = fragments[clickIndex];
            if (!showFragment.isAdded()) {
                transaction.add(R.id.fragment_container, showFragment);
            }
            //显示新的事务
            transaction.show(showFragment);
            //提交事务
            transaction.commit();
//            //变颜色
            imageViews[currentIndexFragment].setSelected(false);
            textViews[currentIndexFragment].setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorGray));
            imageViews[clickIndex].setSelected(true);
            textViews[clickIndex].setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorOrange));

            //把点击的fragment赋值给当前的fragment
            currentIndexFragment = clickIndex;
        }
    }
}
