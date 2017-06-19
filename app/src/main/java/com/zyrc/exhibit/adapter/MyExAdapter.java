package com.zyrc.exhibit.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.entity.MyExBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class MyExAdapter extends BaseQuickAdapter<MyExBean.Data,BaseViewHolder>{
    public MyExAdapter(@LayoutRes int layoutResId, @Nullable List<MyExBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyExBean.Data item) {
        Glide.with(mContext)
                .load(item.getExhibit().getImageUrl())
                .error(R.drawable.error)
                .into((ImageView) helper.getView(R.id.iv_my_ex));
        helper.setText(R.id.tv_my_ex_title,item.getExhibit().getName());
        helper.setText(R.id.tv_my_ex_name,item.getExhibit().getDescription());
    }
}
