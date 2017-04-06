package com.ex.administrator.zhanhui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.adapter.ExTrendsAdapter;
import com.ex.administrator.zhanhui.util.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class TrendsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.rl_ex_trends)
    private RecyclerView mRecycleView;
    @ViewInject(R.id.swipe_trends_fragment)
    private SwipeRefreshLayout swipeTrendsFragment;

    private View view;
    private ExTrendsAdapter adapter;
    private List<String> datas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trends, null);
        x.view().inject(this, view);

        initview();
        setListeners();
        return view;
    }

    private void initview() {
        datas = new ArrayList<>();
        datas.add("测试1");
        datas.add("测试2");
        datas.add("测试3");
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ExTrendsAdapter(datas, getActivity());
        mRecycleView.setAdapter(adapter);
    }

    private void setListeners() {
        swipeTrendsFragment.setColorSchemeResources(R.color.color_bule_2, R.color.colorOrange);
        swipeTrendsFragment.setOnRefreshListener(this);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeTrendsFragment.setRefreshing(false);
                ToastUtil.show(getActivity(), "刷新成功");
            }
        }, 2000);
    }
}
