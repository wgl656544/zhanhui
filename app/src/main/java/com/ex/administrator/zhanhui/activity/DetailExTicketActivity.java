package com.ex.administrator.zhanhui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.adapter.detailex.TicketAdapter;
import com.ex.administrator.zhanhui.constant.HandlerConstant;
import com.ex.administrator.zhanhui.entity.DetailExCommonBean;
import com.ex.administrator.zhanhui.model.DetailExModel;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21 0021.
 */

public class DetailExTicketActivity extends AppCompatActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_detail_ex_ticket_back)
    private ImageView ivBack;
    @ViewInject(R.id.lv_detail_ex_ticket)
    private ListView lvTicket;

    private DetailExModel model = new DetailExModel();
    private TicketAdapter adapter;
    private DetailExCommonBean detailExTicketBean;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HandlerConstant.DETAIL_EX_TICKET_SUCCESS) {
                detailExTicketBean = (DetailExCommonBean) msg.obj;
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
        setListeners();
        model.getDetailExTicket(handler, 1);
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
    private void showTicket(DetailExCommonBean bean) {
        List<DetailExCommonBean.Data> datas;
        datas = bean.getData();
        adapter = new TicketAdapter(this, datas);
        lvTicket.setAdapter(adapter);
    }
}
