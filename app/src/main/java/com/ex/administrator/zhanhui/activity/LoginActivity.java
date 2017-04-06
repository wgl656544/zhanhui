package com.ex.administrator.zhanhui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.application.MyApplication;
import com.ex.administrator.zhanhui.constant.HandlerConstant;
import com.ex.administrator.zhanhui.entity.UserBean;
import com.ex.administrator.zhanhui.model.UserModel;
import com.ex.administrator.zhanhui.util.SPUtils;
import com.ex.administrator.zhanhui.util.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.tv_my_email_login)
    private TextView tvMyEmailLogin;
    @ViewInject(R.id.iv_login_back)
    private ImageView ivLoginBack;
    @ViewInject(R.id.et_login_mobile)
    private EditText etLoginMobile;
    @ViewInject(R.id.btn_login_acquire_code)
    private Button btnLoginAcquireCode;
    @ViewInject(R.id.btn_mobile_login)
    private Button btnMobileLogin;
    @ViewInject(R.id.et_login_message_code)
    private EditText etLoginMessageCode;

    private Intent intent;
    private UserModel userModel;//业务对象
    private String mobile;//手机号码
    private boolean result = true;//60s倒数是否开始
    private int time = 30;//倒数开始
    static LoginActivity login;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HandlerConstant.LOGIN_SUCCESS) {
                stopLoading();//停止加载动画
                UserBean userBean = (UserBean) msg.obj;
                SPUtils.put(LoginActivity.this, "userId", userBean.getData().getId() + "");
                MyApplication.isLogin = true;
                MyApplication.userId = userBean.getData().getId() + "";
                finish();
            }
            if (msg.what == HandlerConstant.REQUEST_FAIL) {
                stopLoading();
                ToastUtil.show(LoginActivity.this, "登录失败");
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        x.view().inject(this);
        login = this;
        //业务对象
        userModel = new UserModel();
        //设置监听器
        setListeners();

    }

    /**
     * 这是监听器
     */
    private void setListeners() {
        tvMyEmailLogin.setOnClickListener(this);
        ivLoginBack.setOnClickListener(this);
        btnLoginAcquireCode.setOnClickListener(this);
        btnMobileLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //跳转Email登录界面
            case R.id.tv_my_email_login:
                intent = new Intent(this, EmailLoginActivity.class);
                startActivity(intent);
                break;

            //返回
            case R.id.iv_login_back:
                finish();
                break;

            //获取验证码
            case R.id.btn_login_acquire_code:
                //获取输入的手机号
                mobile = etLoginMobile.getText().toString();
                //手机号不为空
                if (mobile.length() < 11) {
                    ToastUtil.show(this, "请输入正确的手机号码");
                } else {
                    //发送请求
                    userModel.getMessageCode(handler, mobile);
                    startLoading("加载中...");//开始加载动画
                    //60s后重新获取
                    showTime();
                }
                break;

            //登录
            case R.id.btn_mobile_login:
                //获取输入的验证码
                String msgCode = etLoginMessageCode.getText().toString();
                mobile = etLoginMobile.getText().toString();
                //判断手机号与验证码不为空
                if (!mobile.equals("") && !msgCode.equals("")) {
                    //不为空，发送请求
                    userModel.mobileLogin(handler, mobile, msgCode);
                    startLoading("加载中...");//开始加载动画
                } else {
                    //为空，弹出提示
                    Toast.makeText(this, "手机或验证码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 验证码60s获取一次
     */
    private void showTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (result) {
                    try {
                        btnLoginAcquireCode.post(new Runnable() {
                            @Override
                            public void run() {
                                btnLoginAcquireCode.setText("" + time + "s后重新获取");
                                btnLoginAcquireCode.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.white));
                                btnLoginAcquireCode.setEnabled(false);
                            }
                        });
                        Thread.sleep(1000);
                        time--;
                        if (time <= 0) {
                            result = false;
                            btnLoginAcquireCode.post(new Runnable() {
                                @Override
                                public void run() {
                                    btnLoginAcquireCode.setText("获取验证码");
                                    btnLoginAcquireCode.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.black));
                                    btnLoginAcquireCode.setEnabled(true);
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                result = true;
                time = 6;
            }
        }).start();
    }

}
