package com.zyrc.exhibit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mylibrary.base.BaseFragment;
import com.google.gson.Gson;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.activity.WebViewActivity;
import com.zyrc.exhibit.adapter.HaiNanAdapter;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.model.Model;
import com.zyrc.exhibit.view.findhainan.FindHainanHeader;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class FindFragment extends BaseFragment implements PtrHandler {
    @ViewInject(R.id.rl_find_hainan)
    private RecyclerView mRecycleView;

    @ViewInject(R.id.ptr_find_fragment)
    private PtrFrameLayout mPtrFrameLayout;

    private FindHainanHeader rlHeader;
    private Model model;
    private int page = 1;
    private int itemPerPage = 20;
    private String param = "";
    private String paramNum = "";
    private String categoryCode = "&categoryCode=";
    private String type = "";
    private List<CommonBean.Data> findDatas;


    private View view;
    private List<String> datas;
    private HaiNanAdapter adapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    stopLoading();
                    String json = (String) msg.obj;
                    CommonBean bean = new Gson().fromJson(json, CommonBean.class);
                    if (findDatas != null) {//
                        mPtrFrameLayout.refreshComplete();
                        findDatas.clear();
                        findDatas.addAll(bean.getData());
                        sortData(findDatas);
                    } else {//首次进入
                        findDatas = bean.getData();
                        show(findDatas);
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
        return R.layout.fragment_find;
    }

    @Override
    public void initData() {

    }


    private void initview() {
        model = new Model();
        paramNum = "?page=" + page + "&itemPerPage=" + itemPerPage;
        type = "social-ly";
        param = paramNum + categoryCode + type;
        model.getData(handler, UrlConstant.HTTP_URL_SEARCH_FIND, HandlerConstant.SEARCH_SUCCESS, param);
        startLoading("加载中");
        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getActivity());
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);
        rlHeader = new FindHainanHeader(getActivity());
    }

    private void setListeners() {
        mPtrFrameLayout.setPtrHandler(this);
    }

    private void sortData(List<CommonBean.Data> datas) {
        for (int i = 0; i < datas.size(); i++) {
            if (i == 0 || i == 1) {
                datas.get(i).setItemType(CommonBean.Data.HENG);
            } else {
                datas.get(i).setItemType(CommonBean.Data.SHU);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void show(final List<CommonBean.Data> datas) {
        for (int i = 0; i < datas.size(); i++) {
            if (i == 0 || i == 1) {
                datas.get(i).setItemType(CommonBean.Data.HENG);
            } else {
                datas.get(i).setItemType(CommonBean.Data.SHU);
            }
        }
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HaiNanAdapter(datas);
        rlHeader.getView(adapter, mRecycleView);
        mRecycleView.setAdapter(adapter);
        rlHeader.setOnItemClickListener(new FindHainanHeader.OnItemClickListener() {
            @Override
            public void onItemClick(String typeName) {
                type = typeName;
                param = paramNum + categoryCode + type;
                model.getData(handler, UrlConstant.HTTP_URL_SEARCH_FIND, HandlerConstant.SEARCH_SUCCESS, param);
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra(HandlerConstant.DETAIL_BLOG, datas.get(position)));
            }
        });
    }

    //下拉刷新
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        model.getData(handler, UrlConstant.HTTP_URL_SEARCH_FIND, HandlerConstant.SEARCH_SUCCESS, param);
    }
}
