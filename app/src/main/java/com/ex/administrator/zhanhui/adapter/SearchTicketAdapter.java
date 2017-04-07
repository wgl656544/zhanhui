package com.ex.administrator.zhanhui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.entity.CommonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class SearchTicketAdapter extends BaseAdapter {
    private Context context;
    private List<CommonBean.Data> datas;
    private LayoutInflater inflater;

    public SearchTicketAdapter(Context context, List<CommonBean.Data> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_home_fragment_ticket_list, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_home_fragment_ticket_pic);
            viewHolder.tvTicketTitle = (TextView) convertView.findViewById(R.id.tv_home_fragment_ticket_title);
            viewHolder.tvTicketCity = (TextView) convertView.findViewById(R.id.tv_home_fragment_ticket_city);
            viewHolder.tvTicketDate = (TextView) convertView.findViewById(R.id.tv_home_fragment_ticket_date);
            viewHolder.tvTicketPrice = (TextView) convertView.findViewById(R.id.tv_home_fragment_ticket_price);
            viewHolder.tvTicketReg = (TextView) convertView.findViewById(R.id.tv_home_fragment_ticket_reg);
            viewHolder.rlItem = (LinearLayout) convertView.findViewById(R.id.ll_search_ticket_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CommonBean.Data bean = datas.get(position);
        if (TextUtils.isEmpty(bean.getImageUrl())) {
            viewHolder.rlItem.setVisibility(View.INVISIBLE);
            return convertView;
        }
        viewHolder.rlItem.setVisibility(View.VISIBLE);
        Glide.with(context).load(bean.getImageUrl()).into(viewHolder.imageView);
        viewHolder.tvTicketTitle.setText(bean.getName());
        viewHolder.tvTicketCity.setText(bean.getCity());
        viewHolder.tvTicketDate.setText(bean.getStartDate());
        viewHolder.tvTicketPrice.setText("￥" + bean.getPrice() + "/人");
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView tvTicketTitle;
        TextView tvTicketCity;
        TextView tvTicketDate;
        TextView tvTicketReg;
        TextView tvTicketPrice;
        LinearLayout rlItem;
    }
}
