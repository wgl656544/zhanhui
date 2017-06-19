package com.zyrc.exhibit.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.mylibrary.base.BaseActivity;
import com.google.gson.Gson;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.detailex.DetailPlanAdapter;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.model.Model;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class DetailExPlanActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_detail_ex_plan_back)
    private ImageView ivBack;
    @ViewInject(R.id.rl_detail_ex_plan)
    private RecyclerView rlPlan;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            stopLoading();
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    String data = (String) msg.obj;
                    CommonBean mCommonBean = new Gson().fromJson(data, CommonBean.class);
                    showPlan(mCommonBean.getData());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ex_plan);
        x.view().inject(this);
        setListeners();
        Model model = new Model();
        int entityId = getIntent().getIntExtra("entityId", 1);
        String param = "?page=1&itemsPerPage=100&exhibId=" + entityId;
        startLoading("加载中...");
        model.getData(handler, UrlConstant.HTTP_URL_DETAIL_EX_PLAN, HandlerConstant.SEARCH_SUCCESS, param);
    }

    private void setListeners() {
        ivBack.setOnClickListener(this);
    }

    private void showPlan(List<CommonBean.Data> datas) {
        rlPlan.setLayoutManager(new LinearLayoutManager(this));
        rlPlan.setAdapter(new DetailPlanAdapter(this, datas));

    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_detail_ex_plan_back:
                finish();
                break;
        }
    }
}
