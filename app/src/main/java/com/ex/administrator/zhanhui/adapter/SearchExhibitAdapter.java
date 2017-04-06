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
import com.ex.administrator.zhanhui.entity.SearchExhibitBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class SearchExhibitAdapter extends BaseAdapter {
    private Context context;
    private List<SearchExhibitBean.Data> datas;
    private LayoutInflater layoutInflater;

    public SearchExhibitAdapter(Context context, List<SearchExhibitBean.Data> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public SearchExhibitBean.Data getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.item_find_exhibition_list, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_find_exhibition_pic);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_find_exhibition_name);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_find_exhibition_date);
            viewHolder.tvCity = (TextView) convertView.findViewById(R.id.tv_find_exhibition_city);
            viewHolder.llItem = (LinearLayout) convertView.findViewById(R.id.ll_find_exhibition_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SearchExhibitBean.Data bean = datas.get(position);

        if (TextUtils.isEmpty(bean.getImageUrl())) {
            viewHolder.llItem.setVisibility(View.INVISIBLE);
            return convertView;
        }
        viewHolder.llItem.setVisibility(View.VISIBLE);
        Glide.with(context).load(bean.getImageUrl()).into(viewHolder.imageView);
        viewHolder.tvName.setText(bean.getName());
        viewHolder.tvDate.setText(bean.getStartDate());
        viewHolder.tvCity.setText(bean.getCity());

        return convertView;
    }


    class ViewHolder {
        private ImageView imageView;
        private TextView tvName;
        private TextView tvDate;
        private TextView tvCity;
        private LinearLayout llItem;
    }
}
