package com.zyrc.exhibit.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.entity.CommonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

public class ExTrendAdapter extends BaseMultiItemQuickAdapter<CommonBean.Data, BaseViewHolder> {
    private static final int ZH = 0;
    private static final int ZX = 1;
    //    private static final int MP = 2;
//    private static final int TG = 3;
    private int type = ZH;

    public ExTrendAdapter(List<CommonBean.Data> data) {
        super(data);
        addItemType(CommonBean.Data.HENG, R.layout.item_ex_trend_list_1);
        addItemType(CommonBean.Data.SHU, R.layout.item_ex_trend_list_2);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonBean.Data item) {
        switch (helper.getItemViewType()) {
            case CommonBean.Data.HENG:
                if (type == ZH) {
                    helper.setText(R.id.tv_ex_trend_title, item.getName())
                            .setText(R.id.tv_ex_trend_time, item.getStartDate() + " - " + item.getEndDate())
                            .setText(R.id.tv_ex_trend_city, item.getCity() + " " + item.getAddress())
                            .setTextColor(R.id.tv_ex_trend_city, ContextCompat.getColor(mContext, R.color.text_color));
                } else if (type == ZX) {
                    helper.setText(R.id.tv_ex_trend_title, item.getTitle())
                            .setText(R.id.tv_ex_trend_time, item.getName())
                            .setText(R.id.tv_ex_trend_city, item.getStartDate())
                            .setTextColor(R.id.tv_ex_trend_city, ContextCompat.getColor(mContext, R.color.text_color));
                } else {
                    helper.setText(R.id.tv_ex_trend_title, item.getName())
                            .setText(R.id.tv_ex_trend_time, item.getDescription())
                            .setText(R.id.tv_ex_trend_city, "￥" + item.getPrice() + "/人")
                            .setTextColor(R.id.tv_ex_trend_city, ContextCompat.getColor(mContext, R.color.colorOrange));
                }
                Glide.with(mContext)
                        .load(item.getImageUrl())
                        .error(R.drawable.error)
                        .into((ImageView) helper.getView(R.id.iv_ex_trend));
                break;
            case CommonBean.Data.SHU:
                if (type == ZH) {
                    helper.setText(R.id.tv_ex_trend_title_2, item.getName())
                            .setText(R.id.tv_ex_trend_time_2, item.getStartDate() + " - " + item.getEndDate())
                            .setText(R.id.tv_ex_trend_city_2, item.getCity() + " " + item.getAddress())
                            .setTextColor(R.id.tv_ex_trend_city_2, ContextCompat.getColor(mContext, R.color.text_color));
                } else if (type == ZX) {
                    helper.setText(R.id.tv_ex_trend_title_2, item.getTitle())
                            .setText(R.id.tv_ex_trend_time_2, item.getName())
                            .setText(R.id.tv_ex_trend_city_2, item.getStartDate())
                            .setTextColor(R.id.tv_ex_trend_city_2, ContextCompat.getColor(mContext, R.color.text_color));
                } else {
                    helper.setText(R.id.tv_ex_trend_title_2, item.getName())
                            .setText(R.id.tv_ex_trend_time_2, item.getDescription())
                            .setText(R.id.tv_ex_trend_city_2, "￥" + item.getPrice() + "/人")
                            .setTextColor(R.id.tv_ex_trend_city_2, ContextCompat.getColor(mContext, R.color.colorOrange));
                }
                Glide.with(mContext)
                        .load(item.getImageUrl())
                        .error(R.drawable.error)
                        .into((ImageView) helper.getView(R.id.iv_ex_trend_2));
                break;
        }
    }

    public void setType(int type) {
        this.type = type;
    }
}
