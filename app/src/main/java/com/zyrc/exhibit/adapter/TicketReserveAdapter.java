package com.zyrc.exhibit.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.entity.CommonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/2 0002.
 */

public class TicketReserveAdapter extends BaseQuickAdapter<CommonBean.Data, BaseViewHolder> {
    public TicketReserveAdapter(@LayoutRes int layoutResId, @Nullable List<CommonBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonBean.Data item) {
        helper.setText(R.id.cb_all_ex, item.getName()).addOnClickListener(R.id.cb_all_ex);
    }
}
