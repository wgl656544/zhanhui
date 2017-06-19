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
 * Created by Administrator on 2017/3/10 0010.
 */

public class SearchTeamAdapter extends BaseAdapter {
    private Context context;
    private List<CommonBean.Data> datas;
    private LayoutInflater inflater;
    private int height;

    public SearchTeamAdapter(Context context, List<CommonBean.Data> datas,int height) {
        this.context = context;
        this.datas = datas;
        this.height = height;
        inflater = LayoutInflater.from(context);
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
            convertView = inflater.inflate(R.layout.item_no_data_layout, null);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            RelativeLayout rootView = (RelativeLayout) convertView.findViewById(R.id.rl_root_view);
            rootView.setLayoutParams(params);
            return convertView;
        }

        if (convertView != null && convertView instanceof LinearLayout) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.item_home_fragment_team_list, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_team_list_pic);
            viewHolder.tvTicketTitle = (TextView) convertView.findViewById(R.id.tv_team_list_title);
            viewHolder.tvTicketCity = (TextView) convertView.findViewById(R.id.tv_team_list_city);
            viewHolder.tvTicketDescrip = (TextView) convertView.findViewById(R.id.tv_team_list_descrip);
            viewHolder.tvTicketPrice = (TextView) convertView.findViewById(R.id.tv_team_list_price);
            viewHolder.rlItem = (LinearLayout) convertView.findViewById(R.id.ll_busi_type_item);
            convertView.setTag(viewHolder);
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
        viewHolder.tvTicketDescrip.setText(bean.getDescription());
        viewHolder.tvTicketPrice.setText("￥" + bean.getPrice() + "/人");
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView tvTicketTitle;
        TextView tvTicketCity;
        TextView tvTicketDescrip;
        TextView tvTicketPrice;
        LinearLayout rlItem;
    }
}
