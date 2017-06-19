package com.zyrc.exhibit.view.homeInfoHeader;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zyrc.exhibit.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderHomeInfoFilterView implements HomeInfoFilterView.OnFilterClickListener {

    @ViewInject(R.id.fv_home_fragment_info_filter)
    HomeInfoFilterView fvFilter;

    private static TextView tvCategoryTitle;
    private static TextView tvPlaceTitle;
    private static TextView tvDateTitle;

    private Context context;

    public HeaderHomeInfoFilterView() {
    }

    public HeaderHomeInfoFilterView(Activity context) {
        this.context = context;
    }

    public void getView(ListView listView) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_home_fragment_info_filter, listView, false);
        x.view().inject(this, view);
        tvCategoryTitle = (TextView) view.findViewById(R.id.tv_home_fragment_category_title);
        tvPlaceTitle = (TextView) view.findViewById(R.id.tv_home_fragment_info_place_title);
        tvDateTitle = (TextView) view.findViewById(R.id.tv_home_fragment_info_date_title);

        dealWithTheView();
        listView.addHeaderView(view);
    }

    public static void setTitle(int position, String title) {
        switch (position) {
            case 0:
                if (TextUtils.equals(title, "不限")) {
                    title = "分类";
                }
                tvCategoryTitle.setText(title);
                break;
            case 1:
                if (TextUtils.equals(title, "不限")) {
                    title = "地方";
                }
                tvPlaceTitle.setText(title);
                break;
            case 2:
                if (TextUtils.equals(title, "不限")) {
                    title = "时间";
                }
                tvDateTitle.setText(title);
                break;
        }
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
