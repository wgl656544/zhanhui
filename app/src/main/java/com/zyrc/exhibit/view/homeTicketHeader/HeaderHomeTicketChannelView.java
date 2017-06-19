package com.zyrc.exhibit.view.homeTicketHeader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.zyrc.exhibit.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderHomeTicketChannelView implements View.OnClickListener {
    @ViewInject(R.id.ll_home_fragment_ticket_1)
    private LinearLayout llTicket1;
    @ViewInject(R.id.ll_home_fragment_ticket_2)
    private LinearLayout llTicket2;

    private Context context;

    public HeaderHomeTicketChannelView(Activity context) {
        this.context = context;
    }

    public void getView(ListView listView) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_home_fragment_ticket_channel, listView, false);
        x.view().inject(this, view);
        listView.addHeaderView(view);
        setListeners();
    }

    //设置监听器
    private void setListeners() {
        llTicket1.setOnClickListener(this);
        llTicket2.setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home_fragment_ticket_1:
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(1);
                }
                break;
            case R.id.ll_home_fragment_ticket_2:
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(2);
                }
                break;
        }
    }

    private OnClickListener mOnClickListener;

    public interface OnClickListener {
        void onClick(int position);
    }

    public void setOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }
}
