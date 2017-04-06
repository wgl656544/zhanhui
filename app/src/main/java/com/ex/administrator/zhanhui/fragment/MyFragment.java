package com.ex.administrator.zhanhui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.activity.AllOrderActivity;
import com.ex.administrator.zhanhui.activity.LoginActivity;
import com.ex.administrator.zhanhui.activity.PayOrderActivity;
import com.ex.administrator.zhanhui.activity.PersonalInfoActivity;
import com.ex.administrator.zhanhui.activity.WriteOrderActivity;
import com.ex.administrator.zhanhui.application.MyApplication;
import com.ex.administrator.zhanhui.entity.UserUpDateBean;
import com.ex.administrator.zhanhui.util.SPUtils;
import com.google.gson.Gson;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class MyFragment extends Fragment implements View.OnClickListener {
    @ViewInject(R.id.rl_my_login)
    private RelativeLayout rlMyLogin;//点击登录
    @ViewInject(R.id.ll_my_click)
    private LinearLayout llMyClick;
    @ViewInject(R.id.iv_my_touxiang)
    private CircleImageView ivTouxiang;//头像
    @ViewInject(R.id.tv_my_username)
    private TextView tvName;//用户名
    @ViewInject(R.id.ll_all_order)
    private LinearLayout llAllOrder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        x.view().inject(this, view);

        //设置监听器
        setListeners();
        return view;
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        rlMyLogin.setOnClickListener(this);
        llMyClick.setOnClickListener(this);
        llAllOrder.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //显示头像
        showUserInfo();
    }

    //显示默认用户资料
    private void showUserInfo() {
        if (MyApplication.isLogin) {
            String userinfo = (String) SPUtils.get(getActivity(), "userinfo" + MyApplication.userId, "");
            if (!userinfo.equals("")) {
                UserUpDateBean bean = new Gson().fromJson(userinfo, UserUpDateBean.class);
                Glide.with(getActivity()).load(bean.getData().getHeaderImgUrl()).dontAnimate().into(ivTouxiang);
                tvName.setText(bean.getData().getNickName());
            }
        } else {
            tvName.setText("请登录");
            ivTouxiang.setImageResource(R.drawable.touxiang);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击跳转登录界面
            case R.id.rl_my_login:
                if (MyApplication.isLogin) {//已登录
                    startActivity(new Intent(getActivity(), PersonalInfoActivity.class));
                } else {//没登录
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.ll_my_click://待付款
//                startActivity(new Intent(getActivity(), WriteOrderActivity.class));
                startActivity(new Intent(getActivity(), PayOrderActivity.class));
                break;
            case R.id.ll_all_order://所有订单
                startActivity(new Intent(getActivity(), AllOrderActivity.class));
                break;
        }
    }
}
