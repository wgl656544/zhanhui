package com.zyrc.exhibit.view.extrend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyrc.exhibit.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 展会动态分类按钮view
 * Created by Administrator on 2017/5/28 0028.
 */

public class ExTrendChannelHeader implements View.OnClickListener {
    @ViewInject(R.id.ll_ex_trends_channel_zh)
    private LinearLayout llZh;
    @ViewInject(R.id.ll_ex_trends_channel_info)
    private LinearLayout llInfo;
    @ViewInject(R.id.ll_ex_trends_channel_ticket)
    private LinearLayout llTicket;
    @ViewInject(R.id.ll_ex_trends_channel_gb)
    private LinearLayout llGb;
    private Context context;

    public ExTrendChannelHeader(Context context) {
        this.context = context;
    }

    public void getView(BaseQuickAdapter adapter, RecyclerView recyclerView) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ex_trends_channel, (ViewGroup) recyclerView.getParent(), false);
        x.view().inject(this, view);
        adapter.addHeaderView(view);
        setListeners();
    }

    private void setListeners() {
        llZh.setOnClickListener(this);
        llInfo.setOnClickListener(this);
        llTicket.setOnClickListener(this);
        llGb.setOnClickListener(this);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_ex_trends_channel_zh:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(0);
                }
                break;

            case R.id.ll_ex_trends_channel_info:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(1);
                }
                break;

            case R.id.ll_ex_trends_channel_ticket:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(2);
                }
                break;

            case R.id.ll_ex_trends_channel_gb:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(3);
                }
                break;
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
