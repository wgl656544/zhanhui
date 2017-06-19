package com.zyrc.exhibit.activity;

import android.app.Activity;
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
import android.widget.LinearLayout;

import com.example.mylibrary.base.BaseActivity;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.SmoothListView.SmoothListView;
import com.zyrc.exhibit.adapter.SearchExhibitAdapter;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.entity.TypeBean;
import com.zyrc.exhibit.model.GetDataModel;
import com.zyrc.exhibit.model.filter.FilterEntity;
import com.zyrc.exhibit.util.DensityUtil;
import com.zyrc.exhibit.util.ToastUtil;
import com.zyrc.exhibit.view.ModelUtil;
import com.zyrc.exhibit.view.findEXheader.FilterData;
import com.zyrc.exhibit.view.findEXheader.FilterView;
import com.zyrc.exhibit.view.findEXheader.HeaderAdvertView;
import com.zyrc.exhibit.view.findEXheader.HeaderChannelView;
import com.zyrc.exhibit.view.findEXheader.HeaderFilterView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 展会搜索页面
 * Created by Administrator on 2017/2/20 0020.
 */

public class HomeFindEXActivity extends BaseActivity implements
        SmoothListView.ISmoothListViewListener, View.OnClickListener, PtrHandler, AdapterView.OnItemClickListener {
    @ViewInject(R.id.lv_find_exhibition)
    private SmoothListView mSmoothListView;
    @ViewInject(R.id.fv_find_exhibition)
    private FilterView mFilterView;//筛选
    @ViewInject(R.id.iv_findex_back)
    private ImageView ivBack;
    @ViewInject(R.id.ptr_findex)
    private PtrFrameLayout ptrFindEx;
    @ViewInject(R.id.iv_findex_location)
    private ImageView ivLocation;
    @ViewInject(R.id.ll_find_ex_title)
    private LinearLayout llTitle;//标题

    private Activity mActivity;
    private HeaderFilterView mHeaderFilterView;
    private HeaderChannelView mHeaderChannelView;
    private HeaderAdvertView mHeaderAdvertlView;
    private FilterData filterData;
    private int filterPosition;//点击第几个筛选
    private int filterViewPosition = 3;//筛选视图的位置
    private boolean isStickyTop = false;//是否吸附在顶部
    private boolean isSmooll;//是否滑动中
    private boolean isShow = false;//数据是否加载完毕
    private int filterViewTopMargin;//距离顶部的距离
    private View itemHeaderFilterView;
    private GetDataModel model = new GetDataModel();//展会搜索业页面，业务对象
    private TypeBean exhibitionTypeBean;//展会类型对象实体类
    private List<FilterEntity> exType;//展会类型
    private CommonBean searchExhibitBean;//搜索展会成功实体类
    private List<CommonBean.Data> exDatas;//具体展会实体类
    private SearchExhibitAdapter adapter;
    private boolean isRefreshing = false;
    private String paramNum = "";//搜索参数
    private String param = "";//搜索参数
    private String city = "";
    private String type = "";
    private String near = "";
    private String sift = "";
    private int page = 1;//搜索页数
    private int itemPerPage = 20;//搜索条数
    private final String branch = "branch";//分论坛
    private final String confence = "confence";//会议型(主论坛)
    private final String trade = "trade";//博览会
    private int mHeight;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HandlerConstant.SEARCH_TYPE_SUCCESS) {//类型
                exhibitionTypeBean = (TypeBean) msg.obj;
                exType = new ArrayList<>();
                exType.add(new FilterEntity("不限"));
                for (int i = 0; i < exhibitionTypeBean.getData().size(); i++) {
                    exType.add(new FilterEntity(exhibitionTypeBean.getData().get(i).getName()));
                }
                //设置4个筛选的数据
                setDatas();
            }
            if (msg.what == HandlerConstant.SEARCH_SUCCESS) {//搜索展会
                searchExhibitBean = (CommonBean) msg.obj;
                showExhibi(searchExhibitBean);
            }

            if (msg.what == HandlerConstant.REQUEST_FAIL) {//请求成功，返回失败
                stopLoading();
                ptrFindEx.refreshComplete();
                ToastUtil.show(HomeFindEXActivity.this, (String) msg.obj);
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hone_find_ex);
        x.view().inject(this);
        mActivity = this;
        CommonBean bean = (CommonBean) getIntent().getSerializableExtra("data");
        paramNum = "?page=" + page + "&itemsPerPage=" + itemPerPage;
        initview();
        setListener();
        if (bean != null) {
            showSearchData(bean);
        } else {
            //获取展会类型
            getExhibitionType();
            //获取展会
            getExhibition();
        }
    }


    /**
     * 初始化控件
     */
    private void initview() {
        //添加广告头布局
        mHeaderAdvertlView = new HeaderAdvertView(this);
        mHeaderAdvertlView = new HeaderAdvertView(this);
        mHeaderAdvertlView.getView(mSmoothListView);
        //添加频道头布局
        mHeaderChannelView = new HeaderChannelView(this);
        mHeaderChannelView.getView(mSmoothListView);
        //添加筛选头布局
        mHeaderFilterView = new HeaderFilterView(this);
        mHeaderFilterView.getView(mSmoothListView);

        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(this);
        ptrFindEx.setHeaderView(header);
        ptrFindEx.addPtrUIHandler(header);
        ptrFindEx.setPtrHandler(this);
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        //地图
        ivLocation.setOnClickListener(this);
        //item点击事件
        mSmoothListView.setOnItemClickListener(this);
        //下拉刷新
        ptrFindEx.setPtrHandler(this);
        //返回
        ivBack.setOnClickListener(this);
        //广告点击事件
        mHeaderAdvertlView.setOnItemAdvertClickListtener(new HeaderAdvertView.OnItemAdvertClickListtener() {
            @Override
            public void OnChannelClick(CommonBean.Data data) {
                startActivity(new Intent(HomeFindEXActivity.this, WebViewActivity.class).putExtra(HandlerConstant.DETAIL_BLOG, data));
            }
        });
        //筛选头布局监听器
        mHeaderFilterView.setOnFilterClickListener(new HeaderFilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterPosition = position;
                isSmooll = true;
                mSmoothListView.smoothScrollToPositionFromTop(filterViewPosition, 0);
            }
        });
        //真正的筛选视图监听器
        mFilterView.setOnFilterClickListener(new FilterView.OnFilterClickListener() {
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

        //设置城市监听方法
        mFilterView.setOnItemCityClickListener(new FilterView.OnItemCityClickListener() {
            @Override
            public void onItemCityClick(String c) {
                if (TextUtils.equals(c, "不限")) {
                    city = "";
                } else {
                    city = "&city=" + c;
                }
                param = paramNum + city + type + near + sift;
                mSmoothListView.smoothScrollToPosition(0);
                ptrFindEx.autoRefresh();
            }
        });
        //设置类型监听方法
        mFilterView.setOnItemTypeClickListener(new FilterView.OnItemTypeClickListener() {
            @Override
            public void onItemTypeClick(int t) {
                if (t == 0) {
                    type = "";
                } else {
                    type = "&busiType=" + exhibitionTypeBean.getData().get(t - 1).getCode();
                }
                param = paramNum + city + type + near + sift;
                mSmoothListView.smoothScrollToPosition(0);
                ptrFindEx.autoRefresh(true, 250);
            }
        });
        //设置最近监听方法
        mFilterView.setOnItemNearClickListener(new FilterView.OnItemNearClickListener() {
            @Override
            public void onItemNearClick(int n) {
                if (n == -1) {
                    near = "";
                } else {
                    near = "&startTime=" + n;
                }
                param = paramNum + city + type + near + sift;
                mSmoothListView.smoothScrollToPosition(0);
                ptrFindEx.autoRefresh(true, 250);
            }
        });
        //设置筛选监听方法
        mFilterView.setOnItemSiftClickListener(new FilterView.OnItemSiftClickListener() {
            @Override
            public void onItemSiftClick(int s) {
                if (s == -1) {
                    sift = "";
                } else {
                    sift = "&attendCount=" + s;
                }
                param = paramNum + city + type + near + sift;
                mSmoothListView.smoothScrollToPosition(0);
                ptrFindEx.autoRefresh(true, 250);
            }
        });

        //设置频道头点击事件
        mHeaderChannelView.setOnItemChannelClickListtener(new HeaderChannelView.OnItemChannelClickListtener() {
            @Override
            public void OnChannelClick(String title) {
                switch (title) {
                    case "会议":
                        title = "ex-hy";
                        break;
                    case "博览会":
                        title = "ex-bl";
                        break;
                    case "赛事":
                        title = "ex-ss";
                        break;
                    default:
                        title = "";
                        break;
                }
                type = "&busiType=" + title;
                param = paramNum + city + type + near + sift;
                mSmoothListView.smoothScrollToPosition(0);
                ptrFindEx.autoRefresh(true, 250);
            }
        });


    }

    /**
     * 获取展会类型
     */
    private void getExhibitionType() {
        String typeParam = "name=ex-";
        model.getType(handler, UrlConstant.HTTP_URL_FIND_EXHIBITION_TYPE, typeParam);
    }

    /**
     * 搜索展会
     */
    private void getExhibition() {
        param = paramNum;
        startLoading("正在加载中...");//开始加载动画
        model.search(handler, UrlConstant.HTTP_URL_SEARCH_EXHIBITION, param);
    }

    /**
     * 设置数据
     */
    private void setDatas() {
        //添加数据
        filterData = new FilterData();
        //城市
        filterData.setCity(ModelUtil.getCityData());
        //最近
        filterData.setNear(ModelUtil.getNearData());
        //类型
        filterData.setType(exType);
        //筛选
        filterData.setSift(ModelUtil.getSiftData());

        mFilterView.setFilterData(this, filterData);
    }

    /**
     * 展示展会
     */
    private void showExhibitions(List<CommonBean.Data> datas, boolean idLoadMore) {
        mSmoothListView.setRefreshEnable(false);
        mSmoothListView.setLoadMoreEnable(idLoadMore);
        mSmoothListView.setSmoothListViewListener(this);
        mHeight = DensityUtil.getWindowHeight(mActivity) - DensityUtil.dip2px(mActivity, 45) - 90;
        adapter = new SearchExhibitAdapter(this, datas, mHeight);
        mSmoothListView.setAdapter(adapter);
        isShow = true;
        filterViewPosition = mSmoothListView.getHeaderViewsCount() - 1;
    }

    //下拉刷新
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    //下拉刷新
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        model.search(handler, UrlConstant.HTTP_URL_SEARCH_EXHIBITION, param);
    }

    /**
     * 上啦加载更多
     */
    @Override
    public void onLoadMore() {
        isRefreshing = true;
        itemPerPage = itemPerPage + 10;
        paramNum = "?page=" + page + "&itemsPerPage=" + itemPerPage;
        param = paramNum + city + type + near + sift;
        model.search(handler, UrlConstant.HTTP_URL_SEARCH_EXHIBITION, param);
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_findex_back://返回
                finish();
                break;
            case R.id.iv_findex_location://地图
                startActivity(new Intent(this, MapActivity.class));
                break;
        }
    }


    //item点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (exDatas.size() > 0) {
            int count = mSmoothListView.getHeaderViewsCount();
            if (position > count - 1) {
                int index = position - 4;
                if (exDatas.get(index).getLayout().equals(branch)) {//分论坛
                    startActivity(new Intent(mActivity, BranchActivity.class).putExtra("entityId", exDatas.get(index).getEntityId()));
                } else {//主论坛
                    startActivity(new Intent(mActivity, ConfenceActivity.class).putExtra("entityId", exDatas.get(index).getEntityId()));
                }
            }
        }
    }

    /**
     * 显示搜索的数据
     *
     * @param bean 搜索的结果
     */
    private void showSearchData(CommonBean bean) {
        boolean isLoadMore = false;
        exDatas = bean.getData();
        if (exDatas.size() > 0) {
            if (exDatas.size() < 6) {
                for (int i = exDatas.size(); i < 6; i++) {
                    exDatas.add(bean.new Data());
                }
            } else {
                isLoadMore = true;
            }
            //获取展会类型
        }
        getExhibitionType();
        showExhibitions(exDatas, isLoadMore);
    }

    /**
     * 显示展会
     *
     * @param data 请求的数据
     */

    private void showExhibi(CommonBean data) {
        stopLoading();
        boolean isLoadMore = true;
        int COUNT = 6;
        if (exDatas == null) {//首次进入
            stopLoading();
            exDatas = data.getData();
            if (exDatas.size() < COUNT && exDatas.size() > 0) {//集合个数小于6个
                isLoadMore = false;
                for (int i = exDatas.size(); i < COUNT; i++) {
                    exDatas.add(data.new Data());
                }
            }
            showExhibitions(exDatas, isLoadMore);//显示展会
        } else {
            if (isRefreshing) {//上拉加载更多
                isRefreshing = false;
                exDatas.addAll(data.getData());
                mSmoothListView.stopLoadMore();
                adapter.notifyDataSetChanged();
            } else {//下拉刷新
                exDatas.clear();
                exDatas.addAll(data.getData());
                ptrFindEx.refreshComplete();
                if (data.getData().size() > 0) {
                    if (data.getData().size() < COUNT) {//个数小于6个
                        isLoadMore = false;
                        for (int i = data.getData().size(); i < COUNT; i++) {
                            exDatas.add(data.new Data());
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

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
    }
}
