package com.zyrc.exhibit.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.entity.CommonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/31 0031.
 */

public class CollectAdapter extends BaseQuickAdapter<CommonBean.Data, BaseViewHolder>{
    public CollectAdapter(@LayoutRes int layoutResId, @Nullable List<CommonBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonBean.Data item) {
        helper.setText(R.id.tv_collect_list_name,item.getName());
        Glide.with(mContext)
                .load(item.getImageUrl())
                .error(R.drawable.error)
                .into((ImageView) helper.getView(R.id.iv_collect_list));
        helper.addOnClickListener(R.id.btn_collect_list);
    }
}
