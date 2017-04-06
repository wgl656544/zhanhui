package com.ex.administrator.zhanhui.view.homeFragmentInfoHeader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ex.administrator.zhanhui.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderHomeFragmentInfoFilterView implements HomeFragmrntInfoFilterView.OnFilterClickListener {

    @ViewInject(R.id.fv_home_fragment_info_filter)
    HomeFragmrntInfoFilterView fvFilter;

    private static TextView tvCategoryTitle;
    private static TextView tvPlaceTitle;
    private static TextView tvDateTitle;

    private Context context;

    public HeaderHomeFragmentInfoFilterView() {
    }

    public HeaderHomeFragmentInfoFilterView(Activity context) {
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

    public static void setTitle(int position,String title){
        switch (position){
            case 0:
                tvCategoryTitle.setText(title);
                break;
            case 1:
                tvPlaceTitle.setText(title);
                break;
            case 2:
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