package com.zyrc.exhibit.adapter.detailex;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.activity.MainActivity;
import com.zyrc.exhibit.entity.CommonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class DetailPlanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CommonBean.Data> datas;

    public DetailPlanAdapter(Context context, List<CommonBean.Data> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_plan_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            CommonBean.Data data = datas.get(position);
            ((MyViewHolder) holder).tvDate.setText(data.getStartDate());
            ((MyViewHolder) holder).tvTime.setText(data.getTimeBettwen());
            ((MyViewHolder) holder).tvTitle.setText(data.getName());
            ((MyViewHolder) holder).tvContent.setText(data.getDescription());
            if (position == 0) {
                Glide.with(context).load(data.getImageUrl()).into(((MyViewHolder) holder).ivPic);
            } else {
                ((MyViewHolder) holder).ivPic.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private TextView tvTime;
        private TextView tvTitle;
        private TextView tvContent;
        private ImageView ivPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tv_plan_date);
            tvTime = (TextView) itemView.findViewById(R.id.tv_plan_time);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_plan_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_plan_content);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_plan_pic);
        }
    }

}
