package com.zyrc.exhibit.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.entity.FindHaiNanTypeBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/31 0031.
 */

public class FindHaiNanTypeAdapter extends BaseQuickAdapter<FindHaiNanTypeBean.Data, BaseViewHolder> {

    public FindHaiNanTypeAdapter(@LayoutRes int layoutResId, @Nullable List<FindHaiNanTypeBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FindHaiNanTypeBean.Data item) {
        helper.setText(R.id.tv_find_hainan_type, item.getName());
        if (item.getStatus() == 1) {
            helper.setBackgroundRes(R.id.tv_find_hainan_type, R.drawable.bg_orange);
        } else {
            helper.setBackgroundColor(R.id.tv_find_hainan_type, ContextCompat.getColor(mContext,R.color.color_white_1));
        }
    }
}
