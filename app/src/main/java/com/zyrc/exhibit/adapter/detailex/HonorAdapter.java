package com.zyrc.exhibit.adapter.detailex;

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
 * Created by Administrator on 2017/3/17 0017.
 */

public class HonorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CommonBean.Data> datas;

    public HonorAdapter(Context context, List<CommonBean.Data> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_ex_honor_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            Glide.with(context).load(datas.get(position).getImageUrl()).into(((MyViewHolder) holder).imageView);
            ((MyViewHolder) holder).tvName.setText(datas.get(position).getName());
            ((MyViewHolder) holder).tvTitle.setText(datas.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tvName;
        private TextView tvTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_detail_ex_honor_list);
            tvName = (TextView) itemView.findViewById(R.id.tv_detail_ex_honor_list_name);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_detail_ex_honor_list_title);
        }
    }
}
