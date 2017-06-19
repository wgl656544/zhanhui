package com.zyrc.exhibit.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.mylibrary.base.BaseActivity;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.UrlConstant;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 我的二维码页面
 * Created by Administrator on 2017/5/6 0006.
 */

public class MyQrcodeActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_my_qrcode_back)
    private ImageView ivBack;
    @ViewInject(R.id.iv_my_qrcode)
    private ImageView ivMyQrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qrcode);
        x.view().inject(this);
        setListeners();
        initview();
    }

    private void initview() {
        String url = UrlConstant.HTTP_URL_IP + "/imageCommon/userQrcode?userId=" + MyApplication.userId;
        Glide.with(this).
                load(url).
                into(ivMyQrcode);
    }


    /**
     * 设置监听器
     */
    private void setListeners() {
        ivBack.setOnClickListener(this);
        ivMyQrcode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_my_qrcode_back:
                finish();
                break;
        }
    }
}
