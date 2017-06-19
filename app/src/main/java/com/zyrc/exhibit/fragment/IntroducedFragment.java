package com.zyrc.exhibit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zyrc.exhibit.R;
import com.zyrc.exhibit.constant.UrlConstant;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/4/1 0001.
 */

public class IntroducedFragment extends Fragment {
    @ViewInject(R.id.web_branch)
    private WebView mWebView;
    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_introdued, null);
        x.view().inject(this, view);
        int entityId = getActivity().getIntent().getIntExtra("entityId", 1);
        url = UrlConstant.HTTP_URL_EX_IP + "?id=" + entityId;
        initview();
        return view;
    }

    //初始化控件
    private void initview() {
        if (!TextUtils.isEmpty(url)) {
            //加载一个网页
            mWebView.loadUrl(url);
            //如果html中有js则需要设置为true
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setDomStorageEnabled(true);
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            });
        }
    }

}
