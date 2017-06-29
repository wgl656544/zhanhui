package com.zyrc.exhibit.adapter.detailex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.entity.CommonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/17 0017.
 */

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private List<CommonBean.Data> datas;
    private LayoutInflater layoutInflater;

    public NewsAdapter(Context context, List<CommonBean.Data> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CommonBean.Data getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_detail_ex_news_list, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_detail_ex_news_list);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_detail_ex_news_list_name);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_detail_ex_news_list_title);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_detail_ex_news_list_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CommonBean.Data item = datas.get(position);
        Glide.with(context).load(item.getImageUrl()).fitCenter().into(holder.imageView);
        holder.tvName.setText(datas.get(position).getName());
        holder.tvTitle.setText(datas.get(position).getTitle());
        holder.tvDate.setText(datas.get(position).getStartDate());
        return convertView;
    }

    class ViewHolder {
        private ImageView imageView;
        private TextView tvName;
        private TextView tvTitle;
        private TextView tvDate;
    }

}
