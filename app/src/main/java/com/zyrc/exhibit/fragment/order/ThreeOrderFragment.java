package com.zyrc.exhibit.fragment.order;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyrc.exhibit.R;
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

public class ThreeOrderFragment extends Fragment {
    @ViewInject(R.id.rl_ok_pay_order)
    private RecyclerView rlPayOk;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    OrderBean orderBean = (OrderBean) msg.obj;
                    List<OrderBean.Data> itemses = orderBean.getData();
                    showOrder(OrderDataHealper.getPayOkData(itemses));
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_three, null);
        x.view().inject(this, view);
        OrderModel model = new OrderModel();
        String id = MyApplication.userId;
        model.getAllOrder(handler, UrlConstant.HTTP_URL_FIND_ORDER, "?user.id=" + id);
        return view;
    }

    private void showOrder(List<Object> datas) {
        rlPayOk.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlPayOk.setAdapter(new AllOrderAdapter(getActivity(), datas));
    }
}
