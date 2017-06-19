package com.zyrc.exhibit.adapter.filter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.zyrc.exhibit.R;
import com.zyrc.exhibit.model.filter.FilterEntity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


/**
 * Created by sunfusheng on 16/4/23.
 */
public class FilterRightAdapter extends BaseListAdapter<FilterEntity> {


    public FilterRightAdapter(Context context, List<FilterEntity> list) {
        super(context, list);
    }

    public void setSelectedEntity(FilterEntity filterEntity) {
        for (FilterEntity entity : getData()) {
            entity.setSelected(filterEntity != null && entity.getKey().equals(filterEntity.getKey()));
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_filter_one, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FilterEntity entity = getItem(position);

        holder.tvTitle.setText(entity.getKey());
        if (entity.isSelected()) {
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.colorOrange));
        } else {
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_3));
        }

        return convertView;
    }

    static class ViewHolder {
        @ViewInject(R.id.iv_image)
        ImageView ivImage;
        @ViewInject(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            x.view().inject(this, view);
        }
    }
}
