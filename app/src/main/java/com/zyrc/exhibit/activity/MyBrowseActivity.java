package com.zyrc.exhibit.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.mylibrary.base.BaseActivity;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.fragment.browse.BrowseBlogFragment;
import com.zyrc.exhibit.fragment.browse.BrowseExhibitFragment;
import com.zyrc.exhibit.fragment.browse.BrowseProductFragment;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public class MyBrowseActivity extends BaseActivity implements View.OnClickListener{
    @ViewInject(R.id.iv_my_browse_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.rg_my_browse)
    private RadioGroup rgBrowse;

    private BrowseBlogFragment blog;
    private BrowseProductFragment product;
    private BrowseExhibitFragment exhibit;

    private Fragment current;
    private Fragment click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_browse);
        x.view().inject(this);
        initview();
        setListeners();
    }

    private void initview() {
        blog = new BrowseBlogFragment();
        product = new BrowseProductFragment();
        exhibit = new BrowseExhibitFragment();
        showFragment(blog);
        current = blog;
        click = blog;
    }

    private void setListeners() {
        ivBack.setOnClickListener(this);
        rgBrowse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_my_browse_blog://新闻
                        click = blog;
                        break;
                    case R.id.rb_my_browse_product://产品
                        click = product;
                        break;
                    case R.id.rb_my_browse_exhibit://展会
                        click = exhibit;
                        break;
                }
                showCheckFragment(click,current);
                current = click;
            }
        });
    }

    /**
     * 显示默认界面
     */
    private void showFragment(Fragment check) {
        FragmentManager manager = getSupportFragmentManager();
        //Transaction 事务：包含多个动作，多个动作有一个出了问题，全部取消，都执行成功，提交事务
        FragmentTransaction transaction = manager.beginTransaction();
        //动作1：添加
        transaction.add(R.id.ll_my_browse, check);
        //动作2：显示
        transaction.show(check);
        //两个动作都成功了,提交事务
        transaction.commit();
    }

    //显示选中的fragment
    public void showCheckFragment(Fragment click, Fragment current) {
        if (click != null && current != null && click != current) {
            //开始事务
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            //隐藏以前的事务
            transaction.hide(current);
            //添加新的事务
            if (!click.isAdded()) {
                transaction.add(R.id.ll_my_browse, click);
            }
            //显示新的事务
            transaction.show(click);
            //提交事务
            transaction.commit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_my_browse_back:
                finish();
                break;
        }
    }
}
