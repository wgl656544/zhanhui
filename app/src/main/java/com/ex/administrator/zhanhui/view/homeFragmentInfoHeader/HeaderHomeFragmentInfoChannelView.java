package com.ex.administrator.zhanhui.view.homeFragmentInfoHeader;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.adapter.InfoCategoryChannelAdapter;
import com.ex.administrator.zhanhui.entity.InfoCategoryBean;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderHomeFragmentInfoChannelView {
    @ViewInject(R.id.rl_home_fragment_info_channel)
    private RecyclerView mRecyclerView;
    private InfoCategoryChannelAdapter adapter;
    private Context context;

    public HeaderHomeFragmentInfoChannelView(Activity context) {
        this.context = context;
    }

    public void getView(ListView listView) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_home_fragment_info_channel, listView, false);
        x.view().inject(this, view);
        listView.addHeaderView(view);
    }

    //显示频道数据
    public void showChannel(List<InfoCategoryBean.Data> datas) {
        if (datas != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            adapter = new InfoCategoryChannelAdapter(context, datas);
            mRecyclerView.setAdapter(adapter);
        }

    }

}
