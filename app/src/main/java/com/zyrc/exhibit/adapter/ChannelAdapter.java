package com.zyrc.exhibit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.entity.ChannelBean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/26 0026.
 */

public class ChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ChannelBean.Data> datas;

    public ChannelAdapter(Context context, List<ChannelBean.Data> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_confence_channel, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (datas.get(position).getPageTag()) {
            case "active"://议程
                Glide.with(context).load(datas.get(position).getImageUrl()).error(R.drawable.activityplan).into(((MyViewHolder) holder).imageView);
                break;
            case "content"://展会信息
                Glide.with(context).load(datas.get(position).getImageUrl()).error(R.drawable.exinfo).into(((MyViewHolder) holder).imageView);
                break;
            case "guest"://嘉宾
                Glide.with(context).load(datas.get(position).getImageUrl()).error(R.drawable.exguest).into(((MyViewHolder) holder).imageView);
                break;
            case "branch"://分论坛
                Glide.with(context).load(datas.get(position).getImageUrl()).error(R.drawable.exbranch).into(((MyViewHolder) holder).imageView);
                break;
            case "ticket"://票务
                Glide.with(context).load(datas.get(position).getImageUrl()).error(R.drawable.exbutticket).into(((MyViewHolder) holder).imageView);
                break;
            case "position"://场馆
                Glide.with(context).load(datas.get(position).getImageUrl()).error(R.drawable.exhibitionforum).into(((MyViewHolder) holder).imageView);
                break;
            case "product"://团购
                Glide.with(context).load(datas.get(position).getImageUrl()).error(R.drawable.expo_team).into(((MyViewHolder) holder).imageView);
                break;
            case "live"://资讯
                Glide.with(context).load(datas.get(position).getImageUrl()).error(R.drawable.exnews).into(((MyViewHolder) holder).imageView);
                break;
            case "company"://参展商
                Glide.with(context).load(datas.get(position).getImageUrl()).error(R.drawable.exhibitor).into(((MyViewHolder) holder).imageView);
                break;
            case "invite"://招商
                Glide.with(context).load(datas.get(position).getImageUrl()).error(R.drawable.exbusiness).into(((MyViewHolder) holder).imageView);
                break;
            case "vote"://投票
                Glide.with(context).load(datas.get(position).getImageUrl()).error(R.drawable.exvote).into(((MyViewHolder) holder).imageView);
                break;
        }
        ((MyViewHolder) holder).textView.setText(datas.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(datas.get(position).getPageTag());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private LinearLayout ll_confence;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_confence_channel);
            textView = (TextView) itemView.findViewById(R.id.tv_confence_channel);
            ll_confence = (LinearLayout) itemView.findViewById(R.id.ll_confence_channel);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String type);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
