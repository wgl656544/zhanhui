package com.ex.administrator.zhanhui.adapter.detailex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.entity.CommonListBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/17 0017.
 */

public class DetailBlogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CommonListBean.DataList> datas;

    public DetailBlogAdapter(Context context, List<CommonListBean.DataList> datas) {
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            Glide.with(context).load(datas.get(position).getImageUrl()).into(((MyViewHolder) holder).imageView);
            ((MyViewHolder) holder).tvTitle.setText(datas.get(position).getName());
            ((MyViewHolder) holder).tvContent.setText(datas.get(position).getTitle());
            ((MyViewHolder) holder).tvDate.setText(datas.get(position).getStartDate());
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_detail_ex_blog);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_detail_ex_blog_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_detail_ex_blog_content);
            tvDate = (TextView) itemView.findViewById(R.id.tv_detail_ex_blog_date);
        }
    }

}
