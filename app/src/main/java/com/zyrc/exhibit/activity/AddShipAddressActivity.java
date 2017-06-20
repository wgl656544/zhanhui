package com.zyrc.exhibit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.mylibrary.base.BaseActivity;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.entity.OneUserAddress;
import com.zyrc.exhibit.model.UserModel;
import com.zyrc.exhibit.util.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * 添加收货地址页面
 * Created by Administrator on 2017/3/25 0025.
 */

public class AddShipAddressActivity extends BaseActivity implements View.OnClickListener, AddressPicker.OnAddressPickListener {
    @ViewInject(R.id.iv_add_ship_address_back)
    private ImageView ivBack;
    @ViewInject(R.id.rl_add_ship_address_city)
    private RelativeLayout rlAddCity;
    @ViewInject(R.id.btn_add_ship_address_save)
    private Button btnSave;
    @ViewInject(R.id.tv_add_ship_address_city)
    private TextView tvCity;
    @ViewInject(R.id.et_add_ship_address_name)
    private EditText etName;
    @ViewInject(R.id.et_add_ship_address_phone)
    private EditText etPhone;
    @ViewInject(R.id.et_add_ship_address_street)
    private EditText etStreet;


    private UserModel model = new UserModel();
    private Map<String, String> params;
    private String id;
    private String userId = MyApplication.userId;
    private Activity mActivity;
    private boolean flag = false;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HandlerConstant.SAVE_USER_ADDRESS_SUCCESS) {//保存地址
                String orderId = (String) msg.obj;
                stopLoading();//停止加载动画
                mActivity.setResult(4, new Intent().putExtra("orderId", orderId));
                finish();
            }
            if (msg.what == HandlerConstant.FIND_ONE_ADDRESS_SUCCESS) {//查询一个地址
                stopLoading();//停止加载动画
                OneUserAddress oneUserAddress = (OneUserAddress) msg.obj;
                showDefaultInfo(oneUserAddress);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ship_address);
        x.view().inject(this);
        mActivity = this;
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        flag = intent.getBooleanExtra("flag", false);
        if (id != null) {
            startLoading("正在加载中...");//开始加载动画
            model.findOneUserAddress(handler, id);
        }
        params = new HashMap<>();
        setListeners();//设置监听器
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        rlAddCity.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add_ship_address_back:
                mActivity.setResult(5);
                finish();
                break;
            case R.id.btn_add_ship_address_save:
                startLoading("正在加载中...");//开始加载动画
                saveAddress();//保存地址
                break;
            case R.id.rl_add_ship_address_city:
                showAddress();//选择省市区
                break;
        }
    }

    //选择省份城市区域
    public void showAddress() {
        try {
            ArrayList<Province> data = new ArrayList<>();
            String json = ConvertUtils.toString(getAssets().open("city.json"));
            data.addAll(JSON.parseArray(json, Province.class));
            AddressPicker picker = new AddressPicker(this, data);
            picker.setSelectedItem("北京", "北京", "朝阳");
            picker.setOnAddressPickListener(this);
            picker.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存用户收货地址
     */
    private void saveAddress() {
        if (id != null) {
            params.put("id", id);
        }
        params.put("userId", userId);
        if (!etPhone.getText().toString().equals("")) {
            params.put("address.contactTel", etPhone.getText().toString());
        } else {
            ToastUtil.show(this, "手机号码不能为空");
        }

        if (!etStreet.getText().toString().equals("")) {
            params.put("address.street", etStreet.getText().toString());
        } else {
            ToastUtil.show(this, "详细地址不能为空");
        }

        if (tvCity.getText().toString().equals("")) {
            ToastUtil.show(this, "请选择城市");
        }

        if (!etName.getText().toString().equals("")) {
            params.put("address.contactUserName", etName.getText().toString());
        } else {
            ToastUtil.show(this, "收货人不能为空");
        }

        if (!etName.getText().toString().equals("") &&
                !etPhone.getText().toString().equals("") &&
                !etStreet.getText().toString().equals("") &&
                !tvCity.getText().toString().equals("")) {
            if (flag) {
                params.put("tagType", "1");
            }
            model.userAddress(handler, params);
        }
    }

    //
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            mActivity.setResult(5);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onAddressPicked(Province province, City city, County county) {
        tvCity.setText(province.getAreaName() + " " + city.getAreaName() + " " + county.getAreaName());
        params.put("address.province", province.getAreaName());
        params.put("address.city", city.getAreaName());
        params.put("address.area", county.getAreaName());
    }

    //显示地址信息
    private void showDefaultInfo(OneUserAddress oneUserAddress) {
        etName.setText(oneUserAddress.getData().getAddress().getContactUserName());
        etPhone.setText(oneUserAddress.getData().getAddress().getContactTel());
        etStreet.setText(oneUserAddress.getData().getAddress().getStreet());
        tvCity.setText(oneUserAddress.getData().getAddress().getProvince() + " " +
                oneUserAddress.getData().getAddress().getCity() + " " +
                oneUserAddress.getData().getAddress().getArea());
        params.put("address.province", oneUserAddress.getData().getAddress().getProvince());
        params.put("address.city", oneUserAddress.getData().getAddress().getCity());
        params.put("address.area", oneUserAddress.getData().getAddress().getArea());
    }
}
