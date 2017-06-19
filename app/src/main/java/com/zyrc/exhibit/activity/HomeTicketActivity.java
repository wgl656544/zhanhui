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
import com.zyrc.exhibit.util.ToastUtil;
import com.zyrc.exhibit.view.ModelUtil;
import com.zyrc.exhibit.view.homeTicketHeader.HeaderHomeTicketChannelView;
import com.zyrc.exhibit.view.homeTicketHeader.HeaderHomeTicketFilterView;
import com.zyrc.exhibit.view.homeTicketHeader.HomeTicketFilterView;
import com.zyrc.exhibit.view.homeTicketHeader.TicketFilterData;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 票务搜索页面
 * Created by Administrator on 2017/2/20 0020.
 */

public class HomeTicketActivity extends BaseActivity implements
        SmoothListView.ISmoothListViewListener, View.OnClickListener,
        HeaderHomeTicketChannelView.OnClickListener, PtrHandler, AdapterView.OnItemClickListener {

    @ViewInject(R.id.sl_home_fagment_ticket)
    private SmoothListView mSmoothListView;

    @ViewInject(R.id.fv_home_fragment_ticket)
    private HomeTicketFilterView mFilterView;

    @ViewInject(R.id.iv_home_ticket_back)
    private ImageView ivBack;

    @ViewInject(R.id.ptr_ticket)
    private PtrFrameLayout ptrTicket;

    private HeaderHomeTicketFilterView mHeaderFilterView;
    //    private HeaderHomeTicketChannelView mHeaderChannelView;
    private TicketFilterData filterData;
    private int filterPosition;//点击第几个筛选
    private int filterViewPosition = 2;//筛选视图的位置
    private boolean isStickyTop = false;//是否吸附在顶部
    private boolean isSmooll;//是否滑动中
    private boolean isShow = false;//是否显示
    private int filterViewTopMargin;//距离顶部的距离
    private View itemHeaderFilterView;

    private int COUNT = 6;
    private String param = "";//搜索参数
    private String paramNum = "";//搜索参数
    private String city = "";
    private String type = "";
    private String near = "";
    private String price = "";
    private int page = 1;//搜索页数
    private int itemPerPage = 20;//搜索条数
    private boolean isRefreshing = false;
    private String paramType = "name=pt-tk";
    private GetDataModel ticketModel = new GetDataModel();
    private SearchTicketAdapter adapter;
    private TypeBean ticketTypeBean;//门票类型
    private List<FilterEntity> ticketTypeDatas;//门票类型字符串
    private CommonBean searchTicketBean;//门票搜搜实体类
    private List<CommonBean.Data> ticketDatas;
    private int dataConut;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            stopLoading();
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS://搜索门票成功
                    searchTicketBean = (CommonBean) msg.obj;
                    showTicket(searchTicketBean);
                    break;
                case HandlerConstant.SEARCH_TYPE_SUCCESS://搜索门票类型成功
                    ticketTypeBean = (TypeBean) msg.obj;
                    ticketTypeDatas = new ArrayList<>();
                    ticketTypeDatas.add(new FilterEntity("不限"));
                    for (int i = 0; i < ticketTypeBean.getData().size(); i++) {
                        ticketTypeDatas.add(new FilterEntity(ticketTypeBean.getData().get(i).getName()));
                    }
                    setDatas();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ticket);
        x.view().inject(this);
        paramNum = "?page=" + page + "&itemsPerPage=" + itemPerPage;
        param = paramNum;
        startLoading("正在加载中...");//开始加载动画
        //获取门票类型
        ticketModel.getType(handler, UrlConstant.HTTP_URL_TICKET_TYPE, paramType);
        //搜索门票
        ticketModel.search(handler, UrlConstant.HTTP_URL_SEARCH_TICKET, param);
        initview();
        setListener();
    }


    /**
     * 初始化控件
     */
    private void initview() {
        mHeaderFilterView = new HeaderHomeTicketFilterView(this);
        mHeaderFilterView.getView(mSmoothListView);

        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(this);
        ptrTicket.setHeaderView(header);
        ptrTicket.addPtrUIHandler(header);
        ptrTicket.setPtrHandler(this);
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        //mSmoothListView item 点击事件
        mSmoothListView.setOnItemClickListener(this);
        //下拉刷新
        ptrTicket.setPtrHandler(this);
        //返回
        ivBack.setOnClickListener(this);
        //筛选头布局监听器
        mHeaderFilterView.setOnFilterClickListener(new HeaderHomeTicketFilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                mFilterView.show(position);
                mFilterView.setVisibility(View.VISIBLE);
                mSmoothListView.smoothScrollToPosition(0);
            }
        });
        //真正的筛选视图监听器
        mFilterView.setOnFilterClickListener(new HomeTicketFilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                mFilterView.show(position);
//                mFilterView.setVisibility(View.VISIBLE);
            }
        });
        //listview监听器
        mSmoothListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (itemHeaderFilterView == null) {
//                    itemHeaderFilterView = mSmoothListView.getChildAt(0);
//                }
//                if (itemHeaderFilterView != null) {
                filterViewTopMargin = mSmoothListView.getTop();
                if (filterViewTopMargin > 0) {
                    mFilterView.setVisibility(View.GONE);
                } else {
                    mFilterView.setVisibility(View.VISIBLE);
                }
//                }
//                // 处理筛选是否吸附在顶部
//                if ((filterViewTopMargin <= 0 || firstVisibleItem > filterViewPosition) && isShow) {
//                    isStickyTop = true; // 吸附在顶部
//                    mFilterView.setVisibility(View.VISIBLE);
//                } else {
//                    isStickyTop = false; // 没有吸附在顶部
//                    mFilterView.setVisibility(View.GONE);
//                }
//                //吸附在顶部并且在滑动中
//                if (isStickyTop && isSmooll) {
//                    isSmooll = false;
//                    mFilterView.show(filterPosition);
//                }
            }
        });
        //设置城市监听器
        mFilterView.setOnItemCityClickListener(new HomeTicketFilterView.OnItemCityClickListener() {
            @Override
            public void onItemCityClick(String c) {
                if(TextUtils.equals(c,"不限")){
                    city = "";
                } else {
                    city = "&city=" + c;
                }
                param = paramNum + city + type + near + price;
                ticketModel.search(handler, UrlConstant.HTTP_URL_SEARCH_TICKET, param);
                startLoading("正在加载中");
            }
        });
        //设置最近监听器
        mFilterView.setOnItemNearClickListener(new HomeTicketFilterView.OnItemNearClickListener() {
            @Override
            public void onItemNearClick(int n) {
                if (n == -1) {
                    near = "";
                } else {
                    near = "&startTime=" + n;
                }
                param = paramNum + city + type + near + price;
                ticketModel.search(handler, UrlConstant.HTTP_URL_SEARCH_TICKET, param);
                startLoading("正在加载中");
            }
        });
        //设置类型监听器
        mFilterView.setOnItemTypeClickListener(new HomeTicketFilterView.OnItemTypeClickListener() {
            @Override
            public void onItemTypeClick(int t) {
                if (t == 0) {
                    type = "";
                } else {
                    type = "&busiType=" + ticketTypeBean.getData().get(t - 1).getCode();
                }
                param = paramNum + city + type + near + price;
                ticketModel.search(handler, UrlConstant.HTTP_URL_SEARCH_TICKET, param);
                startLoading("正在加载中");
            }
        });
        //设置筛选监听器
        mFilterView.setOnItemSiftClickListener(new HomeTicketFilterView.OnItemSiftClickListener() {
            @Override
            public void onItemSiftClick(int p) {
                if(p == -1){
                    price = "";
                } else {
                    price = "&price=" + p;
                }
                param = paramNum + city + type + near + price;
                ticketModel.search(handler, UrlConstant.HTTP_URL_SEARCH_TICKET, param);
                startLoading("正在加载中");
            }
        });

//        //频道按钮监听器
//        mHeaderChannelView.setOnClickListener(this);
    }


    /**
     * 设置数据
     */
    private void setDatas() {
        //添加数据
        filterData = new TicketFilterData();
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
     * 展示门票
     */
    private void showExhibitions(List<CommonBean.Data> datas, boolean isLoadMore) {
        mSmoothListView.setRefreshEnable(false);
        mSmoothListView.setLoadMoreEnable(isLoadMore);
        mSmoothListView.setSmoothListViewListener(this);
        int mHeight = DensityUtil.getWindowHeight(this) - DensityUtil.dip2px(this, 45) - 90;
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
     * 上啦加载更多
     */
    @Override
    public void onLoadMore() {
        isRefreshing = true;
        itemPerPage = itemPerPage + 10;
        paramNum = "?page=" + page + "&itemsPerPage=" + itemPerPage;
        param = paramNum + city + type + near + price;
        ticketModel.search(handler, UrlConstant.HTTP_URL_SEARCH_TICKET, param);
    }

    //按钮点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.iv_home_ticket_back:
                finish();
                break;
        }
    }

    //频道按钮的点击事件
    @Override
    public void onClick(int position) {
        ToastUtil.show(this, "你点击了第" + position + "个");

    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        ticketModel.search(handler, UrlConstant.HTTP_URL_SEARCH_TICKET, param);
    }

    //item点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(ticketDatas.size() > 0){
            int count = mSmoothListView.getHeaderViewsCount();
            if (position > count - 1) {
                startActivity(new Intent(this, DetailBuyActivity.class).putExtra(HandlerConstant.DETAIL_BUY, ticketDatas.get(position - count)));
            }
        }
    }

    /**
     * 显示搜索的数据
     */
    private void showTicket(CommonBean bean) {
        boolean isLoadMore = true;
        int COUNT = 6;
        if (ticketDatas == null) {//首次进入
            ticketDatas = bean.getData();
            if (ticketDatas.size() > 0 && ticketDatas.size() < COUNT) {
                isLoadMore = false;
                for (int i = ticketDatas.size(); i < COUNT; i++) {
                    ticketDatas.add(bean.new Data());
                }
            }
            showExhibitions(ticketDatas, isLoadMore);
        } else {//上拉加载更多，或者下拉刷新，或者数据筛选
            if (isRefreshing) {//上拉加载更多
                isRefreshing = false;
                ticketDatas.addAll(bean.getData());
                mSmoothListView.stopLoadMore();
                adapter.notifyDataSetChanged();
            } else {//下拉刷新，或者筛选
                ticketDatas.clear();
                ticketDatas.addAll(bean.getData());
                ptrTicket.refreshComplete();
                if (ticketDatas.size() > 0) {
                    if (ticketDatas.size() < COUNT) {//小于6个
                        isLoadMore = false;
                        for (int i = ticketDatas.size(); i < COUNT; i++) {
                            ticketDatas.add(bean.new Data());
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
