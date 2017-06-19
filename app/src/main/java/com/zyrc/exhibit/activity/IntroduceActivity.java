package com.zyrc.exhibit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
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
 * Created by Administrator on 2017/4/18 0018.
 */

public class IntroduceActivity extends BaseActivity implements View.OnClickListener {
    private WebView mWebView;
    @ViewInject(R.id.iv_introduce_back)
    private ImageView ivBack;

    private String url;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        x.view().inject(this);
        url = getIntent().getStringExtra("url");
        if(TextUtils.isEmpty(url)){
            url = "http://www.gztrib.com";
        }
        initview();
        setListeners();
//        startLoading("正在加载中");
        hideTitleBar();
    }

    private void setListeners() {
        ivBack.setOnClickListener(this);
    }

    //初始化控件
    private void initview() {
        if (!TextUtils.isEmpty(url)) {
            setShowView(ViewType.LOADING_LAYOUT);
            //定义组件
            mWebView = (WebView) findViewById(R.id.web_introduce);
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
            case R.id.iv_introduce_back://返回
                finish();
                break;
        }
    }




}
