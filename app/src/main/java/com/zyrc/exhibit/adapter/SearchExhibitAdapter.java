package com.zyrc.exhibit.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.entity.CommonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class SearchExhibitAdapter extends BaseAdapter {
    private Context context;
    private List<CommonBean.Data> datas;
    private int height;
    private LayoutInflater layoutInflater;

    public SearchExhibitAdapter(Context context, List<CommonBean.Data> datas, int height) {
        this.context = context;
        this.datas = datas;
        this.height = height;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size() == 0 ? 1 : datas.size();
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
        if (datas.size() == 0) {
            convertView = layoutInflater.inflate(R.layout.item_no_data_layout, null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            RelativeLayout rootView = (RelativeLayout) convertView.findViewById(R.id.rl_root_view);
            rootView.setLayoutParams(params);
            return convertView;
        }

        if (convertView != null && convertView instanceof LinearLayout) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = layoutInflater.inflate(R.layout.item_find_exhibition_list, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_find_exhibition_pic);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_find_exhibition_name);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_find_exhibition_date);
            viewHolder.tvCity = (TextView) convertView.findViewById(R.id.tv_find_exhibition_city);
            viewHolder.llItem = (LinearLayout) convertView.findViewById(R.id.ll_find_exhibition_item);
            convertView.setTag(viewHolder);
        }

        CommonBean.Data bean = datas.get(position);

        if (TextUtils.isEmpty(bean.getName())) {
            viewHolder.llItem.setVisibility(View.INVISIBLE);
            return convertView;
        }
        viewHolder.llItem.setVisibility(View.VISIBLE);
        Glide.with(context).load(bean.getImageUrl()).error(R.drawable.error).placeholder(R.drawable.default_pic).into(viewHolder.imageView);
        viewHolder.tvName.setText(bean.getName());
        viewHolder.tvDate.setText(bean.getStartDate()+" - "+bean.getEndDate());
        viewHolder.tvCity.setText(bean.getCity()+" "+bean.getAddress());

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
