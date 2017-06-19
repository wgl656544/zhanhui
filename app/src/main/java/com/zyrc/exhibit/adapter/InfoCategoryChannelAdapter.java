package com.zyrc.exhibit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.entity.InfoCategoryBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public class InfoCategoryChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<InfoCategoryBean.Data> datas;

    public InfoCategoryChannelAdapter(Context context, List<InfoCategoryBean.Data> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(context).inflate(R.layout.item_home_fragment_info_channel, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            }
        });
        Glide.with(context).load(datas.get(position).getImageUrl()).into(((MyViewHolder) holder).imageView);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item_info_channel);
        }
    }
}
