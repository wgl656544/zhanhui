package com.zyrc.exhibit.fragment.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyrc.exhibit.R;
import com.zyrc.exhibit.activity.PayOrderActivity;
import com.zyrc.exhibit.adapter.AllOrderAdapter;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.order.OrderBean;
import com.zyrc.exhibit.model.OrderModel;
import com.zyrc.exhibit.util.OrderDataHealper;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/4/1 0001.
 */

public class TwoOrderFragment extends Fragment {
    @ViewInject(R.id.rl_not_pay_order)
    private RecyclerView rlNotPay;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    OrderBean orderBean = (OrderBean) msg.obj;
                    List<OrderBean.Data> itemses = orderBean.getData();
                    showOrder(OrderDataHealper.getPayNotData(itemses));
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_two, null);
        x.view().inject(this, view);
        OrderModel model = new OrderModel();
        String id = MyApplication.userId;
        model.getAllOrder(handler, UrlConstant.HTTP_URL_FIND_ORDER, "?user.id=" + id);
        return view;
    }

    private void showOrder(List<Object> datas) {
        rlNotPay.setLayoutManager(new LinearLayoutManager(getActivity()));
        AllOrderAdapter adapter = new AllOrderAdapter(getActivity(),datas);
        rlNotPay.setAdapter(adapter);
        adapter.setOnItemClickListener(new AllOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String orderId, String total) {
                startActivity(new Intent(getActivity(), PayOrderActivity.class).putExtra("orderId",orderId).putExtra("total",total));
            }
        });
    }
}
