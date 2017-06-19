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
import com.zyrc.exhibit.entity.CommonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/6 0006.
 */

public class MyExhibiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<CommonBean.Data> data;

    public MyExhibiAdapter(Context context, List<CommonBean.Data> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (data.size() == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.item_no_data_layout, parent, false);
            return new MyExhibiAdapter.MyViewHolderNoData(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_find_exhibition_list, parent, false);
            return new MyExhibiAdapter.MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            CommonBean.Data item = data.get(position);
            Glide.with(context).load("").placeholder(R.drawable.default_pic).error(R.drawable.error).into(((MyViewHolder) holder).imageView);
            ((MyViewHolder) holder).tvName.setText(item.getName());
            ((MyViewHolder) holder).tvDate.setText(item.getStartDate() + "-" + item.getEndDate());
            ((MyViewHolder) holder).tvCity.setText(item.getCity());
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
        private ImageView imageView;
        private TextView tvName;
        private TextView tvDate;
        private TextView tvCity;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_find_exhibition_pic);
            tvName = (TextView) itemView.findViewById(R.id.tv_find_exhibition_name);
            tvDate = (TextView) itemView.findViewById(R.id.tv_find_exhibition_date);
            tvCity = (TextView) itemView.findViewById(R.id.tv_find_exhibition_city);
        }
    }

    private class MyViewHolderNoData extends RecyclerView.ViewHolder {

        public MyViewHolderNoData(View itemView) {
            super(itemView);
        }
    }

}
