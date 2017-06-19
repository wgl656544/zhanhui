package com.zyrc.exhibit.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
 * Created by Administrator on 2017/6/2 0002.
 */

public class PositionReserveActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_position_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.et_position_company_name)
    private EditText etCorpName;//公司名称
    @ViewInject(R.id.et_position_email)
    private EditText etEmail;//邮箱
    @ViewInject(R.id.et_position_contact_name)
    private EditText etContactName;//联系人
    @ViewInject(R.id.et_position_mobile)
    private EditText etMobile;//联系电话
    @ViewInject(R.id.et_position_duty)
    private EditText etUserJob;//职务
    @ViewInject(R.id.et_position_remark)
    private EditText etRemark;//备注
    @ViewInject(R.id.btn_position_commit)
    private Button btnCommit;//提交

    private int exhibId;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    ToastUtil.show(PositionReserveActivity.this, "预定成功!");
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_reserve);
        x.view().inject(this);
        exhibId = getIntent().getIntExtra("entityId", 1);
        seListeners();
    }

    /**
     * 设置监听器
     */
    private void seListeners() {
        ivBack.setOnClickListener(this);//返回
        btnCommit.setOnClickListener(this);//提交
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_position_back:
                finish();
                break;
            case R.id.btn_position_commit:
                reservePOosition();
                break;
        }
    }

    /**
     * 预定展会
     */
    private void reservePOosition() {
        String corpName = etCorpName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contactName = etContactName.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String userJob = etUserJob.getText().toString().trim();
        String remark = etRemark.getText().toString().trim();
        if (TextUtils.isEmpty(corpName)) {
            ToastUtil.show(this, "公司名称不能为空！");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            ToastUtil.show(this, "邮件不能为空！");
            return;
        }
        if (TextUtils.isEmpty(contactName)) {
            ToastUtil.show(this, "联系人不能为空！");
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.show(this, "联系电话不能为空！");
            return;
        }
        if (TextUtils.isEmpty(userJob)) {
            ToastUtil.show(this, "职务不能为空！");
            return;
        }
        if (TextUtils.isEmpty(remark)) {
            ToastUtil.show(this, "备注不能为空！");
            return;
        }
        Map<String, String> param = new HashMap<>();
        param.put("userId", MyApplication.userId);
        param.put("exhibId", String.valueOf(exhibId));
        param.put("corp.name", corpName);
        param.put("corp.email", email);
        param.put("username", contactName);
        param.put("mobile", mobile);
        param.put("userJob", userJob);
        param.put("description", remark);
        Model model = new Model();
        model.postData(handler, UrlConstant.HTTP_URL_COMMIT, HandlerConstant.SEARCH_SUCCESS, param);
    }
}
