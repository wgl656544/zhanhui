package com.zyrc.exhibit.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.entity.CommonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/31 0031.
 */

public class HaiNanAdapter extends BaseMultiItemQuickAdapter<CommonBean.Data, BaseViewHolder> {
    public HaiNanAdapter(List<CommonBean.Data> data) {
        super(data);
        addItemType(CommonBean.Data.HENG, R.layout.item_find_hainan_list_1);
        addItemType(CommonBean.Data.SHU, R.layout.item_find_hainan_list_2);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonBean.Data item) {
        switch (helper.getItemViewType()) {
            case CommonBean.Data.HENG:
                helper.setText(R.id.tv_find_hainan_title, item.getDescription());
                helper.setText(R.id.tv_find_hainan_count, item.getCount() + "人看过");
                Glide.with(mContext)
                        .load(item.getImageUrl())
                        .error(R.drawable.error)
                        .into((ImageView) helper.getView(R.id.iv_find_hainan_pic));
                break;
            case CommonBean.Data.SHU:
                helper.setText(R.id.tv_find_hainan_title_2, item.getDescription());
                helper.setText(R.id.tv_find_hainan_count_2, item.getCount() + "人看过");
                Glide.with(mContext)
                        .load(item.getImageUrl())
                        .error(R.drawable.error)
                        .into((ImageView) helper.getView(R.id.iv_find_hainan_pic_2));
                break;
        }
    }
}
