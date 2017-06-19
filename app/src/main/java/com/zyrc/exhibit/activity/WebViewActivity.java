package com.zyrc.exhibit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

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
 * 详情页面
 * Created by Administrator on 2017/4/18 0018.
 */

public class WebViewActivity extends BaseActivity implements View.OnClickListener {
    private WebView mWebView;
    @ViewInject(R.id.iv_web_back)
    private ImageView ivBack;
    @ViewInject(R.id.iv_web_share)
    private ImageView ivShare;
    @ViewInject(R.id.iv_web_collect)
    private ImageView ivCollect;

    private String url;
    private Model model;
    private boolean isCollect = false;
    private String SHARESDK_KEY = "1d857a628c00b";
    private String userId = "userId";//参数id
    private String id;//用户id
    private String eventType = "eventType";//参数类型
    private String entityName = "entityName";//产品类型
    private String entityId = "entityId";//产品id
    private Map<String, String> param;
    private String strParam = "";

    private CommonBean.Data data;


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
                    ToastUtil.show(WebViewActivity.this, "收藏成功");
                    ivCollect.setImageResource(R.drawable.collect);
                    break;
                case HandlerConstant.COLLECT_CANCEL://取消收藏
                    ToastUtil.show(WebViewActivity.this, "已取消收藏");
                    ivCollect.setImageResource(R.drawable.chanel_collect);
                    isCollect = false;
                    break;
                case HandlerConstant.REQUEST_FAIL:
                    ToastUtil.show(WebViewActivity.this, "失败");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        x.view().inject(this);
        ShareSDK.initSDK(this, SHARESDK_KEY);
        data = (CommonBean.Data) getIntent().getSerializableExtra(HandlerConstant.DETAIL_BLOG);
        if (data != null) {
            if (TextUtils.equals(data.getLinkUrl(), "")) {
                url = "http://www.gztrib.com";
            } else {
                url = data.getLinkUrl();
            }
        }
        initview();
        setListeners();
//        startLoading("正在加载中");
        hideTitleBar();
        //检查收藏
        isCollect(UrlConstant.HTTP_URL_IS_COLLECT, HandlerConstant.IS_COLLECT);
    }

    private void setListeners() {
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        ivCollect.setOnClickListener(this);
    }

    //初始化控件
    private void initview() {
        if (!TextUtils.isEmpty(url)) {
            setShowView(ViewType.LOADING_LAYOUT);
            //定义组件
            mWebView = (WebView) findViewById(R.id.webview);
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

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_web_back://返回
                finish();
                break;
            case R.id.iv_web_share://分享
                showShare();
                break;
            case R.id.iv_web_collect://收藏
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
        oks.setTitle(data.getName());
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(data.getDescription());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(data.getImageUrl());
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

}
