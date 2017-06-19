package com.zyrc.exhibit.adapter.detailex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.entity.CommonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/17 0017.
 */

public class DetailTicketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<CommonBean.Data> datas;

    public DetailTicketAdapter(Context context, List<CommonBean.Data> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_ex_ticket, parent, false);
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
            Glide.with(context).load(datas.get(position).getImageUrl()).into(((MyViewHolder) holder).imageView);
            ((MyViewHolder) holder).tvName.setText(datas.get(position).getName());
            ((MyViewHolder) holder).tvTitle.setText(datas.get(position).getStartDate()+"至"+datas.get(position).getEndDate());
            ((MyViewHolder) holder).tvPrice.setText(datas.get(position).getPrice());
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
        private TextView tvPrice;
        private Button btnBuy;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_detail_ex_ticket);
            tvName = (TextView) itemView.findViewById(R.id.tv_detail_ex_ticket_name);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_detail_ex_ticket_title);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_detail_ex_ticket_price);
            btnBuy = (Button) itemView.findViewById(R.id.btn_detail_ex_ticket);
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
