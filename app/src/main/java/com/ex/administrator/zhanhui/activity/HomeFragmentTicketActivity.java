package com.ex.administrator.zhanhui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.SmoothListView.SmoothListView;
import com.ex.administrator.zhanhui.adapter.SearchTicketAdapter;
import com.ex.administrator.zhanhui.constant.HandlerConstant;
import com.ex.administrator.zhanhui.constant.UrlConstant;
import com.ex.administrator.zhanhui.entity.CommonBean;
import com.ex.administrator.zhanhui.entity.TypeBean;
import com.ex.administrator.zhanhui.model.HomeChannelModel;
import com.ex.administrator.zhanhui.model.filter.FilterEntity;
import com.ex.administrator.zhanhui.util.ToastUtil;
import com.ex.administrator.zhanhui.view.ModelUtil;
import com.ex.administrator.zhanhui.view.homeFragmentTicketHeader.HeaderHomeFragmentTicketChannelView;
import com.ex.administrator.zhanhui.view.homeFragmentTicketHeader.HeaderHomeFragmentTicketFilterView;
import com.ex.administrator.zhanhui.view.homeFragmentTicketHeader.HomeFragmentTicketFilterView;
import com.ex.administrator.zhanhui.view.homeFragmentTicketHeader.TicketFilterData;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class HomeFragmentTicketActivity extends BaseActivity implements
        SmoothListView.ISmoothListViewListener, View.OnClickListener, HeaderHomeFragmentTicketChannelView.OnClickListener {

    @ViewInject(R.id.sl_home_fagment_ticket)
    private SmoothListView mSmoothListView;

    @ViewInject(R.id.fv_home_fragment_ticket)
    private HomeFragmentTicketFilterView mFilterView;

    @ViewInject(R.id.rl_home_fragment_ticket_back)
    private RelativeLayout rlBack;

    private HeaderHomeFragmentTicketFilterView mHeaderFilterView;
    private HeaderHomeFragmentTicketChannelView mHeaderChannelView;
    private TicketFilterData filterData;
    private int filterPosition;//点击第几个筛选
    private int filterViewPosition = 2;//筛选视图的位置
    private boolean isStickyTop = false;//是否吸附在顶部
    private boolean isSmooll;//是否滑动中
    private boolean isShow = false;//是否显示
    private int filterViewTopMargin;//距离顶部的距离
    private View itemHeaderFilterView;

    private String name = "name=pt-tk";
    private HomeChannelModel ticketModel = new HomeChannelModel();
    private SearchTicketAdapter adapter;
    private TypeBean ticketTypeBean;//门票类型
    private List<FilterEntity> ticketTypeDatas;//门票类型字符串
    private CommonBean searchTicketBean;//门票搜搜实体类
    private List<CommonBean.Data> searchTicketDatas;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HandlerConstant.TICKET_TYPE_SUCCESS) {//门票类型
                ticketTypeBean = (TypeBean) msg.obj;
                ticketTypeDatas = new ArrayList<>();
                for (int i = 0; i < ticketTypeBean.getData().size(); i++) {
                    ticketTypeDatas.add(new FilterEntity(ticketTypeBean.getData().get(i).getName()));
                }
            }
            if (msg.what == HandlerConstant.SEARCH_SUCCESS) {//搜索门票
                stopLoading();//停止加载动画
                searchTicketBean = (CommonBean) msg.obj;
                searchTicketDatas = searchTicketBean.getData();
                if (searchTicketDatas.size() < 8) {//数据不满一屏幕
                    for (int i = 0; i < 8; i++) {
                        searchTicketDatas.add(searchTicketBean.new Data());
                    }
                }
            }
            setDatas();
            if (searchTicketDatas != null) {
                showExhibitions(searchTicketDatas);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragment_ticket);
        x.view().inject(this);
        try {
            startLoading("正在加载中...");//开始加载动画
            //获取门票类型
            ticketModel.getTicketType(handler, name);
            //搜索门票
            ticketModel.search(handler, UrlConstant.HTTP_URL_SEARCH_TICKET,"");

        } catch (Exception e) {
            e.printStackTrace();
        }
        initview();
        setListener();
    }


    /**
     * 初始化控件
     */
    private void initview() {
        //添加频道头布局
        mHeaderChannelView = new HeaderHomeFragmentTicketChannelView(this);
        mHeaderChannelView.getView(mSmoothListView);
        //添加筛选头布局
        mHeaderFilterView = new HeaderHomeFragmentTicketFilterView(this);
        mHeaderFilterView.getView(mSmoothListView);

    }

    /**
     * 设置监听器
     */
    private void setListener() {
        //返回
        rlBack.setOnClickListener(this);
        //筛选头布局监听器
        mHeaderFilterView.setOnFilterClickListener(new HeaderHomeFragmentTicketFilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterPosition = position;
                isSmooll = true;
                mSmoothListView.smoothScrollToPositionFromTop(filterViewPosition, 0);
            }
        });
        //真正的筛选视图监听器
        mFilterView.setOnFilterClickListener(new HomeFragmentTicketFilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                mFilterView.show(position);
            }
        });
        //listview监听器
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
        mFilterView.setOnItemCityClickListener(new HomeFragmentTicketFilterView.OnItemCityClickListener() {
            @Override
            public void onItemCityClick(String city) {

            }
        });
        //设置最近监听器
        mFilterView.setOnItemNearClickListener(new HomeFragmentTicketFilterView.OnItemNearClickListener() {
            @Override
            public void onItemNearClick(int near) {

            }
        });
        //设置类型监听器
        mFilterView.setOnItemTypeClickListener(new HomeFragmentTicketFilterView.OnItemTypeClickListener() {
            @Override
            public void onItemTypeClick(String type) {

            }
        });
        //设置筛选监听器
        mFilterView.setOnItemSiftClickListener(new HomeFragmentTicketFilterView.OnItemSiftClickListener() {
            @Override
            public void onItemSiftClick(int price) {

            }
        });

        //频道按钮监听器
        mHeaderChannelView.setOnClickListener(this);
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
     * 展示展会
     */
    private void showExhibitions(List<CommonBean.Data> datas) {
        mSmoothListView.setRefreshEnable(true);
        mSmoothListView.setLoadMoreEnable(false);
        mSmoothListView.setSmoothListViewListener(this);
        adapter = new SearchTicketAdapter(this, datas);
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

    //按钮点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.rl_home_fragment_ticket_back:
                finish();
                break;
        }
    }

    //频道按钮的点击事件
    @Override
    public void onClick(int position) {
        ToastUtil.show(this, "你点击了第" + position + "个");

    }
}
