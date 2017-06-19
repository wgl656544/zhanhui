package com.zyrc.exhibit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.entity.MsgBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class MyMsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MsgBean.Data> datas;

    public MyMsgAdapter(Context context, List<MsgBean.Data> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (datas.size() == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.item_no_data_layout, parent, false);
            return new MyMsgAdapter.MyViewHolderNoData(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_my_msg_list, parent, false);
            return new MyMsgAdapter.MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MsgBean.Data data = datas.get(position);
            Glide.with(context).load(data.getImageUrl()).error(R.drawable.error).into(((MyViewHolder) holder).ivPic);
            ((MyViewHolder) holder).tvTitle.setText(data.getName()+position);
            ((MyViewHolder) holder).tvBody.setText(data.getDescription());
            ((MyViewHolder) holder).tvDate.setText(data.getCreatedDate());
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() == 0 ? 1 : datas.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPic;
        private TextView tvTitle;
        private TextView tvBody;
        private TextView tvDate;

        private MyViewHolder(View itemView) {
            super(itemView);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_my_msg_pic);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_my_msg_title);
            tvBody = (TextView) itemView.findViewById(R.id.tv_my_msg_body);
            tvDate = (TextView) itemView.findViewById(R.id.tv_my_msg_date);
        }
    }

    private class MyViewHolderNoData extends RecyclerView.ViewHolder {

        private MyViewHolderNoData(View itemView) {
            super(itemView);
        }
    }
}
