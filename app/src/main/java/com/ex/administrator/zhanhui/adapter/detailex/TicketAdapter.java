package com.ex.administrator.zhanhui.adapter.detailex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.entity.CommonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/17 0017.
 */

public class TicketAdapter extends BaseAdapter {
    private Context context;
    private List<CommonBean.Data> datas;
    private LayoutInflater layoutInflater;

    public TicketAdapter(Context context, List<CommonBean.Data> datas) {
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
            convertView = layoutInflater.inflate(R.layout.item_detail_ex_ticket_list, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_detail_ex_ticket_list);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_detail_ex_ticket_list_title);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_detail_ex_ticket_list_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CommonBean.Data item = datas.get(position);
        Glide.with(context).load(item.getImageUrl()).into(holder.imageView);
        holder.tvTitle.setText(datas.get(position).getTitle());
        holder.tvPrice.setText("注册门票：" + datas.get(position).getPrice() + "/人");

        return convertView;
    }

    class ViewHolder {
        private ImageView imageView;
        private TextView tvTitle;
        private TextView tvPrice;
    }

}
