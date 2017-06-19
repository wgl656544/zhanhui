package com.zyrc.exhibit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mylibrary.base.BaseActivity;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.model.UserModel;
import com.zyrc.exhibit.util.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * 报名填写页面
 * Created by Administrator on 2017/5/3 0003.
 */

public class ApplyActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_apply_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.tv_apply_company_or_name)
    private TextView tvName;//用户名or公司名称
    @ViewInject(R.id.tv_apply_phone)
    private TextView tvPhone;
    @ViewInject(R.id.et_apply_phone)
    private EditText etPhone;//电话 or 手机
    @ViewInject(R.id.tv_apply_contact_name)
    private TextView tvContactName;//公司联系人
    @ViewInject(R.id.et_apply_company)
    private EditText etCompany;//公司or用户名
    @ViewInject(R.id.et_apply_contact_name)
    private EditText etContactName;
    @ViewInject(R.id.et_apply_email)
    private EditText etEmail;//电子邮件
    @ViewInject(R.id.rg_apply)
    private RadioGroup rgApply;//
    @ViewInject(R.id.rb_apply_personal)
    private RadioButton rbPer;//个人
    @ViewInject(R.id.rb_apply_company)
    private RadioButton rbCompany;//公司
    @ViewInject(R.id.tv_apply_duty)
    private TextView tvDuty;//职务
    @ViewInject(R.id.et_apply_duty)
    private EditText etDuty;//职务
    @ViewInject(R.id.btn_apply_commit)
    private Button btnCommit;//提交

    private String province;
    private String city;

    private int entityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        entityId = getIntent().getIntExtra("entityId", 1);
        x.view().inject(this);
        setListeners();//设置监听器
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        ivBack.setOnClickListener(this);
        btnCommit.setOnClickListener(this);//提交
        rgApply.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_apply_personal://个人
                        showPersonal();
                        break;
                    case R.id.rb_apply_company://公司
                        showCompany();
                        break;
                }
            }
        });
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_apply_back://返回
                finish();
                break;
            case R.id.btn_apply_commit://提交
                if (MyApplication.isLogin) {
                    commitInfo();
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
        }
    }

    private void commitInfo() {
        String name = etCompany.getText().toString();
        String mobile = etPhone.getText().toString();
        String email = etEmail.getText().toString();
        String duty = etDuty.getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(duty)) {
            Map<String, String> param = new HashMap<>();
            UserModel model = new UserModel();
            if (rbPer.isChecked()) {//选中个人
                param.put("username", name);
                param.put("mobile", mobile);
                param.put("email", email);
                param.put("userJob", duty);
                param.put("exhibId", String.valueOf(entityId));
                param.put("userId",MyApplication.userId);
                startLoading("提交中...");
                model.commitInfo(handler, UrlConstant.HTTP_URL_COMMIT, param);
            } else {//选择公司
                String contactName = etContactName.getText().toString();
                if (!TextUtils.isEmpty(contactName)) {
                    param.put("corp.name", name);
                    param.put("mobile", mobile);
                    param.put("corp.email", email);
                    param.put("username", contactName);
                    param.put("userJob", duty);
                    param.put("exhibId", String.valueOf(entityId));
                    param.put("userId",MyApplication.userId);
                    startLoading("提交中...");
                    model.commitInfo(handler, UrlConstant.HTTP_URL_COMMIT, param);
                }
            }
        } else {
            ToastUtil.show(this, "用户名or手机号码or电子邮箱，不能为空");
        }
    }

    //显示个人页面
    private void showPersonal() {
        tvName.setText("用户名：");
        tvPhone.setText("联系手机：");
        tvContactName.setVisibility(View.GONE);
        etContactName.setVisibility(View.GONE);
    }

    //显示企业页面
    private void showCompany() {
        tvName.setText("公司名称：");
        tvPhone.setText("联系电话：");
        tvContactName.setVisibility(View.VISIBLE);
        etContactName.setVisibility(View.VISIBLE);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            stopLoading();
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    ToastUtil.show(ApplyActivity.this, "提交成功");
                    finish();
                    break;
                case HandlerConstant.REQUEST_FAIL:
                    ToastUtil.show(ApplyActivity.this, "提交失败，" + msg.obj);
                    break;
            }
        }
    };
}
