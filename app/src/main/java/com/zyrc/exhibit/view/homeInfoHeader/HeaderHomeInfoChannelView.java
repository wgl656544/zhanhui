package com.zyrc.exhibit.view.homeInfoHeader;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.InfoCategoryChannelAdapter;
import com.zyrc.exhibit.entity.InfoCategoryBean;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderHomeInfoChannelView {
    @ViewInject(R.id.rl_home_fragment_info_channel)
    private RecyclerView mRecyclerView;
    private InfoCategoryChannelAdapter adapter;
    private Context context;
    List<InfoCategoryBean.Data> datas;

    public HeaderHomeInfoChannelView(Activity context) {
        this.context = context;
    }

    public void getView(ListView listView) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_home_fragment_info_channel, listView, false);
        x.view().inject(this, view);
        listView.addHeaderView(view);
        setListeners();
    }

    //显示频道数据
    public void showChannel(final List<InfoCategoryBean.Data> datas) {
        if (datas != null) {
            this.datas = datas;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            adapter = new InfoCategoryChannelAdapter(context, datas);
            mRecyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new InfoCategoryChannelAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (onItemChannelClickListtener != null) {
                        onItemChannelClickListtener.OnChannelClick(datas.get(position).getName());
                    }
                }
            });
        }

    }

    private void setListeners() {
    }

    //频道点击事件
    private OnItemChannelClickListtener onItemChannelClickListtener;

    //设置点击事件
    public void setOnItemChannelClickListtener(OnItemChannelClickListtener onItemChannelClickListtener) {
        this.onItemChannelClickListtener = onItemChannelClickListtener;
    }

    //点击事件回调
    public interface OnItemChannelClickListtener {
        void OnChannelClick(String title);
    }

}
