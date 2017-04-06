package com.ex.administrator.zhanhui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.ex.administrator.zhanhui.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class EmailLoginActivity extends AppCompatActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_email_login_back)
    private ImageView ivEmailLoginBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        x.view().inject(this);
        //设置监听器
        setListeners();
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        ivEmailLoginBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_email_login_back:
                LoginActivity.login.finish();
                finish();
                break;
        }
    }
}
