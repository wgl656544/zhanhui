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
 * Created by Administrator on 2017/3/9 0009.
 */

public class SearchBlogAdapter extends BaseAdapter {
    private Context context;
    private List<CommonBean.Data> datas;
    private LayoutInflater layoutInflater;
    private int mHeight;

    public SearchBlogAdapter(Context context, List<CommonBean.Data> datas,int mHeight) {
        this.context = context;
        this.datas = datas;
        this.mHeight = mHeight;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return datas.size() == 0 ? 1 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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
        if (datas.size() == 0) {
            convertView = layoutInflater.inflate(R.layout.item_no_data_layout, null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight);
            RelativeLayout rootView = (RelativeLayout) convertView.findViewById(R.id.rl_root_view);
            rootView.setLayoutParams(params);
            return convertView;
        }
        ViewHolder viewHolder;
        if (convertView != null && convertView instanceof LinearLayout) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_home_fragment_info_list, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_info_list_pic);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_info_list_name);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_info_list_date);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_info_list_title);
            viewHolder.llItem = (LinearLayout) convertView.findViewById(R.id.ll_search_blog_item);
            convertView.setTag(viewHolder);
        }
        CommonBean.Data bean = datas.get(position);
        if (TextUtils.isEmpty(bean.getName())) {
            viewHolder.llItem.setVisibility(View.INVISIBLE);
            return convertView;
        }
        viewHolder.llItem.setVisibility(View.VISIBLE);
        Glide.with(context).load(bean.getImageUrl()).into(viewHolder.imageView);
        viewHolder.tvTitle.setText(bean.getTitle());
        viewHolder.tvName.setText(bean.getName());
        viewHolder.tvDate.setText(bean.getStartDate());
        return convertView;
    }

    class ViewHolder {
        private ImageView imageView;
        private TextView tvName;
        private TextView tvDate;
        private TextView tvTitle;
        private LinearLayout llItem;
    }
}
