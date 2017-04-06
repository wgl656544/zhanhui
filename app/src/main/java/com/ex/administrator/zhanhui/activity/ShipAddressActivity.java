package com.ex.administrator.zhanhui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.adapter.ShipAddressAdapter;
import com.ex.administrator.zhanhui.application.MyApplication;
import com.ex.administrator.zhanhui.constant.HandlerConstant;
import com.ex.administrator.zhanhui.entity.ShipAddressBean;
import com.ex.administrator.zhanhui.model.UserModel;
import com.ex.administrator.zhanhui.util.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
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
    private boolean isEditor;
    private String userId = MyApplication.userId;
    private UserModel model = new UserModel();
    ShipAddressBean shipAddressBean;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HandlerConstant.FIND_USER_ADDRESS_SUCCESS) {//查询所有地址
                stopLoading();//停止加载动画
                shipAddressBean = (ShipAddressBean) msg.obj;
                adapter = new ShipAddressAdapter(shipAddressBean, ShipAddressActivity.this);
                lvAddress.setAdapter(adapter);
                adapter.setOnDeteleClickListener(new ShipAddressAdapter.onDeteleClickListener() {//删除地址监听器
                    @Override
                    public void onClick(String id) {
                        startLoading("正在加载中...");//开始加载动画
                        model.deleteUserAddress(handler, id);
                    }
                });
            }
            if (msg.what == HandlerConstant.DELETE_USER_ADDRESS_SUCCESS) {//删除地址
                stopLoading();//停止加载动画
                model.findUserAddress(handler, userId);
                tvCancelOrEditor.setText("编辑");
                isEditor = false;
            }
            if (msg.what == HandlerConstant.SAVE_USER_ADDRESS_SUCCESS) {//设置为默认地址
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_address);
        x.view().inject(this);
        setListeners();
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
                startActivity(new Intent(this, AddShipAddressActivity.class));
                break;
            case R.id.iv_ship_address_back:
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
    protected void onResume() {
        super.onResume();
        model.findUserAddress(handler, userId);
        tvCancelOrEditor.setText("编辑");
        startLoading("正在加载中...");//开始加载动画
        isEditor = false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!isEditor) {
            Map<String, String> map = new HashMap<>();
            map.put("userId", userId);
            map.put("tagType", "1");
            map.put("id", shipAddressBean.getData().get(position).getId() + "");
            model.userAddress(handler, map);
            ToastUtil.show(this, "设置为默认地址");
        } else {
            Intent intent = new Intent(this, AddShipAddressActivity.class);
            intent.putExtra("id", shipAddressBean.getData().get(position).getId() + "");
            startActivity(intent);
        }
    }

}
