package com.zyrc.exhibit.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.entity.HotCityBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13 0013.
 */

public class ChoiceCityAdapter extends BaseQuickAdapter<HotCityBean.Data, BaseViewHolder> {
    public ChoiceCityAdapter(@Nullable List<HotCityBean.Data> data) {
        super(R.layout.item_choice_city_ist, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotCityBean.Data item) {
        helper.setText(R.id.tv_hot_city_name, item.getName());
    }
}
