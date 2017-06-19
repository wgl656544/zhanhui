package com.zyrc.exhibit.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mylibrary.base.BaseActivity;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.model.Model;
import com.zyrc.exhibit.util.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改密码页面
 * Created by Administrator on 2017/5/16 0016.
 */

public class UpDatePwdActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.et_update_pwd)
    private EditText etPwd;
    @ViewInject(R.id.et_update_pwd_again)
    private EditText etPwdAgain;
    @ViewInject(R.id.btn_update_pwd_save)
    private Button btnSave;

    private String userId;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    ToastUtil.show(UpDatePwdActivity.this, "修改成功");
                    setResult(6);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);
        x.view().inject(this);
        setListeners();
        if (MyApplication.isLogin) {
            userId = MyApplication.userId;
        } else {
            userId = String.valueOf(getIntent().getIntExtra("userId", -1));
        }
    }

    //设置监听器
    private void setListeners() {
        btnSave.setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_pwd_save:
                upDatePwd();
                break;
        }
    }

    private void upDatePwd() {
        String pwd = etPwd.getText().toString();
        String pwdAgain = etPwdAgain.getText().toString();
        if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(pwdAgain)) {
            if (TextUtils.equals(pwd, pwdAgain)) {
                Model model = new Model();
                Map<String, String> param = new HashMap<>();
                param.put("id", userId);
                param.put("Password", pwd);
                model.postData(handler, UrlConstant.HTTP_URL_USER_UPDATE, HandlerConstant.SEARCH_SUCCESS, param);
            } else {
                ToastUtil.show(this, "两次输入不一致!");
            }
        }
    }
}
