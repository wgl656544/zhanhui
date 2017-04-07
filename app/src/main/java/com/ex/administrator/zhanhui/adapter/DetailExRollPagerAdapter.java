package com.ex.administrator.zhanhui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ex.administrator.zhanhui.entity.CommonListBean;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class DetailExRollPagerAdapter extends LoopPagerAdapter {
    private List<CommonListBean.DataList> topData;


    public DetailExRollPagerAdapter(RollPagerView viewPager, List<CommonListBean.DataList> topData) {
        super(viewPager);
        this.topData = topData;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        Glide.with(container.getContext()).load(topData.get(position).getImageUrl()).into(view);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getRealCount() {
        return topData.size();
    }
}
