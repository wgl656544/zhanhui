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
import com.zyrc.exhibit.entity.ApplyBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class MyApplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ApplyBean.Data> data;
    private int status;

    public MyApplyAdapter(Context context, List<ApplyBean.Data> data, int status) {
        this.context = context;
        this.data = data;
        this.status = status;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (data.size() == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.item_no_data_layout, parent, false);
            return new MyApplyAdapter.MyViewHolderNoData(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_my_apply_list, parent, false);
            return new MyApplyAdapter.MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            ApplyBean.Data item = data.get(position);
            if (item.getCorp() != null) {
                Glide.with(context).load(item.getCorp().getImageUrl()).
                        error(R.drawable.error).
                        into(((MyViewHolder) holder).ivPic);
                if (status == 0) {
                    ((MyViewHolder) holder).tvStatus.setText("未确定");
                } else {
                    ((MyViewHolder) holder).tvStatus.setText("已确定");
                }
                ((MyViewHolder) holder).tvName.setText(item.getCorp().getName());
                ((MyViewHolder) holder).tvStartDate.setText("开始时间");
//                ((MyViewHolder) holder).tvPlace.setText(item.getCorp().getAddress().getCity());
                ((MyViewHolder) holder).tvCreateDate.setText("报名时间：" + item.getCorp().getCreatedDate() + "");
            }
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size() == 0 ? 1 : data.size();
        }
        return 0;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPic;
        private TextView tvCreateDate;
        private TextView tvStatus;
        private TextView tvName;
        private TextView tvStartDate;
        private TextView tvPlace;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_my_apply);
            tvCreateDate = (TextView) itemView.findViewById(R.id.tv_my_apply_create_date);
            tvStatus = (TextView) itemView.findViewById(R.id.tv_my_apply_status);
            tvName = (TextView) itemView.findViewById(R.id.tv_my_apply_name);
            tvStartDate = (TextView) itemView.findViewById(R.id.tv_my_apply_start_date);
            tvPlace = (TextView) itemView.findViewById(R.id.tv_my_apply_place);
        }
    }

    private class MyViewHolderNoData extends RecyclerView.ViewHolder {

        public MyViewHolderNoData(View itemView) {
            super(itemView);
        }
    }
}
