package com.ex.administrator.zhanhui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.SmoothListView.SmoothListView;
import com.ex.administrator.zhanhui.adapter.SearchTeamAdapter;
import com.ex.administrator.zhanhui.application.MyApplication;
import com.ex.administrator.zhanhui.constant.HandlerConstant;
import com.ex.administrator.zhanhui.entity.CommonBean;
import com.ex.administrator.zhanhui.entity.InfoPlaceBean;
import com.ex.administrator.zhanhui.entity.TypeBean;
import com.ex.administrator.zhanhui.model.HomeChannelModel;
import com.ex.administrator.zhanhui.model.TeamModel;
import com.ex.administrator.zhanhui.model.filter.FilterEntity;
import com.ex.administrator.zhanhui.util.ToastUtil;
import com.ex.administrator.zhanhui.view.ModelUtil;
import com.ex.administrator.zhanhui.view.homeFragmentTeamHeader.HeaderHomeFragmentTeamChannelView;
import com.ex.administrator.zhanhui.view.homeFragmentTeamHeader.HeaderHomeFragmentTeamFilterView;
import com.ex.administrator.zhanhui.view.homeFragmentTeamHeader.HomeFragmrntTeamFilterView;
import com.ex.administrator.zhanhui.view.homeFragmentTeamHeader.TeamFilterData;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class HomeFragmentTeamActivity extends BaseActivity implements
        SmoothListView.ISmoothListViewListener, View.OnClickListener {
    @ViewInject(R.id.sl_home_fagment_team)
    private SmoothListView mSmoothListView;

    @ViewInject(R.id.fv_home_fragment_team)
    private HomeFragmrntTeamFilterView mFilterView;

    @ViewInject(R.id.iv_home_fragment_team_back)
    private ImageView ivBack;

    private HeaderHomeFragmentTeamFilterView mHeaderFilterView;
    private HeaderHomeFragmentTeamChannelView mHeaderChannelView;
    private TeamFilterData filterData;
    private int filterPosition;//点击第几个筛选
    private int filterViewPosition = 2;//筛选视图的位置
    private boolean isStickyTop = false;//是否吸附在顶部
    private boolean isSmooll;//是否滑动中
    private boolean isShow = false;//是否滑显示
    private int filterViewTopMargin;//距离顶部的距离
    private View itemHeaderFilterView;
    private SearchTeamAdapter adapter;

    private HomeChannelModel cityModel = new HomeChannelModel();
    private TeamModel teamModel = new TeamModel();
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
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HandlerConstant.INFO_HAINAN_ALL_CITY_SUCCESS) {//获取海南所有城市
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
            }
            if (msg.what == HandlerConstant.TEAM_TYPE_SUCCESS) {//团购类型
                teamTypeBean = (TypeBean) msg.obj;
                teamTypeDatas = teamTypeBean.getData();
                teamType = new ArrayList<>();
                for (int i = 0; i < teamTypeBean.getData().size(); i++) {
                    teamType.add(new FilterEntity(teamTypeBean.getData().get(i).getName()));
                }
            }
            if (msg.what == HandlerConstant.SEARCH_TEAM_SUCCESS) {//搜索团购
                stopLoading();//停止加载动画
                searchTeamBean = (CommonBean) msg.obj;
                searchTeamDatas = searchTeamBean.getData();
                if (searchTeamDatas.size() < 8) {
                    for (int i = 0; i < 8; i++) {
                        searchTeamDatas.add(searchTeamBean.new Data());
                    }
                }
            }
            mHeaderChannelView.showChannel(teamTypeDatas);
            setDatas();
            if (searchTeamDatas != null) {
                showExhibitions(searchTeamDatas);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragment_team);
        x.view().inject(this);
        startLoading("正在加载中...");//开始加载动画
        cityModel.getHaiNanAllCity(handler);//获取城市
        teamModel.getTeamCategory(handler, name);//获取团购类型
        teamModel.searchTeam(handler);//获取搜索门票
        initview();
        setListener();
    }


    /**
     * 初始化控件
     */
    private void initview() {
        //添加频道头布局
        mHeaderChannelView = new HeaderHomeFragmentTeamChannelView(this);
        mHeaderChannelView.getView(mSmoothListView);
        //添加筛选头布局
        mHeaderFilterView = new HeaderHomeFragmentTeamFilterView(this);
        mHeaderFilterView.getView(mSmoothListView);
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        //返回
        ivBack.setOnClickListener(this);
        //筛选头布局监听器
        mHeaderFilterView.setOnFilterClickListener(new HeaderHomeFragmentTeamFilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterPosition = position;
                isSmooll = true;
                mSmoothListView.smoothScrollToPositionFromTop(filterViewPosition, 0);
            }
        });
//        真正的筛选视图监听器
        mFilterView.setOnFilterClickListener(new HomeFragmrntTeamFilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                mFilterView.show(position);
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
        mFilterView.setOnItemCategoryClickListener(new HomeFragmrntTeamFilterView.OnItemTypeClickListener() {
            @Override
            public void onItemTypeClick(String type) {
                ToastUtil.show(MyApplication.getmMyApplication().getApplicationContext(), type);
            }
        });
        //设置产地监听器
        mFilterView.setOnItemPlaceClickListener(new HomeFragmrntTeamFilterView.OnItemPlaceClickListener() {
            @Override
            public void onItemPlaceClick(String place) {
                ToastUtil.show(MyApplication.getmMyApplication().getApplicationContext(), place);
            }
        });
        //设置筛选监听器
        mFilterView.setOnItemSiftClickListener(new HomeFragmrntTeamFilterView.OnItemSiftClickListener() {
            @Override
            public void onItemSiftClick(int price) {
                ToastUtil.show(MyApplication.getmMyApplication().getApplicationContext(), price + "");
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
        filterData.setPlace(places);
        //筛选
        filterData.setType(ModelUtil.getTeamType());

        mFilterView.setFilterData(this, filterData);
    }

    /**
     * 展示展会
     */
    private void showExhibitions(List<CommonBean.Data> datas) {
        mSmoothListView.setRefreshEnable(true);
        mSmoothListView.setLoadMoreEnable(false);
        mSmoothListView.setSmoothListViewListener(this);
        adapter = new SearchTeamAdapter(this, datas);
        mSmoothListView.setAdapter(adapter);
        isShow = true;
        filterViewPosition = mSmoothListView.getHeaderViewsCount() - 1;
    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSmoothListView.stopRefresh();
                mSmoothListView.setRefreshTime("刚刚");
            }
        }, 2000);
    }

    /**
     * 上啦加载更多
     */
    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSmoothListView.stopLoadMore();
            }
        }, 2000);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home_fragment_team_back:
                //返回
                finish();
                break;
        }
    }
}
