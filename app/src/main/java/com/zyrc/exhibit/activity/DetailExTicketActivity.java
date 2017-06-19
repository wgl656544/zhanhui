package com.zyrc.exhibit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.mylibrary.base.BaseActivity;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.detailex.TicketAdapter;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.model.GetDataModel;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21 0021.
 */

public class DetailExTicketActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_detail_ex_ticket_back)
    private ImageView ivBack;
    @ViewInject(R.id.lv_detail_ex_ticket)
    private ListView lvTicket;

    private Activity mActivity;

    private GetDataModel model = new GetDataModel();
    private TicketAdapter adapter;
    private CommonBean detailExTicketBean;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            stopLoading();
            if (msg.what == HandlerConstant.SEARCH_SUCCESS) {
                detailExTicketBean = (CommonBean) msg.obj;
                if (detailExTicketBean != null) {
                    showTicket(detailExTicketBean);
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ex_ticket);
        x.view().inject(this);
        mActivity = this;
        setListeners();
        int exhibId = getIntent().getIntExtra("entityId", 1);
        model.search(handler, UrlConstant.HTTP_URL_DETAIL_EX_TICKET, "exhibId=" + exhibId);
        startLoading("加载中...");
    }

    //设置监听器
    private void setListeners() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_detail_ex_ticket_back:
                finish();
                break;
        }
    }

    //显示门票
    private void showTicket(CommonBean bean) {
        final List<CommonBean.Data> datas = bean.getData();
        adapter = new TicketAdapter(this, datas);
        lvTicket.setAdapter(adapter);
        lvTicket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mActivity, DetailBuyActivity.class).putExtra(HandlerConstant.DETAIL_BUY, datas.get(position)));
            }
        });
    }
}
