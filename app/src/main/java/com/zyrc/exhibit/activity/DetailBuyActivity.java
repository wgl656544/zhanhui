package com.zyrc.exhibit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mylibrary.base.BaseActivity;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.model.Model;
import com.zyrc.exhibit.sharesdk.onekeyshare.OnekeyShare;
import com.zyrc.exhibit.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;

/**
 * 详细购买页面
 * Created by Administrator on 2017/4/5 0005.
 */

public class   DetailBuyActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_detail_buy_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.iv_detail_buy_share)
    private ImageView ivShare;///分享
    @ViewInject(R.id.btn_detail_buy)
    private Button btnBuy;//购买按钮
    @ViewInject(R.id.iv_detail_buy_collect)
    private ImageView ivCollect;//收藏按钮
    @ViewInject(R.id.tv_buy_price)
    private TextView tvPrice;
    @ViewInject(R.id.web_detail_buy)
    private WebView mWebView;

    private Model model;
    private boolean isCollect = false;
    private String SHARESDK_KEY = "1d857a628c00b";
    private CommonBean.Data data;
    private String url;
    private String userId = "userId";//参数id
    private String eventType = "eventType";//参数类型
    private String entityName = "entityName";//产品类型
    private String entityId = "entityId";//产品id
    private Map<String, String> param;
    private String strParam = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = (String) msg.obj;
            switch (msg.what) {
                case HandlerConstant.IS_COLLECT://查询是否收藏
                    try {
                        JSONObject object = new JSONObject(json);
                        String data = object.getString("data");
                        if (TextUtils.equals(data, "0")) {
                            ivCollect.setImageResource(R.drawable.chanel_collect);
                            isCollect = false;
                        } else {
                            ivCollect.setImageResource(R.drawable.collect);
                            isCollect = true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case HandlerConstant.COLLECT_SUCCESS://收藏成功
                    isCollect = true;
                    ToastUtil.show(DetailBuyActivity.this, "收藏成功");
                    ivCollect.setImageResource(R.drawable.collect);
                    break;
                case HandlerConstant.COLLECT_CANCEL://取消收藏
                    ToastUtil.show(DetailBuyActivity.this, "已取消收藏");
                    ivCollect.setImageResource(R.drawable.chanel_collect);
                    isCollect = false;
                    break;
                case HandlerConstant.REQUEST_FAIL:
                    ToastUtil.show(DetailBuyActivity.this, "失败");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_buy);
        x.view().inject(this);
        ShareSDK.initSDK(this, SHARESDK_KEY);
        data = (CommonBean.Data) getIntent().getSerializableExtra(HandlerConstant.DETAIL_BUY);
        //设置监听器
        setListeners();
        //初始化控件
        initview();
        //检查收藏
        isCollect(UrlConstant.HTTP_URL_IS_COLLECT, HandlerConstant.IS_COLLECT);
    }

    //初始化控件
    private void initview() {
        if (data != null) {
            url = data.getLinkUrl();
            tvPrice.setText("￥" + data.getPrice() + "");
            if (TextUtils.equals(data.getPrice(), "0.00")) {
                btnBuy.setText("立即预定");
            }
            //加载一个网页
            mWebView.loadUrl(url);
            //如果html中有js则需要设置为true
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setDomStorageEnabled(true);
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    setShowView(ViewType.CONTAINER_LAYOUT);
                }
            });
        }

    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        ivBack.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        ivCollect.setOnClickListener(this);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.iv_detail_buy_back:
                finish();
                break;
            case R.id.iv_detail_buy_share:
                showShare();
                break;
            //购买
            case R.id.btn_detail_buy:
                if (MyApplication.isLogin) {
                    if (TextUtils.equals(data.getPrice(), "0.00")) {
                        startActivity(new Intent(this, TicketReserveActivity.class).putExtra("entityId", data.getEntityId()).putExtra("entityName",data.getEntityName()));
                    } else {
                        startActivity(new Intent(this, WriteOrderActivity.class).putExtra(HandlerConstant.DETAIL_BUY, data));
                    }
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            //收藏按钮
            case R.id.iv_detail_buy_collect:
                if (MyApplication.isLogin) {
                    if (isCollect) {//已经收藏(即再次点击取消收藏)
                        collect(UrlConstant.HTTP_URL_CANCEL_COLLECT, HandlerConstant.COLLECT_CANCEL);
                    } else {//没有收藏(即点击收藏)
                        collect(UrlConstant.HTTP_URL_ADD_COLLECT, HandlerConstant.COLLECT_SUCCESS);
                    }
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
        }
    }

    /**
     * 进行收藏，已经收藏就取消收藏
     */
    private void collect(String url, int requestCode) {
        if (MyApplication.isLogin) {
            if (model == null) {
                model = new Model();
            }
            if (param == null) {
                param = new HashMap<>();
            }
            param.put(userId, MyApplication.userId);
            param.put(eventType, "wish");
            param.put(entityName, data.getEntityName());
            param.put(entityId, String.valueOf(data.getEntityId()));
            model.postData(handler, url, requestCode, param);
        }
    }

    /**
     * 查询是否已经收藏
     */
    private void isCollect(String url, int requestCode) {
        if (MyApplication.isLogin) {
            if (model == null) {
                model = new Model();
            }
            strParam = "?userId=" + MyApplication.userId + "&eventType=wish" + "&entityName=" + data.getEntityName() + "&entityId=" + data.getEntityId();
            model.getData(handler, url, requestCode, strParam);
        }
    }

    //调出分享界面
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("这是一个分享标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是要分享的内容啊！");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://139.129.202.208/M00/00/02/ChlK2lkDBvKAfZIBAAAXTMPlumI782.png");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(this);
    }

    @Override
    protected void onDestroy() {
        ShareSDK.stopSDK(this);
        super.onDestroy();
    }
}
