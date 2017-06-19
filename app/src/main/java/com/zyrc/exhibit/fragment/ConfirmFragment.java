package com.zyrc.exhibit.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mylibrary.base.BaseFragment;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.MyApplyAdapter;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.ApplyBean;
import com.zyrc.exhibit.model.UserModel;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/1 0001.
 */

public class ConfirmFragment extends BaseFragment {
    @ViewInject(R.id.rl_all_order)
    private RecyclerView rlAllOrder;
    private List<ApplyBean.Data> datas;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            stopLoading();
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    ApplyBean applyBean = (ApplyBean) msg.obj;
                    showOrder(applyBean);
                    break;
                case HandlerConstant.REQUEST_FAIL:
                    break;
            }
        }
    };

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        x.view().inject(this, view);
        String id = MyApplication.userId;
        UserModel model = new UserModel();
        startLoading("正在加载中...");
        model.searchMyApply(handler, UrlConstant.HTTP_URL_SEARCH_APPLY, "?userId=" + id);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_one;
    }

    @Override
    public void initData() {

    }

    private void showOrder(ApplyBean data) {
        datas = new ArrayList<>();
        for(ApplyBean.Data item : data.getData()){
            if(item.getStatus() == 1){
                datas.add(item);
            }
        }
        rlAllOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlAllOrder.setAdapter(new MyApplyAdapter(getActivity(), datas, 1));
    }
}
