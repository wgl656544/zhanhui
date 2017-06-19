package com.zyrc.exhibit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mylibrary.base.BaseActivity;
import com.google.gson.Gson;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.TicketReserveAdapter;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.model.Model;
import com.zyrc.exhibit.model.UserModel;
import com.zyrc.exhibit.util.ToastUtil;
import com.zyrc.exhibit.view.FullyGridLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报名填写页面
 * Created by Administrator on 2017/5/3 0003.
 */

public class TicketReserveActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_ticket_reserve_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.tv_ticket_reserve_company_or_name)
    private TextView tvName;//用户名or公司名称
    @ViewInject(R.id.tv_ticket_reserve_phone)
    private TextView tvPhone;
    @ViewInject(R.id.et_ticket_reserve_phone)
    private EditText etPhone;//电话 or 手机
    @ViewInject(R.id.tv_ticket_reserve_contact_name)
    private TextView tvContactName;//公司联系人
    @ViewInject(R.id.et_ticket_reserve_company)
    private EditText etCompany;//公司or用户名
    @ViewInject(R.id.et_ticket_reserve_contact_name)
    private EditText etContactName;
    @ViewInject(R.id.et_ticket_reserve_email)
    private EditText etEmail;//电子邮件
    @ViewInject(R.id.rg_ticket_reserve)
    private RadioGroup rgApply;//
    @ViewInject(R.id.rb_ticket_reserve_personal)
    private RadioButton rbPer;//个人
    @ViewInject(R.id.rb_ticket_reserve_company)
    private RadioButton rbCompany;//公司
    @ViewInject(R.id.tv_ticket_reserve_duty)
    private TextView tvDuty;//职务
    @ViewInject(R.id.et_ticket_reserve_duty)
    private EditText etDuty;//职务
    @ViewInject(R.id.btn_ticket_reserve_commit)
    private Button btnCommit;//提交
    @ViewInject(R.id.rl_ticket_reserve)
    private RecyclerView rlTicketReserve;


    private int entityId;
    private String entityName;
    private String busiData = "";
    private Model model;
    private List<CommonBean.Data> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_reserve);
        entityId = getIntent().getIntExtra("entityId", 1);
        entityName = getIntent().getStringExtra("entityName");
        x.view().inject(this);
        setListeners();//设置监听器
        initData();
    }

    //初始化数据
    private void initData() {
        if (model == null) {
            model = new Model();
        }
        String param = "?entityId=" + entityId + "&entityName=" + entityName;
        model.getData(handler, UrlConstant.HTTP_URL_GET_EXHIBID_FOR_NAME, HandlerConstant.COLLECT_SUCCESS, param);
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
                    case R.id.rb_ticket_reserve_personal://个人
                        showPersonal();
                        break;
                    case R.id.rb_ticket_reserve_company://公司
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
            case R.id.iv_ticket_reserve_back://返回
                finish();
                break;
            case R.id.btn_ticket_reserve_commit://提交
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
                param.put("userId", MyApplication.userId);
                if (TextUtils.equals(busiData, "")) {
                    ToastUtil.show(this, "请至少选择一个论坛！");
                    return;
                } else {
                    param.put("busiData", busiData);
                }
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
                    param.put("userId", MyApplication.userId);
                    if (TextUtils.equals(busiData, "")) {
                        ToastUtil.show(this, "请至少选择一个论坛！");
                        return;
                    } else {
                        param.put("busiData", busiData);
                    }
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

    private void showAllEx(final List<CommonBean.Data> datas) {
        rlTicketReserve.setLayoutManager(new FullyGridLayoutManager(this, 2));
        TicketReserveAdapter adapter = new TicketReserveAdapter(R.layout.item_ticket_reserve_list, datas);
        rlTicketReserve.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String index = String.valueOf(datas.get(position).getEntityId());
                CheckBox cb = (CheckBox) view;
                if (cb.isChecked()) {
                    if (TextUtils.equals(busiData, "")) {
                        busiData = busiData + index;
                    } else {
                        busiData = busiData + "," + index;
                    }
                } else {
                    if (busiData.indexOf(index) == 0) {
                        if (busiData.length() > 1) {
                            busiData = busiData.replace(index + ",", "");
                        } else {
                            busiData = busiData.replace(index, "");
                        }
                    } else {
                        busiData = busiData.replace("," + index, "");
                    }
                }
            }
        });
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            stopLoading();
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    ToastUtil.show(TicketReserveActivity.this, "提交成功");
                    finish();
                    break;
                case HandlerConstant.REQUEST_FAIL:
                    ToastUtil.show(TicketReserveActivity.this, "提交失败，" + msg.obj);
                    break;
                case HandlerConstant.COLLECT_SUCCESS:
                    String json = (String) msg.obj;
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        JSONObject object = jsonObject.getJSONObject("data");
                        String exhibId = object.getString("exhibId");
                        if (model == null) {
                            model = new Model();
                        }
                        String param = "?exhibId=" + exhibId;
                        model.getData(handler, UrlConstant.HTTP_URL_GET_ALL_EX, HandlerConstant.GET_ALL_EX, param);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case HandlerConstant.GET_ALL_EX:
                    String allJson = (String) msg.obj;
                    CommonBean bean = new Gson().fromJson(allJson, CommonBean.class);
                    datas = bean.getData();
                    showAllEx(datas);
                    break;
            }
        }
    };
}
