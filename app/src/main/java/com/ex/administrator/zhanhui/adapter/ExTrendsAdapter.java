package com.ex.administrator.zhanhui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ex.administrator.zhanhui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/22 0022.
 */

public class ExTrendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int TYPE_HEADER_ONE = 0;
    private static final int TYPE_HEADER_TWO = 1;
    private static final int TYPE_HEADER_THREE = 2;
    private static final int TYPE_NORMAL = 3;


    private Context context;
    private List<String> datas;
    private RecyclerView.ViewHolder viewHolder;
    private List<Button> buttons;
    private int btnClickIndex;
    private int btnCurrentIndex = 0;

    public ExTrendsAdapter(List<String> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == TYPE_HEADER_ONE) {
            view = LayoutInflater.from(context).inflate(R.layout.item_ex_trends_channel, parent, false);
            return new ChannelViewHolder(view);
        } else if (viewType == TYPE_HEADER_TWO) {
            view = LayoutInflater.from(context).inflate(R.layout.item_ex_trends_advert, parent, false);
            return new MyViewHolder(view);
        } else if (viewType == TYPE_HEADER_THREE) {
            view = LayoutInflater.from(context).inflate(R.layout.item_ex_trends_button_channel, parent, false);
            return new ButtonViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_ex_trend_normal, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            this.viewHolder = holder;
            ((ChannelViewHolder) holder).llExTrendsZh.setOnClickListener(this);
            ((ChannelViewHolder) holder).llExTrendsInfo.setOnClickListener(this);
            ((ChannelViewHolder) holder).llExTrendsTicket.setOnClickListener(this);
            ((ChannelViewHolder) holder).llExTrendsGb.setOnClickListener(this);
        } else if (position == 2) {
            this.viewHolder = holder;
            ((ButtonViewHolder) holder).btnExTrendsZh.setOnClickListener(this);
            ((ButtonViewHolder) holder).btnExTrendsInfo.setOnClickListener(this);
            ((ButtonViewHolder) holder).btnExTrendsTicket.setOnClickListener(this);
            ((ButtonViewHolder) holder).btnExTrendsGroupBuy.setOnClickListener(this);
        }
        if (position > 2) {
            ((MyViewHolder) holder).textView.setText(datas.get(position - 3));
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER_ONE;
        } else if (position == 1) {
            return TYPE_HEADER_TWO;
        } else if (position == 2) {
            return TYPE_HEADER_THREE;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_ex_trends_channel_zh:
                Toast.makeText(context, "你点了我1", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_ex_trends_channel_info:
                Toast.makeText(context, "你点了我2", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_ex_trends_channel_ticket:
                Toast.makeText(context, "你点了我3", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ll_ex_trends_channel_gb:
                Toast.makeText(context, "你点了我4", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_ex_trends_zh:
                btnClickIndex = 0;
                break;

            case R.id.btn_ex_trends_info:
                btnClickIndex = 1;
                break;

            case R.id.btn_ex_trends_ticket:
                btnClickIndex = 2;
                break;

            case R.id.btn_ex_trends_groupbuy:
                btnClickIndex = 3;
                break;

        }
        if(btnClickIndex != btnCurrentIndex){
            buttons.get(btnClickIndex).setBackgroundResource(R.drawable.bg_black);
            buttons.get(btnCurrentIndex).setBackgroundColor(ContextCompat.getColor(context,R.color.color_white_1));
            buttons.get(btnClickIndex).setTextColor(ContextCompat.getColor(context,R.color.color_button));
            buttons.get(btnCurrentIndex).setTextColor(ContextCompat.getColor(context,R.color.black));
            btnCurrentIndex = btnClickIndex;
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;


        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_normal);

        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llExTrendsZh;
        private LinearLayout llExTrendsInfo;
        private LinearLayout llExTrendsTicket;
        private LinearLayout llExTrendsGb;

        public ChannelViewHolder(View itemView) {
            super(itemView);
            llExTrendsZh = (LinearLayout) itemView.findViewById(R.id.ll_ex_trends_channel_zh);
            llExTrendsInfo = (LinearLayout) itemView.findViewById(R.id.ll_ex_trends_channel_info);
            llExTrendsTicket = (LinearLayout) itemView.findViewById(R.id.ll_ex_trends_channel_ticket);
            llExTrendsGb = (LinearLayout) itemView.findViewById(R.id.ll_ex_trends_channel_gb);
        }
    }

    class ButtonViewHolder extends RecyclerView.ViewHolder {
        private Button btnExTrendsZh;
        private Button btnExTrendsInfo;
        private Button btnExTrendsTicket;
        private Button btnExTrendsGroupBuy;

        public ButtonViewHolder(View itemView) {
            super(itemView);
            btnExTrendsZh = (Button) itemView.findViewById(R.id.btn_ex_trends_zh);
            btnExTrendsInfo = (Button) itemView.findViewById(R.id.btn_ex_trends_info);
            btnExTrendsTicket = (Button) itemView.findViewById(R.id.btn_ex_trends_ticket);
            btnExTrendsGroupBuy = (Button) itemView.findViewById(R.id.btn_ex_trends_groupbuy);
            buttons = new ArrayList<>();
            buttons.add(btnExTrendsZh);
            buttons.add(btnExTrendsInfo);
            buttons.add(btnExTrendsTicket);
            buttons.add(btnExTrendsGroupBuy);
        }
    }

}
