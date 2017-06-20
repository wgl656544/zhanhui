package com.zyrc.exhibit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mylibrary.base.BaseActivity;
import com.google.gson.Gson;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.UserBean;
import com.zyrc.exhibit.model.Model;
import com.zyrc.exhibit.util.SPUtils;
import com.zyrc.exhibit.util.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户名登录页面
 * Created by Administrator on 2017/2/24 0024.
 */

public class EmailLoginActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_email_login_back)
    private ImageView ivEmailLoginBack;//返回
    @ViewInject(R.id.et_email_login_username)
    private EditText etUserName;//用户名
    @ViewInject(R.id.et_email_login_pwd)
    private EditText etPwd;//密码
    @ViewInject(R.id.btn_email_login)
    private Button btnLogin;//登录
    @ViewInject(R.id.tv_email_update_pwd)
    private TextView tvUpDatePwd;

    private Model model;
    private Activity mActivity;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.LOGIN_SUCCESS:
                    stopLoading();
                    String user = (String) msg.obj;
                    UserBean userBean = new Gson().fromJson(user, UserBean.class);
                    SPUtils.put(mActivity, "userId", userBean.getData().getId() + "");//保存id
                    SPUtils.put(mActivity, "userinfo" + userBean.getData().getId(), user);//保存信息
                    MyApplication.isLogin = true;
                    MyApplication.userId = userBean.getData().getId() + "";
                    finish();
                    LoginActivity.login.finish();
                    break;
                case HandlerConstant.REQUEST_FAIL:
                    stopLoading();
                    ToastUtil.show(mActivity, "账号或密码错误");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        x.view().inject(this);
        mActivity = this;
        //设置监听器
        setListeners();
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        ivEmailLoginBack.setOnClickListener(this);//返回
        btnLogin.setOnClickListener(this);//登录
        tvUpDatePwd.setOnClickListener(this);//忘记密码
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_email_login_back:
                LoginActivity.login.finish();
                finish();
                break;
            case R.id.btn_email_login:
                login();
                break;
            case R.id.tv_email_update_pwd:
                startActivity(new Intent(this, VerifyMobileActivity.class));
                break;
        }
    }

    //登录
    private void login() {
        String username = etUserName.getText().toString();
        String pwd = etPwd.getText().toString();
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
            model = new Model();
            Map<String, String> param = new HashMap<>();
            param.put("username", username);
            param.put("password", pwd);
            startLoading("登录中...");
            model.postData(handler, UrlConstant.HTTP_URL_USER_LOGIN, HandlerConstant.LOGIN_SUCCESS, param);
        }
    }
}
