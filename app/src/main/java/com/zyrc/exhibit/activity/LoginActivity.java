package com.zyrc.exhibit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibrary.base.BaseActivity;
import com.google.gson.Gson;
import com.mob.tools.utils.UIHandler;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.UserBean;
import com.zyrc.exhibit.model.Model;
import com.zyrc.exhibit.model.UserModel;
import com.zyrc.exhibit.util.ToastUtil;
import com.zyrc.exhibit.util.UserUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 手机号码登录页面
 * Created by Administrator on 2017/2/24 0024.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, PlatformActionListener, Handler.Callback {
    @ViewInject(R.id.tv_my_email_login)
    private TextView tvMyEmailLogin;//邮箱登录
    @ViewInject(R.id.iv_login_back)
    private ImageView ivLoginBack;//烦恼会
    @ViewInject(R.id.et_login_mobile)
    private EditText etLoginMobile;//获取手机号码
    @ViewInject(R.id.btn_login_acquire_code)
    private Button btnLoginAcquireCode;//获取验证码
    @ViewInject(R.id.btn_mobile_login)
    private Button btnMobileLogin;//手机登录
    @ViewInject(R.id.et_login_message_code)
    private EditText etLoginMessageCode;//验证码
    @ViewInject(R.id.iv_login_wx)
    private ImageView ivWX;//微信登录
    @ViewInject(R.id.iv_login_qq)
    private ImageView ivQQ;//qq登录
    @ViewInject(R.id.iv_login_sina)
    private ImageView ivSINA;//新浪登录

    private String TAG;

    private Model model;
    private Map<String, String> param;
    private String type;
    private String openid;//第三方登录userId
    private final int SUCCESS = 100;
    private UserModel userModel;//业务对象
    private String mobile;//手机号码
    private boolean result = true;//60s倒数是否开始
    private int time = 60;//倒数开始
    static LoginActivity login;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            stopLoading();
            switch (msg.what) {
                case HandlerConstant.LOGIN_SUCCESS://登录成功
                    if (msg.obj instanceof UserBean) {
                        UserBean userBean = (UserBean) msg.obj;
                        UserUtils.saveUserInfo(LoginActivity.this, userBean);
                        finish();
                    } else if (msg.obj instanceof String) {
                        String userdata = (String) msg.obj;
                        UserUtils.saveUserInfo(LoginActivity.this, new Gson().fromJson(userdata, UserBean.class));
                        finish();
                    }
                    break;
                case HandlerConstant.REQUEST_FAIL://登录失败
                    ToastUtil.show(LoginActivity.this, "验证码错误");
                    break;
                case 100://获取验证码成功
                    break;
                case HandlerConstant.SEARCH_SUCCESS://第三方登录检查绑定
                    String json = (String) msg.obj;
                    try {
                        JSONObject object = new JSONObject(json);
                        String data = object.getString("data");
                        if (TextUtils.equals(data, "null")) {
                            startActivityForResult(new Intent(LoginActivity.this, VerifyMobileActivity.class).
                                    putExtra("openid", openid).
                                    putExtra("socialType", type), 1);
                        } else {
                            if (model != null && param != null) {
                                param.clear();
                                param.put("userId", data);
                                model.postData(handler, UrlConstant.HTTP_URL_FIND_USERINFO, HandlerConstant.LOGIN_SUCCESS, param);
                            }
                            startLoading("登录中...");
                            ToastUtil.show(LoginActivity.this, "已经绑定");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        x.view().inject(this);
        TAG = getLocalClassName();
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
        ivWX.setOnClickListener(this);
        ivQQ.setOnClickListener(this);
        ivSINA.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.iv_login_back:
                finish();
                break;
            //跳转Email登录界面
            case R.id.tv_my_email_login:
                startActivity(new Intent(this, EmailLoginActivity.class));
                break;
            //获取验证码
            case R.id.btn_login_acquire_code:
                getCode();
                break;
            //登录
            case R.id.btn_mobile_login://手机号码登录
                login();
                break;
            case R.id.iv_login_wx://微信登录
                type = "webchat";
                sanFangLogin(Wechat.NAME);
                break;
            case R.id.iv_login_qq://qq登录
                type = "qq";
                sanFangLogin(QQ.NAME);
                break;
            case R.id.iv_login_sina://新浪登录
                type = "weibo";
                sanFangLogin(SinaWeibo.NAME);
                break;
        }
    }

    /**
     * 第三方登录
     */
    private void sanFangLogin(String name) {
        Platform mPlatform = ShareSDK.getPlatform(name);
        if (mPlatform.isAuthValid()) {
            mPlatform.removeAccount(true);
        }
        mPlatform.setPlatformActionListener(this);
        mPlatform.authorize();
        startLoading("请稍后...");
        mPlatform.showUser(null);
    }


    /**
     * 获取验证码
     */
    private void getCode() {
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
    }

    /**
     * 登录
     */
    private void login() {
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
                time = 60;
            }
        }).start();
    }

    //第三方登录回调
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message message = new Message();
        message.what = SUCCESS;
        message.obj = platform;
        UIHandler.sendMessage(message, this);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        ToastUtil.show(LoginActivity.this, "登录失败");
    }

    @Override
    public void onCancel(Platform platform, int i) {
        ToastUtil.show(LoginActivity.this, "取消登录");
    }

    //主线程
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case SUCCESS:
                Platform platform = (Platform) msg.obj;
                openid = platform.getDb().getUserId();
                ToastUtil.show(LoginActivity.this, "授权成功");
                param = new HashMap<>();
                param.put("socialType", "webchat");
                param.put("openid", openid);
                model = new Model();
                model.postData(handler, UrlConstant.HTTP_URL_BIND_CHECK, HandlerConstant.SEARCH_SUCCESS, param);
                break;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 5) {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
