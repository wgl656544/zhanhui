package com.zyrc.exhibit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mylibrary.base.BaseActivity;
import com.google.gson.Gson;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.UserBean;
import com.zyrc.exhibit.model.Model;
import com.zyrc.exhibit.util.ToastUtil;
import com.zyrc.exhibit.util.UserUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证手机页面
 * Created by Administrator on 2017/5/16 0016.
 */

public class VerifyMobileActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.et_verify_mobile)
    private EditText etMobile;//手机号码
    @ViewInject(R.id.et_verify_message_code)
    private EditText etCode;//验证码
    @ViewInject(R.id.btn_verify_acquire_code)
    private Button btnAcquire;//获取验证码
    @ViewInject(R.id.btn_verify_save)
    private Button btnSave;//提交
    @ViewInject(R.id.iv_verify_back)
    private ImageView ivBack;

    private int time = 60;//倒数开始
    private boolean result = true;//60s倒数是否开始
    private Activity mActivity;
    private String mobile;//手机号码
    private Model model;
    private Map<String, String> param;
    private String openid = "";
    private String type = "";


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            stopLoading();
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    String data = (String) msg.obj;
                    UserBean userBean = new Gson().fromJson(data, UserBean.class);
                    int userId = userBean.getData().getId();
                    startActivityForResult(new Intent(mActivity, UpDatePwdActivity.class).putExtra("userId", userId), 1);
                    break;
                case HandlerConstant.LOGIN_SUCCESS://验证手机，绑定给手机成功
                    String json = (String) msg.obj;
                    UserBean userBean1 = new Gson().fromJson(json, UserBean.class);
                    UserUtils.saveUserInfo(VerifyMobileActivity.this, userBean1);
                    setResult(5);
                    finish();
                    break;
                case HandlerConstant.GET_MOBILE_CODE:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile);
        x.view().inject(this);
        type = getIntent().getStringExtra("socialType");
        openid = getIntent().getStringExtra("openid");
        mActivity = this;
        setListeners();
    }

    //设置监听器
    private void setListeners() {
        btnAcquire.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_verify_acquire_code:
                getCode();
                break;
            case R.id.btn_verify_save:
                if (TextUtils.isEmpty(openid)) {
                    login();//忘记密码，修改密码
                } else {
                    typeLogin();//第三方登录绑定手机
                }
                break;
            case R.id.iv_verify_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 6) {//从忘记密码传过来
            finish();
        }
    }

    /**
     * 验证手机
     */
    private void login() {
        //获取输入的验证码
        String msgCode = etCode.getText().toString();
        //判断手机号与验证码不为空
        if (!TextUtils.isEmpty(msgCode) && (mobile.length() == 11)) {
            param.put("mobile", mobile);
            param.put("msgCode", msgCode);
            //不为空，发送请求
            model.postData(handler, UrlConstant.HTTP_URL_MOBILE_LOGIN, HandlerConstant.SEARCH_SUCCESS, param);
            startLoading("加载中...");//开始加载动画
        } else {
            //为空，弹出提示
            Toast.makeText(this, "手机或验证码不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 验证手机
     */
    private void typeLogin() {
        //获取输入的验证码
        String msgCode = etCode.getText().toString();
        //判断手机号与验证码不为空
        if (!TextUtils.isEmpty(msgCode) && (mobile.length() == 11)) {
            param.put("mobile", mobile);
            param.put("msgCode", msgCode);
            param.put("openid", openid);
            param.put("socialType", type);
            //不为空，发送请求
            model.postData(handler, UrlConstant.HTTP_URL_BIND_USER, HandlerConstant.LOGIN_SUCCESS, param);
            startLoading("加载中...");//开始加载动画
        } else {
            //为空，弹出提示
            Toast.makeText(this, "手机或验证码不能为空", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 获取验证码
     */
    private void getCode() {
        model = new Model();
        //获取输入的手机号
        mobile = etMobile.getText().toString();
        //手机号不为空
        if (mobile.length() < 11) {
            ToastUtil.show(this, "请输入正确的手机号码");
        } else {
            //发送请求
            param = new HashMap<>();
            param.put("mobile", mobile);
            model.postData(handler, UrlConstant.HTTP_URL_GET_MESSAGE_CODE, HandlerConstant.GET_MOBILE_CODE, param);
            startLoading("加载中...");//开始加载动画
            //60s后重新获取
            showTime();
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
                        btnAcquire.post(new Runnable() {
                            @Override
                            public void run() {
                                btnAcquire.setText("" + time + "s后重新获取");
                                btnAcquire.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
                                btnAcquire.setEnabled(false);
                            }
                        });
                        Thread.sleep(1000);
                        time--;
                        if (time <= 0) {
                            result = false;
                            btnAcquire.post(new Runnable() {
                                @Override
                                public void run() {
                                    btnAcquire.setText("获取验证码");
                                    btnAcquire.setTextColor(ContextCompat.getColor(mActivity, R.color.black));
                                    btnAcquire.setEnabled(true);
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                result = true;
                time = 60;
            }
        }).start();
    }

}
