package com.ex.administrator.zhanhui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.entity.TeamTypeBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class TeamChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<TeamTypeBean.Data> datas;

    public TeamChannelAdapter(Context context, List<TeamTypeBean.Data> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_fragment_info_channel, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(context).load(datas.get(position).getImageUrl()).error(R.drawable.default_pic).into(((MyViewHolder) holder).imageView);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_info_channel);
        }
    }
}
