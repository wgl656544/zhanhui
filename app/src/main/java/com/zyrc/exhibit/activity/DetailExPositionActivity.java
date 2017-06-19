package com.zyrc.exhibit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.mylibrary.base.BaseActivity;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.detailex.DetailHonorAdapter;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.model.GetDataModel;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 会议主页跳转的 嘉宾列表页面
 * Created by Administrator on 2017/2/21 0021.
 */

public class DetailExPositionActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_detail_ex_position_back)
    private ImageView ivBack;
    @ViewInject(R.id.rl_detail_ex_position)
    private RecyclerView rlHonor;

    private Activity mActivity;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            stopLoading();
            if (msg.what == HandlerConstant.SEARCH_SUCCESS) {
                CommonBean detailExCommonBean = (CommonBean) msg.obj;
                if (detailExCommonBean != null) {
                    shoeHonor(detailExCommonBean);
                }
            }
        }
    };
    private GetDataModel model = new GetDataModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ex_position);
        x.view().inject(this);
        mActivity = this;
        seListeners();
        int exhibId = getIntent().getIntExtra("entityId", 1);
        model.search(handler, UrlConstant.HTTP_URL_DETAIL_EX_POSITION, "?exhibId=" + exhibId);
        startLoading("加载中...");
    }

    //设置监听器
    private void seListeners() {
        ivBack.setOnClickListener(this);
    }


    //显示场馆
    private void shoeHonor(CommonBean bean) {
        final List<CommonBean.Data> datas = bean.getData();
        DetailHonorAdapter adapter = new DetailHonorAdapter(this, datas);
        rlHonor.setLayoutManager(new GridLayoutManager(this, 2));
        rlHonor.setAdapter(adapter);
        adapter.setOnItemClickListener(new DetailHonorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(mActivity, IntroduceActivity.class).putExtra("url", datas.get(position).getLinkUrl()));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.iv_detail_ex_position_back:
                finish();
                break;
        }
    }
}
