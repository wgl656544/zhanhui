package com.zyrc.exhibit.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.app.MyApplication;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.zyrc.exhibit.entity.CommonBean;

import java.util.List;

/**
 * 轮播广告Adapter
 * Created by Administrator on 2017/2/16 0016.
 */

public class HomeRollPagerAdapter extends LoopPagerAdapter {
    private List<CommonBean.Data> topData;


    public HomeRollPagerAdapter(RollPagerView viewPager, List<CommonBean.Data> topData) {
        super(viewPager);
        this.topData = topData;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        Glide.with(MyApplication.getmMyApplication().getApplicationContext())
                .load(topData.get(position).getImageUrl())
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.error)
                .into(view);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getRealCount() {
        return topData.size();
    }
}
