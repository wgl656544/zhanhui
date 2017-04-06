package com.ex.administrator.zhanhui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.entity.HomePageBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class HomeTicketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<HomePageBean.DataList> mDataList;

    public HomeTicketAdapter(Context context, List<HomePageBean.DataList> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(context).inflate(R.layout.item_home_pic_three, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(context).load(mDataList.get(position).getImageUrl()).into(((MyViewHolder) holder).imageView);
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
}
