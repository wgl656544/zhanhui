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
import com.ex.administrator.zhanhui.adapter.FindHaiNanAdapter;
import com.ex.administrator.zhanhui.util.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class FindFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.rl_find_hainan)
    private RecyclerView mRecycleView;
    @ViewInject(R.id.swipe_find_fragment)
    private SwipeRefreshLayout swipeFindFragment;


    private View view;
    private List<String> datas;
    private FindHaiNanAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find, null);
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
        adapter = new FindHaiNanAdapter(datas, getActivity());
        mRecycleView.setAdapter(adapter);

    }

    private void setListeners() {
        swipeFindFragment.setColorSchemeResources(R.color.color_bule_2, R.color.colorOrange);
        swipeFindFragment.setOnRefreshListener(this);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeFindFragment.setRefreshing(false);
                datas.add("测试4");
                adapter.notifyDataSetChanged();
                ToastUtil.show(getActivity(), "刷新成功");
            }
        }, 2000);
    }
}
