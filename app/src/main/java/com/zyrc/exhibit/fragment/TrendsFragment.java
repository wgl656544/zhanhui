package com.zyrc.exhibit.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mylibrary.base.BaseFragment;
import com.google.gson.Gson;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.activity.BranchActivity;
import com.zyrc.exhibit.activity.ConfenceActivity;
import com.zyrc.exhibit.activity.DetailBuyActivity;
import com.zyrc.exhibit.activity.HomeFindEXActivity;
import com.zyrc.exhibit.activity.HomeInfoActivity;
import com.zyrc.exhibit.activity.HomeTeamActivity;
import com.zyrc.exhibit.activity.HomeTicketActivity;
import com.zyrc.exhibit.activity.WebViewActivity;
import com.zyrc.exhibit.adapter.ExTrendAdapter;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.model.Model;
import com.zyrc.exhibit.view.extrend.ExTrendAdvertHeader;
import com.zyrc.exhibit.view.extrend.ExTrendButtonChannelHeader;
import com.zyrc.exhibit.view.extrend.ExTrendChannelHeader;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 展会动态
 * Created by Administrator on 2017/2/16 0016.
 */

public class TrendsFragment extends BaseFragment implements PtrHandler {
    @ViewInject(R.id.rl_ex_trends)
    private RecyclerView mRecycleView;
    @ViewInject(R.id.ptr_trends_fragment)
    private PtrFrameLayout mPtrFrameLayout;

    private View view;
    ExTrendAdvertHeader advertHeader;
    ExTrendChannelHeader channelHeader;
    ExTrendButtonChannelHeader buttonChannelHeader;
    private ExTrendAdapter adapter;
    private Model model;
    private int page = 1;
    private int itemPerPage = 20;
    private String param = "";
    private int currentPosition = 0;
    private List<CommonBean.Data> datas;
    private String url = "";
    private final String branch = "branch";//分论坛

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    stopLoading();
                    String json = (String) msg.obj;
                    CommonBean bean = new Gson().fromJson(json, CommonBean.class);
                    if (datas == null) {//首次进入
                        datas = bean.getData();
                        showExTrends(datas);
                    } else {//点击分类按钮
                        mPtrFrameLayout.refreshComplete();
                        adapter.loadMoreComplete();
                        datas.clear();
                        datas.addAll(bean.getData());
                        sortData(datas);
                    }
                    break;
            }
        }
    };

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        x.view().inject(this, view);
        initview();
        setListeners();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trends;
    }

    @Override
    public void initData() {

    }

    private void initview() {
        model = new Model();
        param = "?page=" + page + "&itemPerPage=" + itemPerPage;
        url = UrlConstant.HTTP_URL_EX_TREND_EX;
        model.getData(handler, url, HandlerConstant.SEARCH_SUCCESS, param);
        startLoading("加载中");
        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getActivity());
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);
        advertHeader = new ExTrendAdvertHeader(getActivity());
        channelHeader = new ExTrendChannelHeader(getActivity());
        buttonChannelHeader = new ExTrendButtonChannelHeader(getActivity());
    }

    private void setListeners() {
        mPtrFrameLayout.setPtrHandler(this);
        //频道点击事件
        channelHeader.setOnItemClickListener(new ExTrendChannelHeader.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getActivity(), HomeFindEXActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), HomeInfoActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), HomeTicketActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), HomeTeamActivity.class));
                        break;
                }
            }
        });
        //分类点击事件
        buttonChannelHeader.setOnItemClickListener(new ExTrendButtonChannelHeader.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (currentPosition != position) {
                    currentPosition = position;
                    switch (position) {
                        case 0://展会
                            url = UrlConstant.HTTP_URL_EX_TREND_EX;
                            model.getData(handler, url, HandlerConstant.SEARCH_SUCCESS, param);
                            break;
                        case 1://资讯
                            url = UrlConstant.HTTP_URL_EX_TREND_INFO;
                            model.getData(handler, url, HandlerConstant.SEARCH_SUCCESS, param);
                            break;
                        case 2://门票
                            url = UrlConstant.HTTP_URL_EX_TREND_TICKET;
                            model.getData(handler, url, HandlerConstant.SEARCH_SUCCESS, param);
                            break;
                        case 3://团购
                            url = UrlConstant.HTTP_URL_EX_TREND_TEAM;
                            model.getData(handler, url, HandlerConstant.SEARCH_SUCCESS, param);
                            break;
                    }
                    if (adapter != null) {
                        adapter.setType(position);
                    }
                    startLoading("加载中");
                }
            }
        });
    }

    /**
     * 显示信息
     *
     * @param datas 数据源
     */
    private void showExTrends(final List<CommonBean.Data> datas) {
        for (int i = 0; i < datas.size(); i++) {
            if (i == 0 || i == 1) {
                datas.get(i).setItemType(CommonBean.Data.HENG);
            } else {
                datas.get(i).setItemType(CommonBean.Data.SHU);
            }
        }
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ExTrendAdapter(datas);
        adapter.setHeaderAndEmpty(true);
        channelHeader.getView(adapter, mRecycleView);
        advertHeader.getView(adapter, mRecycleView);
        buttonChannelHeader.getView(adapter, mRecycleView);
        mRecycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (currentPosition == 0) {//展会
                    if (datas.get(position).getLayout().equals(branch)) {//分论坛
                        startActivity(new Intent(getActivity(), BranchActivity.class).putExtra("entityId", datas.get(position).getEntityId()));
                    } else {//主论坛
                        startActivity(new Intent(getActivity(), ConfenceActivity.class).putExtra("entityId", datas.get(position).getEntityId()));
                    }
                } else if (currentPosition == 1) {
                    startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra(HandlerConstant.DETAIL_BLOG, datas.get(position)));
                } else if (currentPosition == 2) {
                    startActivity(new Intent(getActivity(), DetailBuyActivity.class).putExtra(HandlerConstant.DETAIL_BUY, datas.get(position)));
                } else if (currentPosition == 3) {
                    startActivity(new Intent(getActivity(), DetailBuyActivity.class).putExtra(HandlerConstant.DETAIL_BUY, datas.get(position)));
                }
            }
        });
    }

    private void sortData(List<CommonBean.Data> datas) {
        if(datas.size() != 0){
            for (int i = 0; i < datas.size(); i++) {
                if (i == 0 || i == 1) {
                    datas.get(i).setItemType(CommonBean.Data.HENG);
                } else {
                    datas.get(i).setItemType(CommonBean.Data.SHU);
                }
            }
            adapter.notifyDataSetChanged();
        } else {
            adapter.notifyDataSetChanged();
            adapter.setEmptyView(R.layout.item_no_data_layout, (ViewGroup) mRecycleView.getParent());
        }

    }

    //下拉刷新
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        model.getData(handler, url, HandlerConstant.SEARCH_SUCCESS, param);
    }
}
