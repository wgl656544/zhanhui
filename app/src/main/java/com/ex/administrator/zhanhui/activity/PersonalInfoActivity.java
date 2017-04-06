package com.ex.administrator.zhanhui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ex.administrator.zhanhui.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

public class PersonalInfoActivity extends AppCompatActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_personal_myInfo)
    private LinearLayout llMyInfo;
    @ViewInject(R.id.iv_personal_info_back)
    private ImageView ivback;

    public static PersonalInfoActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        x.view().inject(this);
        setListeners();//设置点击事件
        activity = this;
    }

    //设置点击事件
    private void setListeners() {
        llMyInfo.setOnClickListener(this);
        ivback.setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_personal_myInfo:
                startActivity(new Intent(this, MyInfoActivity.class));
                break;
            case R.id.iv_personal_info_back:
                finish();
                break;
        }
    }
}
