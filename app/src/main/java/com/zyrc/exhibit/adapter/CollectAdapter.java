package com.zyrc.exhibit.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
    private static final String WISH = "wish";
    private static final String VIEW = "view";
    private String type = "wish";
    public CollectAdapter(@LayoutRes int layoutResId, @Nullable List<CommonBean.Data> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommonBean.Data item) {
        switch (type){
            case WISH:
                helper.addOnClickListener(R.id.btn_collect_list);
                break;
            case VIEW:
                helper.setVisible(R.id.btn_collect_list,false);
                break;
        }
        if(TextUtils.isEmpty(item.getTitle())){
            helper.setText(R.id.tv_collect_list_name,item.getName())
                    .setText(R.id.tv_collect_list_city,item.getDescription());
        } else {
            helper.setText(R.id.tv_collect_list_name,item.getTitle())
                    .setText(R.id.tv_collect_list_city,item.getDescription());
        }
        Glide.with(mContext)
                .load(item.getImageUrl())
                .error(R.drawable.error)
                .into((ImageView) helper.getView(R.id.iv_collect_list));
    }

    public void setType(String type){
        this.type = type;
    }
}
