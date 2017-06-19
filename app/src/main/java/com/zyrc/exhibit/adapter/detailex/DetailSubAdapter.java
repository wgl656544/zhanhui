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

public class DetailSubAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CommonBean.Data> datas;

    public DetailSubAdapter(Context context,List<CommonBean.Data> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_ex_sub, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(holder.itemView, position);
                    }
                }
            });
            Glide.with(context).load(datas.get(position).getImageUrl()).error(R.drawable.error).into(((MyViewHolder) holder).imageView);
            ((MyViewHolder) holder).tvTitle.setText(datas.get(position).getName());
            ((MyViewHolder) holder).tvDate.setText(datas.get(position).getStartDate()+" - "+datas.get(position).getEndDate());
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tvTitle;
        private TextView tvDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_detail_ex_sub);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_detail_ex_sub_title);
            tvDate = (TextView) itemView.findViewById(R.id.tv_detail_ex_sub_date);
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
