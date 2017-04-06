package com.ex.administrator.zhanhui.view.homeFragmentTeamHeader;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.adapter.TeamChannelAdapter;
import com.ex.administrator.zhanhui.entity.InfoCategoryBean;
import com.ex.administrator.zhanhui.entity.TeamTypeBean;
import com.ex.administrator.zhanhui.model.InfoCategoryModel;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderHomeFragmentTeamChannelView {
    @ViewInject(R.id.rl_home_fragment_team_channel)
    private RecyclerView mRecyclerView;

    private TeamChannelAdapter adapter;
    private InfoCategoryModel model = new InfoCategoryModel();
    private Context context;
    private String name = "termName=news";
    private InfoCategoryBean infoCategoryBean;
    private List<InfoCategoryBean.Data> datas;

    public HeaderHomeFragmentTeamChannelView(Activity context) {
        this.context = context;
    }

    public void getView(ListView listView) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_home_fragment_team_channel, listView, false);
        x.view().inject(this, view);
        listView.addHeaderView(view);
    }

    //显示频道数据
    public void showChannel(List<TeamTypeBean.Data> datas) {
        if (datas != null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            adapter = new TeamChannelAdapter(context, datas);
            mRecyclerView.setAdapter(adapter);
        }
    }

}
