package com.zyrc.exhibit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.entity.CommonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class HomeTicketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CommonBean.Data> mDataList;

    public HomeTicketAdapter(Context context, List<CommonBean.Data> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_pic_three, parent, false);
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
        Glide.with(context).load(mDataList.get(position).getImageUrl()).error(R.drawable.default_pic).into(((MyViewHolder) holder).imageView);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_home_item_three);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
