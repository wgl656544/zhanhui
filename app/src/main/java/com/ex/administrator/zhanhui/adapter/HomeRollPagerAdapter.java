package com.ex.administrator.zhanhui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ex.administrator.zhanhui.application.MyApplication;
import com.ex.administrator.zhanhui.entity.HomePageBean;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class HomeRollPagerAdapter extends LoopPagerAdapter {
    private List<HomePageBean.DataList> topData;


    public HomeRollPagerAdapter(RollPagerView viewPager, List<HomePageBean.DataList> topData) {
        super(viewPager);
        this.topData = topData;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        Glide.with(MyApplication.getmMyApplication().getApplicationContext()).load(topData.get(position).getImageUrl()).into(view);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getRealCount() {
        return topData.size();
    }
}
