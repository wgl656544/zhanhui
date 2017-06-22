package com.zyrc.exhibit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alipay.sdk.app.PayTask;
import com.example.mylibrary.base.BaseActivity;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.WXPayBean;
import com.zyrc.exhibit.model.Model;
import com.zyrc.exhibit.util.PayResult;
import com.zyrc.exhibit.util.ToastUtil;

import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 详细支付页面
 * Created by Administrator on 2017/4/5 0005.
 */

public class PayOrderActivity extends BaseActivity implements View.OnClickListener {
    private static PayOrderActivity mActivity;
    @ViewInject(R.id.ll_pay_order_wx)
    private LinearLayout llWX;//微信
    @ViewInject(R.id.ll_pay_order_zfb)
    private LinearLayout llZFB;//支付宝
    @ViewInject(R.id.iv_pay_order_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.iv_pay_order_wx)
    private ImageView ivWX;
    @ViewInject(R.id.iv_pay_order_zfb)
    private ImageView ivZFB;
    @ViewInject(R.id.btn_pay_order)
    private Button btnPay;

    private String orderId;
    private String total;
    private final int SDK_PAY_FLAG = 2017;
    private static final String WX_APPID = "wxfacc8293ddba891f";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.ZFB://支付宝支付
                    payZFB((String) msg.obj);
                    break;
                case HandlerConstant.WX://微信支付
                    String WXJson = (String) msg.obj;
                    WXPayBean mWxPayBean = new Gson().fromJson(WXJson, WXPayBean.class);
                    payWX(mWxPayBean);
                    break;
                case HandlerConstant.REQUEST_FAIL:
                    ToastUtil.show(PayOrderActivity.this, "提交错误");
                    break;
                case SDK_PAY_FLAG:
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtil.show(PayOrderActivity.this, "支付成功");
                        startActivity(new Intent(PayOrderActivity.this, AllOrderActivity.class).putExtra("index", 2));
                        finish();
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        ToastUtil.show(PayOrderActivity.this, "已取消支付");
                    } else if (TextUtils.equals(resultStatus, "4000")) {
                        ToastUtil.show(PayOrderActivity.this, "交易失败");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);
        x.view().inject(this);
        orderId = getIntent().getStringExtra("orderId");
        total = getIntent().getStringExtra("total");
        setListeners();//设置监听器
        mActivity = this;
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        ivBack.setOnClickListener(this);
        llWX.setOnClickListener(this);
        llZFB.setOnClickListener(this);
        btnPay.setOnClickListener(this);
        btnPay.setText("￥：" + total + "");
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.iv_pay_order_back:
                finish();
                break;
            case R.id.ll_pay_order_wx://选择微信支付
                ivWX.setSelected(true);
                ivZFB.setSelected(false);
                break;
            case R.id.ll_pay_order_zfb://选择支付宝支付
                ivZFB.setSelected(true);
                ivWX.setSelected(false);
                break;
            case R.id.btn_pay_order:
                Model model = new Model();
                Map<String, String> param = new HashMap<>();
                param.put("userId", MyApplication.userId);
                param.put("orderId", orderId);
                if (ivWX.isSelected()) {//微信支付
                    model.postWXPay(handler, UrlConstant.HTTP_URL_WX_PAY, HandlerConstant.WX, param);
                }
                if (ivZFB.isSelected()) {//支付宝支付
                    model.postData(handler, UrlConstant.HTTP_URL_ZFB_PAY, HandlerConstant.ZFB, param);
                }
                break;
        }
    }

    //发起支付宝支付
    private void payZFB(final String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            final String object = jsonObject.getString("data");
            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
                    // 构造PayTask 对象
                    PayTask alipay = new PayTask(PayOrderActivity.this);
                    // 调用支付接口，获取支付结果
                    Map<String, String> result = alipay.payV2(object, true);
                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }
            };
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发起微信支付
    private void payWX(WXPayBean bean) {
        IWXAPI mWxApi = WXAPIFactory.createWXAPI(this, WX_APPID, true);
        mWxApi.registerApp(WX_APPID);
        PayReq req = new PayReq();
        req.appId = WX_APPID;// 微信开放平台审核通过的应用APPID
        req.partnerId = bean.getPartnerid();//商户号
        req.prepayId = bean.getPrepayid();//微信返回的支付交易会话ID
        req.packageValue = "Sign=WXPay";//暂填写固定值Sign=WXPay
        req.nonceStr = bean.getNoncestr();//随机字符串
        req.timeStamp = bean.getTimestamp();//时间戳
        req.sign = bean.getSign();//签名
        mWxApi.sendReq(req);
    }

    public static PayOrderActivity getInstance() {
        return mActivity;
    }
}
