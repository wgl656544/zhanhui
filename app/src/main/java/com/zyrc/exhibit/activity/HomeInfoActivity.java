package com.zyrc.exhibit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.mylibrary.base.BaseActivity;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.SmoothListView.SmoothListView;
import com.zyrc.exhibit.adapter.SearchBlogAdapter;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.entity.InfoCategoryBean;
import com.zyrc.exhibit.entity.InfoPlaceBean;
import com.zyrc.exhibit.model.GetDataModel;
import com.zyrc.exhibit.model.filter.FilterEntity;
import com.zyrc.exhibit.util.DensityUtil;
import com.zyrc.exhibit.util.ToastUtil;
import com.zyrc.exhibit.view.ModelUtil;
import com.zyrc.exhibit.view.homeInfoHeader.HeaderHomeInfoAdvertView;
import com.zyrc.exhibit.view.homeInfoHeader.HeaderHomeInfoFilterView;
import com.zyrc.exhibit.view.homeInfoHeader.HomeInfoFilterView;
import com.zyrc.exhibit.view.homeInfoHeader.InfoFilterData;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 搜索资讯页面
 * Created by Administrator on 2017/2/20 0020.
 */

public class HomeInfoActivity extends BaseActivity implements
        View.OnClickListener, PtrHandler, AdapterView.OnItemClickListener {
    @ViewInject(R.id.sl_home_fagment_infomation)
    private SmoothListView mSmoothListView;
    @ViewInject(R.id.ptr_info)
    private PtrFrameLayout ptrInfo;
    @ViewInject(R.id.fv_home_fragment_info)
    private HomeInfoFilterView mFilterView;
    @ViewInject(R.id.iv_home_info_back)
    private ImageView ivBack;
    @ViewInject(R.id.rl_info_title)
    private RelativeLayout rlInfoTitle;

    private HeaderHomeInfoFilterView mHeaderFilterView;
    //    private HeaderHomeInfoChannelView mHeaderChannelView;
    private HeaderHomeInfoAdvertView mHeaderAdvertlView;
    private InfoFilterData infoFilterData;
    private int filterPosition;//点击第几个筛选
    private int filterViewPosition = 3;//筛选视图的位置
    private boolean isStickyTop = false;//是否吸附在顶部
    private boolean isSmooll;//是否滑动中
    private boolean isShow = false;//是否显示
    private int filterViewTopMargin;//距离顶部的距离
    private View itemHeaderFilterView;
    private List<FilterEntity> exType;//展会类型
    private SearchBlogAdapter adapter;
    private Activity mActivity;

    private GetDataModel model = new GetDataModel();//获取展会分类对象
    private List<FilterEntity> categorys;//筛选视图分类数据
    private List<FilterEntity> places;//筛选视图地方数据
    private String name = "termName=new";//分类数据参数
    private InfoCategoryBean infoCategoryBean;//资讯实体类
    private List<InfoCategoryBean.Data> datas;//资讯页面频道实体类
    private InfoPlaceBean infoPlaceBean;//海南所有县市实体类
    private List<InfoPlaceBean.Data> placeDatas;
    private List<InfoPlaceBean.SubData> placeSubDatas;
    private CommonBean infoBlogBean;//查询资讯实体类
    private List<CommonBean.Data> blogDatas;
    //筛选条件
    private int page = 1;
    private int itemPerPage = 20;
    private String paramNum = "";//搜索参数
    private String city = "";
    private String categoryCode = "";
    private String startTime = "";
    private String param = "";
    private boolean isRefreshing = false;
    private int mHeight;

    private MyHandler handler = new MyHandler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_info);
        x.view().inject(this);
        mActivity = this;
        paramNum = "?page=" + page + "&itemsPerPage=" + itemPerPage;
        param = paramNum;
        startLoading("正在加载中...");//开始加载动画
        model.getInfoCategory(handler, name);//发送请求获取资讯分类数据
        model.getHaiNanAllCity(handler);//获取海南所有县市
        model.search(handler, UrlConstant.HTTP_URL_INFO_SEARCH_BLOG, param);//查询资讯
        initview();
        setListener();
    }


    /**
     * 初始化控件
     */
    private void initview() {
//        //添加频道头布局
//        mHeaderChannelView = new HeaderHomeInfoChannelView(this);
//        mHeaderChannelView.getView(mSmoothListView);
        //添加广告头布局
        mHeaderAdvertlView = new HeaderHomeInfoAdvertView(this);
        mHeaderAdvertlView.getView(mSmoothListView);
        //添加筛选头布局
        mHeaderFilterView = new HeaderHomeInfoFilterView(this);
        mHeaderFilterView.getView(mSmoothListView);

        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(this);
        ptrInfo.setHeaderView(header);
        ptrInfo.addPtrUIHandler(header);
        ptrInfo.setPtrHandler(this);
        mSmoothListView.setLoadMoreEnable(true);
        mSmoothListView.setRefreshEnable(false);
    }

    /**
     * 设置监听器
     */
    private void setListener() {
//        mSmoothListView.setSmoothListViewListener(this);
        //listview点击事件
        mSmoothListView.setOnItemClickListener(this);
        //返回
        ivBack.setOnClickListener(this);
        //筛选头布局监听器
        mHeaderFilterView.setOnFilterClickListener(new HeaderHomeInfoFilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterPosition = position;
                isSmooll = true;
                mSmoothListView.smoothScrollToPositionFromTop(filterViewPosition, 0);
            }
        });
//        真正的筛选视图监听器
        mFilterView.setOnFilterClickListener(new HomeInfoFilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                mFilterView.show(position);
            }
        });
//        listview监听器
        mSmoothListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if ((scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) &&
                        view.getLastVisiblePosition() == (view.getCount() - 1)
                        && !isRefreshing) {
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (itemHeaderFilterView == null) {
                    itemHeaderFilterView = mSmoothListView.getChildAt(filterViewPosition - firstVisibleItem);
                }
                if (itemHeaderFilterView != null) {
                    filterViewTopMargin = itemHeaderFilterView.getTop();
                }
                // 处理筛选是否吸附在顶部
                if ((filterViewTopMargin <= 0 || firstVisibleItem > filterViewPosition) && isShow) {
                    isStickyTop = true; // 吸附在顶部
                    mFilterView.setVisibility(View.VISIBLE);
                } else {
                    isStickyTop = false; // 没有吸附在顶部
                    mFilterView.setVisibility(View.GONE);
                }
                //吸附在顶部并且在滑动中
                if (isStickyTop && isSmooll) {
                    isSmooll = false;
                    mFilterView.show(filterPosition);
                }
            }
        });

        //分类监听器
        mFilterView.setOnItemCategoryClickListener(new HomeInfoFilterView.OnItemCategoryClickListener() {
            @Override
            public void onItemCategoryClick(int t) {
                if (t == 0) {
                    categoryCode = "";
                } else {
                    categoryCode = "&categoryCode=" + infoCategoryBean.getData().get(t - 1).getCodeNo();
                }
                param = paramNum + categoryCode + city + startTime;
                model.search(handler,
                        UrlConstant.HTTP_URL_INFO_SEARCH_BLOG, param);//查询资讯
                startLoading("正在加载中");
            }
        });
        //设置地方监听器
        mFilterView.setOnItemPlaceClickListener(new HomeInfoFilterView.OnItemPlaceClickListener() {
            @Override
            public void onItemPlaceClick(String c) {
                if (TextUtils.equals(c, "不限")) {
                    city = "";
                } else {
                    city = "&city=" + c;
                }
                param = paramNum + categoryCode + city + startTime;
                model.search(handler, UrlConstant.HTTP_URL_INFO_SEARCH_BLOG, param);//查询资讯
                startLoading("正在加载中");
            }
        });
        //设置时间监听器
        mFilterView.setOnItemDateClickListener(new HomeInfoFilterView.OnItemDateClickListener() {
            @Override
            public void onItemDateClick(int d) {
                if (d == -1) {
                    startTime = "";
                } else {
                    startTime = "&startTime=" + d;
                }
                param = paramNum + categoryCode + city + startTime;
                model.search(handler, UrlConstant.HTTP_URL_INFO_SEARCH_BLOG, param);//查询资讯
                startLoading("正在加载中");
            }
        });
        //设置广告监听器
        mHeaderAdvertlView.setOnItemAdvertClickListtener(new HeaderHomeInfoAdvertView.OnItemAdvertClickListtener() {
            @Override
            public void OnChannelClick(String title) {
                ToastUtil.show(HomeInfoActivity.this, title);
            }
        });
//        //设置频道监听器
//        mHeaderChannelView.setOnItemChannelClickListtener(new HeaderHomeInfoChannelView.OnItemChannelClickListtener() {
//            @Override
//            public void OnChannelClick(String title) {
//                ToastUtil.show(HomeInfoActivity.this, title);
//            }
//        });
    }


    /**
     * 设置数据
     */
    private void setDatas() {
        //添加数据
        infoFilterData = new InfoFilterData();
        //分类
        infoFilterData.setCategory(categorys);
        //地方
        infoFilterData.setPlace(ModelUtil.getCityData());
        //最近
        infoFilterData.setDate(ModelUtil.getNearData());
        mFilterView.setFilterData(this, infoFilterData);
    }

    /**
     * 展示资讯
     */
    private void showExInfo(List<CommonBean.Data> datas, boolean isLoadMore) {
        mHeight = DensityUtil.getWindowHeight(mActivity) - DensityUtil.dip2px(mActivity, 45) - 90;
        adapter = new SearchBlogAdapter(this, datas, mHeight);
        mSmoothListView.setAdapter(adapter);
        mSmoothListView.setLoadMoreEnable(false);
        isShow = true;
        filterViewPosition = mSmoothListView.getHeaderViewsCount() - 1;
    }


    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home_info_back:
                //返回
                finish();
                break;
        }
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, mSmoothListView, header);
    }

    //下拉刷新
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        model.search(handler, UrlConstant.HTTP_URL_INFO_SEARCH_BLOG, param);//查询资讯
    }

    //listview Itme的点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int count = mSmoothListView.getHeaderViewsCount();
        if (position > count - 1) {
            if (blogDatas.size() > 0) {
                startActivity(new Intent(this, WebViewActivity.class).putExtra(HandlerConstant.DETAIL_BLOG, blogDatas.get(position - count)));
            }
        }
    }

//    //下拉刷新
//    @Override
//    public void onRefresh() {
//
//    }

//    //上拉加载
//    @Override
//    public void onLoadMore() {
//        isRefreshing = true;
//        itemPerPage = itemPerPage + 10;
//        paramNum = "?page=" + page + "&itemPerPage=" + itemPerPage;
//        param = paramNum + categoryCode + city + startTime;
//        model.search(handler, UrlConstant.HTTP_URL_INFO_SEARCH_BLOG, param);//查询资讯
//    }

    //Handler
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.INFO_CATEGORY_SUCCESS://资讯分类
                    infoCategoryBean = (InfoCategoryBean) msg.obj;
                    datas = infoCategoryBean.getData();
                    categorys = new ArrayList<>();
                    categorys.add(new FilterEntity("不限"));
                    for (int i = 0; i < datas.size(); i++) {//获取分类名称
                        categorys.add(new FilterEntity(datas.get(i).getName()));
                    }
//                    mHeaderChannelView.showChannel(datas);
                    setDatas();
                    break;
                case HandlerConstant.INFO_HAINAN_ALL_CITY_SUCCESS://海南所有城市
                    infoPlaceBean = (InfoPlaceBean) msg.obj;
                    placeDatas = infoPlaceBean.getData();
                    places = new ArrayList<>();
                    for (int i = 0; i < placeDatas.size(); i++) {
                        if (placeDatas.get(i).getName().equals("海南")) {
                            placeSubDatas = placeDatas.get(i).getSubData();
                            for (int j = 0; j < placeSubDatas.size(); j++) {
                                places.add(new FilterEntity(placeSubDatas.get(j).getName()));
                            }
                        }
                    }
                    setDatas();
                    break;
                case HandlerConstant.SEARCH_SUCCESS://查询资讯
                    stopLoading();
                    infoBlogBean = (CommonBean) msg.obj;
                    showInfo(infoBlogBean);
                    break;
            }
        }
    }

    /**
     * 显示搜索数据
     */
    private void showInfo(CommonBean bean) {
        stopLoading();
        boolean isLoadMore = true;
        int COUNT = 5;
        if (blogDatas == null) {//首次进入
            blogDatas = bean.getData();
            if (blogDatas.size() < COUNT) {
                isLoadMore = false;
                if (blogDatas.size() > 0) {
                    for (int i = blogDatas.size(); i < COUNT; i++) {
                        blogDatas.add(bean.new Data());
                    }
                }
            }
            showExInfo(blogDatas, isLoadMore);
        } else {//上拉加载更多，或者下拉刷新，或者数据筛选
            if (isRefreshing) {//上拉加载更多
                isRefreshing = false;
                blogDatas.addAll(bean.getData());
                mSmoothListView.stopLoadMore();
                adapter.notifyDataSetChanged();
            } else {//下拉刷新，或者筛选
                blogDatas.clear();
                blogDatas.addAll(bean.getData());
                ptrInfo.refreshComplete();
                if (blogDatas.size() > 0) {
                    if (blogDatas.size() < COUNT) {//小于6个
                        for (int i = blogDatas.size(); i < COUNT; i++) {
                            blogDatas.add(bean.new Data());
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

}
