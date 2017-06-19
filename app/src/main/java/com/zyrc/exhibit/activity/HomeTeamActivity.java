package com.zyrc.exhibit.activity;

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

import com.example.mylibrary.base.BaseActivity;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.SmoothListView.SmoothListView;
import com.zyrc.exhibit.adapter.SearchTeamAdapter;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.entity.InfoPlaceBean;
import com.zyrc.exhibit.entity.TypeBean;
import com.zyrc.exhibit.model.GetDataModel;
import com.zyrc.exhibit.model.filter.FilterEntity;
import com.zyrc.exhibit.util.DensityUtil;
import com.zyrc.exhibit.view.ModelUtil;
import com.zyrc.exhibit.view.homeTeamHeader.HeaderHomeTeamChannelView;
import com.zyrc.exhibit.view.homeTeamHeader.HeaderHomeTeamFilterView;
import com.zyrc.exhibit.view.homeTeamHeader.HomeTeamFilterView;
import com.zyrc.exhibit.view.homeTeamHeader.TeamFilterData;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 所搜团购页面
 * Created by Administrator on 2017/2/20 0020.
 */

public class HomeTeamActivity extends BaseActivity implements
        SmoothListView.ISmoothListViewListener, View.OnClickListener, AdapterView.OnItemClickListener, PtrHandler {
    @ViewInject(R.id.sl_home_fagment_team)
    private SmoothListView mSmoothListView;

    @ViewInject(R.id.fv_home_fragment_team)
    private HomeTeamFilterView mFilterView;

    @ViewInject(R.id.ptr_team)
    private PtrFrameLayout ptrTeam;

    @ViewInject(R.id.iv_team_back)
    private ImageView ivBack;

    private HeaderHomeTeamFilterView mHeaderFilterView;
    private HeaderHomeTeamChannelView mHeaderChannelView;
    private TeamFilterData filterData;
    private int filterPosition;//点击第几个筛选
    private int filterViewPosition = 2;//筛选视图的位置
    private boolean isStickyTop = false;//是否吸附在顶部
    private boolean isSmooll;//是否滑动中
    private boolean isShow = false;//是否滑显示
    private int filterViewTopMargin;//距离顶部的距离
    private View itemHeaderFilterView;
    private SearchTeamAdapter adapter;

    private boolean isRefreshing = false;
    private GetDataModel model = new GetDataModel();
    private String name = "name=pt-tg-";
    private InfoPlaceBean infoPlaceBean;
    private List<InfoPlaceBean.Data> placeDatas;
    private List<InfoPlaceBean.SubData> placeSubDatas;
    private List<FilterEntity> places;//所有城市数据
    private TypeBean teamTypeBean;//团购类型
    private List<TypeBean.Data> teamTypeDatas;
    private List<FilterEntity> teamType;//团购类型
    private CommonBean searchTeamBean;//搜索团购bean
    private List<CommonBean.Data> searchTeamDatas;

    private String paramNum = "";//搜索参数
    private int page = 1;//搜索页数
    private int itemPerPage = 20;//搜索条数
    private String param = "";//搜索参数
    private String city = "";
    private String type = "";
    private String near = "";
    private String sift = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            stopLoading();//停止加载动画
            switch (msg.what) {
                case HandlerConstant.INFO_HAINAN_ALL_CITY_SUCCESS://搜索海南所有城市
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
                    break;
                case HandlerConstant.SEARCH_TYPE_SUCCESS://团购的类型
                    teamTypeBean = (TypeBean) msg.obj;
                    teamType = new ArrayList<>();
                    teamType.add(new FilterEntity("不限"));
                    for (int i = 0; i < teamTypeBean.getData().size(); i++) {
                        teamType.add(new FilterEntity(teamTypeBean.getData().get(i).getName()));//下拉筛选的数据
                    }
                    mHeaderChannelView.showChannel(teamTypeBean.getData());//频道的数据
                    setDatas();
                    break;
                case HandlerConstant.SEARCH_SUCCESS://搜索团购数据
                    searchTeamBean = (CommonBean) msg.obj;
                    showTeam(searchTeamBean);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_team);
        x.view().inject(this);
        paramNum = "?page=" + page + "&itemsPerPage=" + itemPerPage;
        param = paramNum;
        startLoading("正在加载中...");//开始加载动画
        model.getHaiNanAllCity(handler);//获取城市
        model.getType(handler, UrlConstant.HTTP_URL_TEAM_CATEGORY, name);//获取团购类型
        model.search(handler, UrlConstant.HTTP_URL_SEARCH_TEAM, param);//获取搜索门票
        initview();
        setListener();
    }


    /**
     * 初始化控件
     */
    private void initview() {
        //添加频道头布局
        mHeaderChannelView = new HeaderHomeTeamChannelView(this);
        mHeaderChannelView.getView(mSmoothListView);
        //添加筛选头布局
        mHeaderFilterView = new HeaderHomeTeamFilterView(this);
        mHeaderFilterView.getView(mSmoothListView);

        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(this);
        ptrTeam.setHeaderView(header);
        ptrTeam.addPtrUIHandler(header);
        ptrTeam.setPtrHandler(this);
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        //mSmoothListView 点击事件
        mSmoothListView.setOnItemClickListener(this);
        //返回
        ivBack.setOnClickListener(this);
        //筛选头布局监听器
        mHeaderFilterView.setOnFilterClickListener(new HeaderHomeTeamFilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                if (teamType != null) {
                    filterPosition = position;
                    isSmooll = true;
                    mSmoothListView.smoothScrollToPositionFromTop(filterViewPosition, 0);
                }
            }
        });
//        真正的筛选视图监听器
        mFilterView.setOnFilterClickListener(new HomeTeamFilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                if (teamType != null) {
                    mFilterView.show(position);
                }

            }
        });
//        listview监听器
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
        //设置类型监听器
        mFilterView.setOnItemCategoryClickListener(new HomeTeamFilterView.OnItemTypeClickListener() {
            @Override
            public void onItemTypeClick(int t) {
                if (t == 0) {
                    type = "";
                } else {
                    type = "&busiType=" + teamTypeBean.getData().get(t - 1).getCode();
                }
                param = paramNum + city + type + near;
                mSmoothListView.smoothScrollToPosition(0);
                ptrTeam.autoRefresh();
            }
        });
        //设置产地监听器
        mFilterView.setOnItemPlaceClickListener(new HomeTeamFilterView.OnItemPlaceClickListener() {
            @Override
            public void onItemPlaceClick(String p) {
                if (TextUtils.equals(p, "不限")) {
                    city = "";
                } else {
                    city = "&city=" + p;
                }
                param = paramNum + city + type + near;
                mSmoothListView.smoothScrollToPosition(0);
                ptrTeam.autoRefresh();
            }
        });
        //设置筛选监听器
        mFilterView.setOnItemSiftClickListener(new HomeTeamFilterView.OnItemSiftClickListener() {
            @Override
            public void onItemSiftClick(int p) {
                if (p == -1) {
                    near = "";
                } else {
                    near = "&price=" + p;
                }
                param = paramNum + city + type + near;
                mSmoothListView.smoothScrollToPosition(0);
                ptrTeam.autoRefresh();
            }
        });
    }


    /**
     * 设置数据
     */
    private void setDatas() {
        //添加数据
        filterData = new TeamFilterData();
        //类型
        filterData.setCategory(teamType);
        //产地
        filterData.setPlace(ModelUtil.getCityData());
        //筛选
        filterData.setType(ModelUtil.getTeamType());

        mFilterView.setFilterData(this, filterData);
    }

    /**
     * 显示团购产品
     */
    private void showTeamProduct(List<CommonBean.Data> datas, boolean isLoadMore) {
        mSmoothListView.setRefreshEnable(false);
        mSmoothListView.setLoadMoreEnable(false);
        mSmoothListView.setSmoothListViewListener(this);
        int mHeight = DensityUtil.getWindowHeight(this) - DensityUtil.dip2px(this, 45) - 90;
        adapter = new SearchTeamAdapter(this, datas, mHeight);
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
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_team_back://返回
                finish();
                break;
        }
    }

    //item点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (searchTeamDatas.size() != 0) {
            int count = mSmoothListView.getHeaderViewsCount();
            if (position > count - 1) {
                startActivity(new Intent(this, DetailBuyActivity.class).putExtra(HandlerConstant.DETAIL_BUY, searchTeamDatas.get(position - 3)));
            }
        }
    }

    //下拉刷新
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        model.search(handler, UrlConstant.HTTP_URL_SEARCH_TEAM, param);//获取搜索门票
    }

    /**
     * 显示团购
     *
     * @param data 请求的数据
     */

    private void showTeam(CommonBean data) {
        stopLoading();
        boolean isLoadMore = true;
        int COUNT = 6;
        if (searchTeamDatas == null) {//首次进入
            stopLoading();
            searchTeamDatas = data.getData();
            if (searchTeamDatas.size() < COUNT && searchTeamDatas.size() > 0) {//集合个数小于6个
                isLoadMore = false;
                for (int i = searchTeamDatas.size(); i < COUNT; i++) {
                    searchTeamDatas.add(data.new Data());
                }
            }
            showTeamProduct(searchTeamDatas, isLoadMore);//显示展会
        } else {
            if (isRefreshing) {//上拉加载更多
                isRefreshing = false;
                searchTeamDatas.addAll(data.getData());
                mSmoothListView.stopLoadMore();
                adapter.notifyDataSetChanged();
            } else {//下拉刷新
                searchTeamDatas.clear();
                searchTeamDatas.addAll(data.getData());
                ptrTeam.refreshComplete();
                if (data.getData().size() > 0) {
                    if (data.getData().size() < COUNT) {//个数小于6个
                        isLoadMore = false;
                        for (int i = data.getData().size(); i < COUNT; i++) {
                            searchTeamDatas.add(data.new Data());
                        }
                    } else {
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
