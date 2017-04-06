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
import com.ex.administrator.zhanhui.entity.InfoBlogBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class SearchBlogAdapter extends BaseAdapter {
    private Context context;
    private List<InfoBlogBean.Data> datas;
    private LayoutInflater layoutInflater;

    public SearchBlogAdapter(Context context, List<InfoBlogBean.Data> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public InfoBlogBean.Data getItem(int position) {
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
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_home_fragment_info_list, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_info_list_pic);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_info_list_title);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_info_list_date);
            viewHolder.tvNum = (TextView) convertView.findViewById(R.id.tv_info_list_num);
            viewHolder.llItem = (LinearLayout) convertView.findViewById(R.id.ll_search_blog_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        InfoBlogBean.Data bean = datas.get(position);
        if (TextUtils.isEmpty(bean.getImageUrl())) {
            viewHolder.llItem.setVisibility(View.INVISIBLE);
            return convertView;
        }
        viewHolder.llItem.setVisibility(View.VISIBLE);
        Glide.with(context).load(bean.getImageUrl()).into(viewHolder.imageView);
        viewHolder.tvName.setText(bean.getName());
        viewHolder.tvDate.setText(bean.getStartDate());
        viewHolder.tvNum.setText(bean.getCity());
        return convertView;
    }

    class ViewHolder {
        private ImageView imageView;
        private TextView tvName;
        private TextView tvDate;
        private TextView tvNum;
        private LinearLayout llItem;
    }
}
