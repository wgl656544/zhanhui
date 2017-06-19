package com.zyrc.exhibit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mylibrary.base.BaseActivity;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.ShipAddressAdapter;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.entity.ShipAddressBean;
import com.zyrc.exhibit.model.UserModel;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收货地址页面
 * Created by Administrator on 2017/3/24 0024.
 */

public class ShipAddressActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    @ViewInject(R.id.btn_add_address)
    private Button btnAdd;
    @ViewInject(R.id.iv_ship_address_back)
    private ImageView ivBack;
    @ViewInject(R.id.lv_ship_address_ship)
    private ListView lvAddress;
    @ViewInject(R.id.tv_ship_cancel_or_editor)
    private TextView tvCancelOrEditor;

    private ShipAddressAdapter adapter;
    private List<ShipAddressBean.Data> datas;
    private boolean isEditor;
    private boolean isDefualt;
    private String userId = MyApplication.userId;
    private UserModel model = new UserModel();
    private ShipAddressBean shipAddressBean;
    private Activity mActivity;
    private int position;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HandlerConstant.FIND_USER_ADDRESS_SUCCESS) {//查询所有地址
                shipAddressBean = (ShipAddressBean) msg.obj;
                if (datas != null) {//点击设置默认后刷新界面
                    stopLoading();
                    showAddress(datas, shipAddressBean.getData());
                    datas.clear();
                    datas.addAll(shipAddressBean.getData());
                    adapter.notifyDataSetChanged();
                } else {//首次进入查询所有
                    stopLoading();//停止加载动画
                    datas = shipAddressBean.getData();
                    adapter = new ShipAddressAdapter(datas, ShipAddressActivity.this);
                    lvAddress.setAdapter(adapter);
                    adapter.setOnDeteleClickListener(new ShipAddressAdapter.onDeteleClickListener() {//删除地址监听器
                        @Override
                        public void onClick(String id) {
                            startLoading("正在加载中...");//开始加载动画
                            model.deleteUserAddress(handler, id);
                        }
                    });
                }
            }
            if (msg.what == HandlerConstant.DELETE_USER_ADDRESS_SUCCESS) {//删除地址
                stopLoading();//停止加载动画
                model.findUserAddress(handler, userId);
            }
            if (msg.what == HandlerConstant.SAVE_USER_ADDRESS_SUCCESS) {//设置为默认地址
                model.findUserAddress(handler, userId);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_address);
        x.view().inject(this);
        mActivity = this;
        setListeners();
        model.findUserAddress(handler, userId);
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        btnAdd.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvCancelOrEditor.setOnClickListener(this);
        lvAddress.setOnItemClickListener(this);
    }


    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_address:
                //添加界面
//                startActivity(new Intent(this, AddShipAddressActivity.class));
                startActivityForResult(new Intent(this, AddShipAddressActivity.class), 1);
                break;
            case R.id.iv_ship_address_back:
                mActivity.setResult(6);
                finish();//返回
                break;
            case R.id.tv_ship_cancel_or_editor:
                if (isEditor) {//变成不在编辑状态
                    tvCancelOrEditor.setText("编辑");
                    isEditor = false;
                    adapter.isEditor = isEditor;
                } else {//变成编辑状态
                    tvCancelOrEditor.setText("取消");
                    isEditor = true;
                    adapter.isEditor = isEditor;
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 4) {
//            String orderId = data.getStringExtra("orderId");
//            model.findOneUserAddress(handler, orderId);
            model.findUserAddress(handler, userId);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!isEditor) {//设置为默认地址
            if (datas.get(position).getTagType() != 1) {
                this.position = position;
                setDefaultAddress(datas.get(position).getId());
            }
            isDefualt = true;
        } else {//跳转编辑页面
            Intent intent = new Intent(this, AddShipAddressActivity.class);
            intent.putExtra("id", shipAddressBean.getData().get(position).getId() + "");
            startActivity(intent);
        }
    }

    /**
     * @param orderId 地址id
     */
    private void setDefaultAddress(int orderId) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("tagType", "1");
        map.put("id", String.valueOf(orderId));
        model.userAddress(handler, map);
        startLoading("正在修改中...");
    }

    /**
     * @param oldData //首次进入发送请求拿到的数组
     * @param newData //再次发送请求拿到的数组
     */
    private void showAddress(List<ShipAddressBean.Data> oldData, List<ShipAddressBean.Data> newData) {
        if (newData.size() > oldData.size()) {//新增地址
            boolean flag = false;
            for (ShipAddressBean.Data data : newData) {
                if (data.getTagType() == 1) {
                    flag = true;
                }
            }
            if (!flag) {
                setDefaultAddress(newData.get(0).getId());
            }
        } else if (newData.size() < oldData.size() && newData.size() > 0) {//删除地址
            boolean flag = false;
            for (ShipAddressBean.Data data : newData) {
                if (data.getTagType() == 1) {
                    flag = true;
                }
            }
            if (!flag) {
                setDefaultAddress(newData.get(0).getId());
            }
        } else if (newData.size() == oldData.size()) {//设置默认
            if (isDefualt) {
                isDefualt = false;
                mActivity.setResult(4, new Intent().putExtra("orderId", String.valueOf(oldData.get(position).getId())));
                finish();
            }
        } else if (newData.size() == 0){
            tvCancelOrEditor.setText("编辑");
            isEditor = false;
            adapter.isEditor = isEditor;
        }
    }

}
