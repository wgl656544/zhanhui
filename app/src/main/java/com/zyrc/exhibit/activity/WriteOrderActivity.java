package com.zyrc.exhibit.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mylibrary.base.BaseActivity;
import com.google.gson.Gson;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.entity.AddOrderBean;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.entity.OneUserAddress;
import com.zyrc.exhibit.entity.ShipAddressBean;
import com.zyrc.exhibit.model.OrderModel;
import com.zyrc.exhibit.model.UserModel;
import com.zyrc.exhibit.view.NumberPicker;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单填写页面
 * Created by Administrator on 2017/4/5 0005.
 */

public class WriteOrderActivity extends BaseActivity implements View.OnClickListener,
        NumberPicker.InterfaceOnNumChange, NumberPicker.OnLoadListener {
    @ViewInject(R.id.iv_write_order_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.numberPicker)
    private NumberPicker numBerPicker;
    @ViewInject(R.id.tv_write_order_total)
    private TextView tvTotal;//产品总价
    @ViewInject(R.id.btn_order_save)
    private Button btnSave;

    @ViewInject(R.id.ll_order_address)
    private LinearLayout llAddress;//默认收货地址
    @ViewInject(R.id.tv_order_name)
    private TextView tvOrderName;//收货人姓名
    @ViewInject(R.id.tv_order_phone)
    private TextView tvOrderPhone;//收货人手机
    @ViewInject(R.id.tv_order_street)
    private TextView tvOrderStreet;//收货人地址
    @ViewInject(R.id.tv_order_title)
    private TextView tvTitle;//产品名字
    @ViewInject(R.id.tv_order_price)
    private TextView tvPrice;//产品价格

    private int count = 1;
    private Serializable s;
    private String total;
    private Activity mActivity;
    private String userId;
    private CommonBean.Data ticket;
    private UserModel model = new UserModel();
    private OrderModel orderModel;
    private ShipAddressBean shipAddressBean;
    private ShipAddressBean.Data defaultAddress;
    private int productId;//产品id
    private int addId;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            stopLoading();
            if (msg.what == HandlerConstant.FIND_USER_ADDRESS_SUCCESS) {//查询所有地址
                shipAddressBean = (ShipAddressBean) msg.obj;
                stopLoading();
                if (shipAddressBean.getData().size() == 0) {//遍历
                    setDefaultAddress();
                } else {
                    for (ShipAddressBean.Data data : shipAddressBean.getData()) {
                        if (data.getTagType() == 1) {
                            defaultAddress = data;//找到默认地址
                            addId = data.getId();
                        }
                    }
                    if (defaultAddress == null) {
                        defaultAddress = shipAddressBean.getData().get(0);
                        addId = defaultAddress.getId();
                    }
                    showDefault(defaultAddress);//显示默认地址
                }
            }

            if (msg.what == HandlerConstant.FIND_ONE_ADDRESS_SUCCESS) {
                OneUserAddress one = (OneUserAddress) msg.obj;
                showDefault1(one.getData());
            }
            if (msg.what == HandlerConstant.SEARCH_SUCCESS) {
                String orderId = (String) msg.obj;
                startActivity(new Intent(WriteOrderActivity.this, PayOrderActivity.class).putExtra("orderId", orderId).putExtra("total", total));
                finish();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_order);
        x.view().inject(this);
        s = getIntent().getSerializableExtra(HandlerConstant.DETAIL_BUY);
        mActivity = this;
        setListeners();//设置监听器
        intiview();
        userId = MyApplication.userId;
        model.findUserAddress(handler, userId);
        startLoading("正在加载中...");
    }

    /**
     * 初始化控件
     */
    private void intiview() {
        if (s instanceof CommonBean.Data) {
            ticket = (CommonBean.Data) s;
            productId = ticket.getEntityId();
            numBerPicker.setProductId(productId);
            total = ticket.getPrice();
            tvTitle.setText(ticket.getName());
            tvPrice.setText(ticket.getPrice());
            tvTotal.setText("￥" + total + "");
        }
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        ivBack.setOnClickListener(this);//返回
        numBerPicker.setOnNumChangeListener(this);//选择数量
        numBerPicker.setOnLoadListener(this);
        llAddress.setOnClickListener(this);//选择收货地址
        btnSave.setOnClickListener(this);//提交订单
    }

    /**
     * 显示默认地址
     */
    private void showDefault(ShipAddressBean.Data data) {
        if (data != null) {
            ShipAddressBean.Address address = data.getAddress();
            tvOrderName.setText("收货人：" + address.getContactUserName() + "");
            tvOrderPhone.setText(address.getContactTel());
            tvOrderStreet.setText("地址地址：" + address.getProvince() + address.getCity() + address.getArea() + address.getStreet());
        }
    }

    /**
     * 显示默认地址
     */
    private void showDefault1(OneUserAddress.Data data) {
        if (data != null) {
            OneUserAddress.Address address = data.getAddress();
            tvOrderName.setText("收货人：" + address.getContactUserName() + "");
            tvOrderPhone.setText(address.getContactTel());
            tvOrderStreet.setText("地址地址：" + address.getProvince() + address.getCity() + address.getArea() + address.getStreet());
        }
    }

    //退出账号
    private void setDefaultAddress() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(mActivity);
        normalDialog.setCancelable(false);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("您还没有设置收货地址，请点击这里设置!");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        startActivityForResult(new Intent(mActivity, AddShipAddressActivity.class).putExtra("flag", true), 1);
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        finish();
                    }
                });
        // 显示
        normalDialog.show();
    }

    private String getJson() {
        AddOrderBean bean = new AddOrderBean();
        AddOrderBean.User user = bean.new User();
        AddOrderBean.Items items = bean.new Items();
        user.setId(Integer.parseInt(MyApplication.userId));
        bean.setUser(user);
        bean.setAddrld(addId);
        items.setProductId(productId);
        items.setQty(count);
        items.setPrice(Integer.parseInt(ticket.getPrice().split("\\.")[0]));
        List<AddOrderBean.Items> item = new ArrayList<>();
        item.add(items);
        bean.setItems(item);
        return new Gson().toJson(bean);
    }


    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.iv_write_order_back:
                finish();
                break;
            //选择收货地址
            case R.id.ll_order_address:
                startActivityForResult(new Intent(mActivity, ShipAddressActivity.class), 1);
                break;
            case R.id.btn_order_save:
                orderModel = new OrderModel();
                orderModel.saveOrder(handler, getJson());
                startLoading("正在提交订单...");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == 4) {//选择收货地址
            String orderId = data.getStringExtra("orderId");
            model.findOneUserAddress(handler, orderId);
        }
        if (requestCode == 1 && resultCode == 5) {//没有设置收货地址，跳转到收货地址界面，用户没有设置，直接返回
            finish();
        }
        if (requestCode == 1 && resultCode == 6) {//进入到地址选择界面，用户没有操作，直接点击返回
            model.findUserAddress(handler, userId);
        }
    }

    //选择数量接口
    @Override
    public void onNumChange(NumberPicker numberPicker) {
        stopLoading();
        tvTotal.setText("￥" + numberPicker.getTotalCount() + "");
        total = numberPicker.getTotalCount();
        count = numberPicker.getValue();
    }

    //选择数量接口
    @Override
    public void loading() {
        startLoading("正在加载中");
    }
}
