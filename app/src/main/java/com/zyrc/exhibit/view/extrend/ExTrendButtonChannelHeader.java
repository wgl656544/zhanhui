package com.zyrc.exhibit.view.extrend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyrc.exhibit.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

public class ExTrendButtonChannelHeader implements View.OnClickListener {
    @ViewInject(R.id.btn_ex_trends_zh)
    private Button btnZH;
    @ViewInject(R.id.btn_ex_trends_info)
    private Button btnInfo;
    @ViewInject(R.id.btn_ex_trends_ticket)
    private Button btnTicket;
    @ViewInject(R.id.btn_ex_trends_groupbuy)
    private Button btnGb;

    private Context context;

    private Button[] buttons = new Button[4];
    private int clickIndex = 0;
    private int currentIndex = 0;

    public ExTrendButtonChannelHeader(Context context) {
        this.context = context;
    }

    public void getView(BaseQuickAdapter adapter, RecyclerView recyclerView) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ex_trends_button_channel, (ViewGroup) recyclerView.getParent(), false);
        x.view().inject(this, view);
        adapter.addHeaderView(view);
        initview();
        setListeners();
    }

    private void initview() {
        buttons[0] = btnZH;
        buttons[1] = btnInfo;
        buttons[2] = btnTicket;
        buttons[3] = btnGb;
        btnZH.setSelected(true);
    }

    private void setListeners() {
        btnZH.setOnClickListener(this);
        btnInfo.setOnClickListener(this);
        btnTicket.setOnClickListener(this);
        btnGb.setOnClickListener(this);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ex_trends_zh:
                clickIndex = 0;
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(clickIndex);
                }
                break;

            case R.id.btn_ex_trends_info:
                clickIndex = 1;
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(clickIndex);
                }
                break;

            case R.id.btn_ex_trends_ticket:
                clickIndex = 2;
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(clickIndex);
                }
                break;

            case R.id.btn_ex_trends_groupbuy:
                clickIndex = 3;
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(clickIndex);
                }
                break;
        }
        if (currentIndex != clickIndex) {
            buttons[clickIndex].setSelected(true);
            buttons[currentIndex].setSelected(false);
            currentIndex = clickIndex;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

}
