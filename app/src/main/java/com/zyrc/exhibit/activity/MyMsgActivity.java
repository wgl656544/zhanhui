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
import com.zyrc.exhibit.fragment.msg.MsgReadFragment;
import com.zyrc.exhibit.fragment.msg.MsgUnReadFragment;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 我的消息页面
 * Created by Administrator on 2017/5/17 0017.
 */

public class MyMsgActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.rg_my_msg)
    private RadioGroup rgMsg;
    @ViewInject(R.id.iv_my_msg_back)
    private ImageView ivBack;

    private MsgReadFragment fgMsgRead;
    private MsgUnReadFragment fgMsgUnread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_msg);
        x.view().inject(this);
        initview();
        setListeners();
    }

    private void initview() {
        fgMsgRead = new MsgReadFragment();
        fgMsgUnread = new MsgUnReadFragment();
        showFragment(fgMsgUnread);
    }

    //设置监听器
    private void setListeners() {
        ivBack.setOnClickListener(this);
        rgMsg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_my_msg_read://已读消息
                        showCheckFragment(fgMsgRead, fgMsgUnread);
                        break;
                    case R.id.rb_my_msg_unread://未读消息
                        showCheckFragment(fgMsgUnread, fgMsgRead);
                        break;
                }
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
        transaction.add(R.id.ll_my_msg, check);
        //动作2：显示
        transaction.show(check);
        //两个动作都成功了,提交事务
        transaction.commit();
    }

    //显示选中的fragment
    public void showCheckFragment(Fragment check, Fragment uncheck) {
        if (check != null && uncheck != null) {
            //开始事务
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            //隐藏以前的事务
            transaction.hide(uncheck);
            //添加新的事务
            if (!check.isAdded()) {
                transaction.add(R.id.ll_my_msg, check);
            }
            //显示新的事务
            transaction.show(check);
            //提交事务
            transaction.commit();
        }
    }


    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_my_msg_back://返回
                finish();
                break;
        }
    }
}
