package com.example.mylibrary.base;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mylibrary.R;
import com.example.mylibrary.xrecyclerview.XRecyclerView;

/**
 * Created by Administrator on 2017/1/4.
 */

public abstract class BaseListActivity extends BaseActivity implements XRecyclerView.LoadingListener{

    private XRecyclerView mRecyclerView;
    private int currentPage = 0;
    private boolean isRefresh = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        setupView();
    }

    private void setupView() {
        //初始化
        mRecyclerView =  (XRecyclerView)findViewById(R.id.base_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        setHeaderView(mRecyclerView);
        //
        mRecyclerView.setAdapter(getFitAdapter());
        mRecyclerView.setLoadingListener(this);
//        mRecyclerView.setLoadingMoreEnabled(false);

        if(isRefresh()) {
            mRecyclerView.refresh();
        }
    }

    public void setLoadingMoreEnable(Boolean flag){
        mRecyclerView.setLoadingMoreEnabled(flag);
    }

    protected abstract void setHeaderView(XRecyclerView recyclerView);

    /**
     * 是否主动刷新
     */
    protected abstract boolean isRefresh();

    protected abstract RecyclerView.Adapter getFitAdapter();

    @Override
    public void onRefresh() {
        isRefresh = true;
        currentPage = 0;
        initData(mRecyclerView,currentPage,isRefresh);
    }

    /**
     * 将recyclerview传过去*/
    protected abstract void initData(XRecyclerView recyclerView,int currentPage,boolean isRefresh);

    @Override
    public void onLoadMore() {
        isRefresh = false;
        currentPage ++;
        initData(mRecyclerView,currentPage,isRefresh);
    }
}
