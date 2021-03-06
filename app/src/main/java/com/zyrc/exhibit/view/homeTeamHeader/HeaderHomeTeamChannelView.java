package com.zyrc.exhibit.view.homeTeamHeader;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.TeamChannelAdapter;
import com.zyrc.exhibit.entity.TypeBean;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderHomeTeamChannelView {
    @ViewInject(R.id.rl_home_fragment_team_channel)
    private RecyclerView mRecyclerView;

    private TeamChannelAdapter adapter;
    private Context context;

    public HeaderHomeTeamChannelView(Activity context) {
        this.context = context;
    }

    public void getView(ListView listView) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_home_fragment_team_channel, listView, false);
        x.view().inject(this, view);
        listView.addHeaderView(view);
    }

    //显示频道数据
    public void showChannel(List<TypeBean.Data> datas) {
        if (datas != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            adapter = new TeamChannelAdapter(context, datas);
            mRecyclerView.setAdapter(adapter);
        }
    }

}
