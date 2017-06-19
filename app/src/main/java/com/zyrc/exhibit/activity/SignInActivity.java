package com.zyrc.exhibit.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mylibrary.base.BaseActivity;
import com.google.gson.Gson;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.DetailExBean;
import com.zyrc.exhibit.model.Model;
import com.zyrc.exhibit.util.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/3 0003.
 */

public class SignInActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_sign_in_back)
    private ImageView ivBack;
    @ViewInject(R.id.tv_sign_in_name)
    private TextView tvName;
    @ViewInject(R.id.tv_sign_in_time)
    private TextView tvTime;
    @ViewInject(R.id.tv_sign_in)
    private TextView tvSignIn;

    private int exhibId;
    private Model model;
    private boolean isSignIn = false;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    String json = (String) msg.obj;
                    DetailExBean bean = new Gson().fromJson(json, DetailExBean.class);
                    tvName.setText(bean.getData().getName());
                    tvTime.setText(bean.getData().getStartDate());
                    break;
                case HandlerConstant.IS_COLLECT:
                    stopLoading();
                    tvSignIn.setText("已签到");
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        x.view().inject(this);
        exhibId = getIntent().getIntExtra("exhibId", 1);
        setListeners();
        initData();
    }

    private void initData() {
        if (model == null) {
            model = new Model();
            model.getData(handler, UrlConstant.HTTP_URL_DETAIL_EX, HandlerConstant.SEARCH_SUCCESS, String.valueOf(exhibId));
        }
    }

    private void setListeners() {
        tvSignIn.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_sign_in_back:
                finish();
                break;
            case R.id.tv_sign_in:
                    signIn();
                break;
        }
    }

    private void signIn() {
        if (model == null) {
            model = new Model();
        }
        Map<String, String> param = new HashMap<>();
        param.put("exhibId", String.valueOf(exhibId));
        param.put("userId", MyApplication.userId);
        model.postData(handler, UrlConstant.HTTP_URL_SIGN_IN, HandlerConstant.IS_COLLECT, param);
        startLoading("签到中...");
    }
}
