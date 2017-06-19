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
import com.zyrc.exhibit.adapter.MyExAdapter;
import com.zyrc.exhibit.adapter.MyExhibiAdapter;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.entity.MyExBean;
import com.zyrc.exhibit.model.Model;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的展会页面
 * Created by Administrator on 2017/5/6 0006.
 */

public class MyExhibiActivity extends BaseActivity {
    @ViewInject(R.id.rl_my_exhibi)
    private RecyclerView rlMyExhibi;
    @ViewInject(R.id.iv_my_exhibi_back)
    private ImageView ibBack;
    private Model model;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    String json = (String) msg.obj;
                    MyExBean bean = new Gson().fromJson(json,MyExBean.class);
                    showExhibi(bean.getData());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exhibi);
        x.view().inject(this);
        model = new Model();
        String param = "?userId=" + MyApplication.userId;
        model.getData(handler, UrlConstant.API_GET_MY_EXHIBIT, HandlerConstant.SEARCH_SUCCESS, param);
        setListener();
    }

    //设置监听器
    private void setListener() {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showExhibi(List<MyExBean.Data> datas) {
        rlMyExhibi.setLayoutManager(new LinearLayoutManager(this));
        rlMyExhibi.setAdapter(new MyExAdapter(R.layout.item_my_ex_list, datas));
    }
}
