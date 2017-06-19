package com.zyrc.exhibit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.mylibrary.base.BaseActivity;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.SmoothListView.SmoothListView;
import com.zyrc.exhibit.adapter.SearchTicketAdapter;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.entity.TypeBean;
import com.zyrc.exhibit.model.GetDataModel;
import com.zyrc.exhibit.model.filter.FilterEntity;
import com.zyrc.exhibit.util.DensityUtil;
import com.zyrc.exhibit.view.ModelUtil;
import com.zyrc.exhibit.view.homeBusiHeader.BusiFilterData;
import com.zyrc.exhibit.view.homeBusiHeader.HeaderHomeBusiChannelView;
import com.zyrc.exhibit.view.homeBusiHeader.HeaderHomeBusiFilterView;
import com.zyrc.exhibit.view.homeBusiHeader.HomeBusiFilterView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 招商页面
 * Created by Administrator on 2017/2/20 0020.
 */

public class HomeBusiActivity extends BaseActivity implements
        SmoothListView.ISmoothListViewListener, View.OnClickListener, AdapterView.OnItemClickListener, PtrHandler {
    @ViewInject(R.id.sl_home_fragment_busi)
    private SmoothListView mSmoothListView;
    @ViewInject(R.id.fv_home_fragment_busi)
    private HomeBusiFilterView mFilterView;//筛选
    @ViewInject(R.id.iv_home_busi_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.ptr_busi)
    private PtrFrameLayout ptrBusi;

    private HeaderHomeBusiFilterView mHeaderFilterView;//筛选头布局
    private int filterPosition;//点击第几个筛选
    private int filterViewPosition = 2;//筛选视图的位置
    private boolean isStickyTop = false;//是否吸附在顶部
    private boolean isSmooll;//是否滑动中
    private boolean isShow = false;//是否显示
    private int filterViewTopMargin;//距离顶部的距离
    private View itemHeaderFilterView;
    private SearchTicketAdapter adapter;
    private boolean isRefreshing = false;

    private String name = "?name=ex-";
    private String paramNum = "";//搜索参数
    private String param = "";//搜索参数
    private String city = "";
    private String type = "";
    private String near = "";
    private String sift = "";
    private int page = 1;//搜索页数
    private int itemPerPage = 20;//搜索条数
    private GetDataModel model = new GetDataModel();
    private TypeBean ticketTypeBean;
    private List<FilterEntity> ticketTypeDatas;//门票类型字符串
    private List<CommonBean.Data> searchTicketDatas;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            stopLoading();//停止加载动画
            switch (msg.what) {
                case HandlerConstant.SEARCH_TYPE_SUCCESS://搜索类型
                    ticketTypeBean = (TypeBean) msg.obj;
                    ticketTypeDatas = new ArrayList<>();
                    ticketTypeDatas.add(new FilterEntity("不限"));
                    for (int i = 0; i < ticketTypeBean.getData().size(); i++) {
                        ticketTypeDatas.add(new FilterEntity(ticketTypeBean.getData().get(i).getName()));
                    }
                    setDatas();//设置数据
                    break;
                case HandlerConstant.SEARCH_SUCCESS://搜索招商数据
                    CommonBean searchTicketBean = (CommonBean) msg.obj;
                    showInfo(searchTicketBean);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_busi);
        x.view().inject(this);
        startLoading("正在加载中...");//开始加载动画
        //获取门票类型
        model.getType(handler, UrlConstant.HTTP_URL_INVITE_TYPE, name);
        //搜索招商
        paramNum = "?page=" + page + "&itemsPerPage=" + itemPerPage;
        param = paramNum;
        model.search(handler, UrlConstant.HTTP_URL_SEARCH_INVITE, param);
        initview();
        setListener();
    }


    /**
     * 初始化控件
     */
    private void initview() {
        //添加频道头布局
        HeaderHomeBusiChannelView mHeaderChannelView = new HeaderHomeBusiChannelView(this);
        mHeaderChannelView.getView(mSmoothListView);
        //添加筛选头布局
        mHeaderFilterView = new HeaderHomeBusiFilterView(this);
        mHeaderFilterView.getView(mSmoothListView);

        //下拉刷新
        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(this);
        ptrBusi.setHeaderView(header);
        ptrBusi.addPtrUIHandler(header);
        ptrBusi.setPtrHandler(this);
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        //设置监听器
        mSmoothListView.setOnItemClickListener(this);
        //返回
        ivBack.setOnClickListener(this);
        //筛选头布局监听器
        mHeaderFilterView.setOnFilterClickListener(new HeaderHomeBusiFilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterPosition = position;
                isSmooll = true;
                mSmoothListView.smoothScrollToPositionFromTop(filterViewPosition, 0);
            }
        });
        //真正的筛选视图监听器
        mFilterView.setOnFilterClickListener(new HomeBusiFilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                if (ticketTypeDatas != null && ticketTypeDatas.size() > 0) {
                    mFilterView.show(position);
                }
            }
        });
        //listView监听器
        mSmoothListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

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
        //设置城市监听器
        mFilterView.setOnItemCityClickListener(new HomeBusiFilterView.OnItemCityClickListener() {
            @Override
            public void onItemCityClick(String c) {
                if (TextUtils.equals(c, "不限")) {
                    city = "";
                } else {
                    city = "&city=" + c;
                }
                param = paramNum + city + type + near + sift;
                startLoading("加载中");
                model.search(handler, UrlConstant.HTTP_URL_SEARCH_INVITE, param);
            }
        });
        //设置最近监听器
        mFilterView.setOnItemNearClickListener(new HomeBusiFilterView.OnItemNearClickListener() {
            @Override
            public void onItemNearClick(int n) {
                if (n == -1) {
                    near = "";
                } else {
                    near = "&startTime=" + n;
                }
                param = paramNum + city + type + near + sift;
                startLoading("加载中");
                model.search(handler, UrlConstant.HTTP_URL_SEARCH_INVITE, param);
            }
        });
        //设置类型监听器
        mFilterView.setOnItemTypeClickListener(new HomeBusiFilterView.OnItemTypeClickListener() {
            @Override
            public void onItemTypeClick(int t) {
                if (t == 0) {
                    type = "";
                } else {
                    type = "&busiType=" + ticketTypeBean.getData().get(t - 1).getCode();
                }
                param = paramNum + city + type + near + sift;
                startLoading("加载中");
                model.search(handler, UrlConstant.HTTP_URL_SEARCH_INVITE, param);
            }
        });
        //设置筛选监听器
        mFilterView.setOnItemSiftClickListener(new HomeBusiFilterView.OnItemSiftClickListener() {
            @Override
            public void onItemSiftClick(int p) {
                if (p == -1) {
                    sift = "";
                } else {
                    sift = "&attendCount=" + p;
                }
                param = paramNum + city + type + near + sift;
                startLoading("加载中");
                model.search(handler, UrlConstant.HTTP_URL_SEARCH_INVITE, param);
            }
        });
    }


    /**
     * 设置数据
     */
    private void setDatas() {
        //添加数据
        BusiFilterData filterData = new BusiFilterData();
        //城市
        filterData.setCity(ModelUtil.getCityData());
        //最近
        filterData.setNear(ModelUtil.getNearData());
        //类型
        filterData.setType(ticketTypeDatas);
        //筛选
        filterData.setSift(ModelUtil.getTeamType());

        mFilterView.setFilterData(this, filterData);
    }

    /**
     * 展示展会
     */
    private void showExhibitions(List<CommonBean.Data> datas, boolean isLoadMore) {
        mSmoothListView.setRefreshEnable(false);
        mSmoothListView.setLoadMoreEnable(false);
        mSmoothListView.setSmoothListViewListener(this);
        int mHeight = DensityUtil.getWindowHeight(this) - 80 - 90;
        adapter = new SearchTicketAdapter(this, datas, mHeight);
        mSmoothListView.setAdapter(adapter);
        isShow = true;
        filterViewPosition = mSmoothListView.getHeaderViewsCount() - 1;
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMore() {
    }

    //事件的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.iv_home_busi_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (searchTicketDatas.size() > 0) {
            int count = mSmoothListView.getHeaderViewsCount();
            if (position > count - 1) {
                startActivity(new Intent(this, DetailBuyActivity.class).putExtra(HandlerConstant.DETAIL_BUY, searchTicketDatas.get(position - count)));
            }
        }
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {

    }

    /**
     * 显示搜索数据
     */
    private void showInfo(CommonBean bean) {
        stopLoading();
        boolean isLoadMore = true;
        int COUNT = 5;
        if (searchTicketDatas == null) {//首次进入
            searchTicketDatas = bean.getData();
            if (searchTicketDatas.size() < COUNT) {
                isLoadMore = false;
                if (searchTicketDatas.size() > 0) {
                    for (int i = searchTicketDatas.size(); i < COUNT; i++) {
                        searchTicketDatas.add(bean.new Data());
                    }
                }
            }
            showExhibitions(searchTicketDatas, isLoadMore);
        } else {//上拉加载更多，或者下拉刷新，或者数据筛选
            if (isRefreshing) {//上拉加载更多
                isRefreshing = false;
                searchTicketDatas.addAll(bean.getData());
                mSmoothListView.stopLoadMore();
                adapter.notifyDataSetChanged();
            } else {//下拉刷新，或者筛选
                searchTicketDatas.clear();
                searchTicketDatas.addAll(bean.getData());
                ptrBusi.refreshComplete();
                if (searchTicketDatas.size() > 0) {
                    if (searchTicketDatas.size() < COUNT) {//小于6个
                        isLoadMore = false;
                        for (int i = searchTicketDatas.size(); i < COUNT; i++) {
                            searchTicketDatas.add(bean.new Data());
                        }
                    } else {//大于6个
                        isLoadMore = true;
                    }
                } else {
                    isLoadMore = false;
                }
                mSmoothListView.setLoadMoreEnable(isLoadMore);
                adapter.notifyDataSetChanged();
            }
        }
    }

}
