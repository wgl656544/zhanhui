package com.zyrc.exhibit.view.homeTicketHeader;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zyrc.exhibit.R;
import com.zyrc.exhibit.view.findEXheader.FilterView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderHomeTicketFilterView implements FilterView.OnFilterClickListener {

    @ViewInject(R.id.fv_filter)
    FilterView fvFilter;

    private static TextView tvCityTitle;
    private static TextView tvNearTitle;
    private static TextView tvTypeTitle;
    private static TextView tvSiftTitle;

    private Context context;

    public HeaderHomeTicketFilterView() {
    }

    public HeaderHomeTicketFilterView(Activity context) {
        this.context = context;
    }

    public void getView(ListView listView) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_find_exhibition_filter, listView, false);
        x.view().inject(this, view);
        tvCityTitle = (TextView) view.findViewById(R.id.tv_city_title);
        tvNearTitle = (TextView) view.findViewById(R.id.tv_near_title);
        tvTypeTitle = (TextView) view.findViewById(R.id.tv_type_title);
        tvSiftTitle = (TextView) view.findViewById(R.id.tv_sift_title);

        dealWithTheView();
        listView.addHeaderView(view);
    }

    //修改筛选框文本
    public static void setTitle(int position, String title) {
        switch (position) {
            case 0://城市
                if(TextUtils.equals(title,"不限")){
                    title = "城市";
                }
                tvCityTitle.setText(title);
                break;
            case 1://最近
                if(TextUtils.equals(title,"不限")){
                    title = "最近";
                }
                tvNearTitle.setText(title);
                break;
            case 2://类型
                if(TextUtils.equals(title,"不限")){
                    title = "类型";
                }
                tvTypeTitle.setText(title);
                break;
            case 3://筛选
                if(TextUtils.equals(title,"不限")){
                    title = "筛选";
                }
                tvSiftTitle.setText(title);
                break;
        }
    }

    // 获得筛选View
    public FilterView getFilterView() {
        return fvFilter;
    }

    private void dealWithTheView() {
        fvFilter.setOnFilterClickListener(this);
    }

    @Override
    public void onFilterClick(int position) {
        if (onFilterClickListener != null) {
            onFilterClickListener.onFilterClick(position);
        }
    }

    private OnFilterClickListener onFilterClickListener;

    public void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        this.onFilterClickListener = onFilterClickListener;
    }

    public interface OnFilterClickListener {
        void onFilterClick(int position);
    }

}
