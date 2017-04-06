package com.ex.administrator.zhanhui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/4/1 0001.
 */

public class AllOrderAdapter extends FragmentPagerAdapter {
    private List<String> titlelist;
    private List<Fragment> fragments;

    public AllOrderAdapter(FragmentManager fm, List<String> titlelist, List<Fragment> fragments) {
        super(fm);
        this.titlelist = titlelist;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlelist.get(position);
    }
}
