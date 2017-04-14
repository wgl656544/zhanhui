package com.ex.administrator.zhanhui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.SmoothListView.SmoothListView;
import com.ex.administrator.zhanhui.adapter.SearchBlogAdapter;
import com.ex.administrator.zhanhui.constant.HandlerConstant;
import com.ex.administrator.zhanhui.constant.UrlConstant;
import com.ex.administrator.zhanhui.entity.CommonBean;
import com.ex.administrator.zhanhui.entity.InfoCategoryBean;
import com.ex.administrator.zhanhui.entity.InfoPlaceBean;
import com.ex.administrator.zhanhui.model.GetDataModel;
import com.ex.administrator.zhanhui.model.filter.FilterEntity;
import com.ex.administrator.zhanhui.util.ToastUtil;
import com.ex.administrator.zhanhui.view.ModelUtil;
import com.ex.administrator.zhanhui.view.homeInfoHeader.HeaderHomeInfoAdvertView;
import com.ex.administrator.zhanhui.view.homeInfoHeader.HeaderHomeInfoChannelView;
import com.ex.administrator.zhanhui.view.homeInfoHeader.HeaderHomeInfoFilterView;
import com.ex.administrator.zhanhui.view.homeInfoHeader.HomeInfoFilterView;
import com.ex.administrator.zhanhui.view.homeInfoHeader.InfoFilterData;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class HomeInfoActivity extends BaseActivity implements
        View.OnClickListener, PtrHandler, AdapterView.OnItemClickListener, SmoothListView.ISmoothListViewListener {
    @ViewInject(R.id.sl_home_fagment_infomation)
    private SmoothListView mSmoothListView;
    @ViewInject(R.id.ptr_info)
    private PtrFrameLayout ptrInfo;
    @ViewInject(R.id.fv_home_fragment_info)
    private HomeInfoFilterView mFilterView;
    @ViewInject(R.id.iv_home_fragment_info_back)
    private ImageView ivBack;

    private HeaderHomeInfoFilterView mHeaderFilterView;
    private HeaderHomeInfoChannelView mHeaderChannelView;
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

    private GetDataModel model = new GetDataModel();//获取展会分类对象
    private List<FilterEntity> categorys;//筛选视图分类数据
    private List<FilterEntity> places;//筛选视图地方数据
    private String name = "termName=news";//分类数据参数
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
    private String categoryCode;
    private String city;
    private int startTime;
    private String param;
    private boolean isRefreshing = false;

    private MyHandler handler = new MyHandler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragment_info);
        x.view().inject(this);
        param = "?page=" + page + "&itemPerPage=" + itemPerPage;

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
        //添加频道头布局
        mHeaderChannelView = new HeaderHomeInfoChannelView(this);
        mHeaderChannelView.getView(mSmoothListView);
        //添加广告头布局
        mHeaderAdvertlView = new HeaderHomeInfoAdvertView(this);
        mHeaderAdvertlView.getView(mSmoothListView);
        //添加筛选头布局
        mHeaderFilterView = new HeaderHomeInfoFilterView(this);
        mHeaderFilterView.getView(mSmoothListView);

        final StoreHouseHeader header = new StoreHouseHeader(this);
        header.initWithString("ruichuang", 35);
        header.setTextColor(R.color.color_bule_2);
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
        mSmoothListView.setSmoothListViewListener(this);
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
//                    //处理逻辑
//                    isRefreshing = true;
//                    page++;
//                    model.search(handler, UrlConstant.HTTP_URL_INFO_SEARCH_BLOG, param);//查询资讯
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
        //listview滑动监听器
//        mSmoothListView.setOnScrollListener(this);

        //分类监听器
        mFilterView.setOnItemCategoryClickListener(new HomeInfoFilterView.OnItemCategoryClickListener() {
            @Override
            public void onItemCategoryClick(String type) {
                categoryCode = type;
            }
        });
        //设置地方监听器
        mFilterView.setOnItemPlaceClickListener(new HomeInfoFilterView.OnItemPlaceClickListener() {
            @Override
            public void onItemPlaceClick(String city) {
                HomeInfoActivity.this.city = city;
            }
        });
        //设置时间监听器
        mFilterView.setOnItemDateClickListener(new HomeInfoFilterView.OnItemDateClickListener() {
            @Override
            public void onItemDateClick(int date) {
                HomeInfoActivity.this.startTime = date;
            }
        });
        //设置广告监听器
        mHeaderAdvertlView.setOnItemAdvertClickListtener(new HeaderHomeInfoAdvertView.OnItemAdvertClickListtener() {
            @Override
            public void OnChannelClick(String title) {
                ToastUtil.show(HomeInfoActivity.this, title);
            }
        });
        //设置频道监听器
        mHeaderChannelView.setOnItemChannelClickListtener(new HeaderHomeInfoChannelView.OnItemChannelClickListtener() {
            @Override
            public void OnChannelClick(String title) {
                ToastUtil.show(HomeInfoActivity.this, title);
            }
        });
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
        infoFilterData.setPlace(places);
        //最近
        infoFilterData.setDate(ModelUtil.getNearData());
        mFilterView.setFilterData(this, infoFilterData);
    }

    /**
     * 展示资讯
     */
    private void showExhibitions(List<CommonBean.Data> datas) {
        if (datas != null) {
//            mSmoothListView.setRefreshEnable(true);
//            mSmoothListView.setLoadMoreEnable(false);
//            mSmoothListView.setSmoothListViewListener(this);
            adapter = new SearchBlogAdapter(this, datas);
            mSmoothListView.setAdapter(adapter);
            isShow = true;
            filterViewPosition = mSmoothListView.getHeaderViewsCount() - 1;
        }
    }


    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home_fragment_info_back:
                //返回
                finish();
                break;
        }
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    //下拉刷新
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        model.search(handler, UrlConstant.HTTP_URL_INFO_SEARCH_BLOG, param);//查询资讯
    }

    //listview Itme的点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this, blogDatas.get(position - 4).getName());
    }

    //下拉刷新
    @Override
    public void onRefresh() {

    }

    //上拉加载
    @Override
    public void onLoadMore() {
        model.search(handler, UrlConstant.HTTP_URL_INFO_SEARCH_BLOG, param);//查询资讯
        isRefreshing = true;
    }

    //Handler
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.INFO_CATEGORY_SUCCESS://资讯分类
                    if (msg.what == HandlerConstant.INFO_CATEGORY_SUCCESS) {//资讯分类
                        infoCategoryBean = (InfoCategoryBean) msg.obj;
                        datas = infoCategoryBean.getData();
                        categorys = new ArrayList<>();
                        for (int i = 0; i < datas.size(); i++) {//获取分类名称
//                            categorys.add(new FilterEntity(datas.get(i).getBlogTerm().getName()));
                            categorys.add(new FilterEntity(datas.get(i).getName()));
                        }
                        mHeaderChannelView.showChannel(datas);
                    }
                    setDatas();
                    break;
                case HandlerConstant.INFO_HAINAN_ALL_CITY_SUCCESS://海南所有城市
                    if (msg.what == HandlerConstant.INFO_HAINAN_ALL_CITY_SUCCESS) {//海南所有城市
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
                    setDatas();
                    break;
                case HandlerConstant.SEARCH_SUCCESS://查询资讯
                    if (msg.what == HandlerConstant.SEARCH_SUCCESS) {//查询资讯
                        stopLoading();//停止加载动画
                        infoBlogBean = (CommonBean) msg.obj;
                        if (blogDatas != null) {
                            //下拉刷新or条件筛选
                            if (isRefreshing) {
                                blogDatas.addAll(infoBlogBean.getData());
                                isRefreshing = false;
                                ToastUtil.show(HomeInfoActivity.this, "加载了更多");
                                mSmoothListView.stopLoadMore();
                                adapter.notifyDataSetChanged();
                            } else {
                                //上拉加载更多
                                blogDatas.clear();
                                blogDatas.addAll(infoBlogBean.getData());
                                adapter.notifyDataSetChanged();
                                ptrInfo.refreshComplete();
                                ToastUtil.show(HomeInfoActivity.this, "点击了筛选");
                            }
                        } else {
                            //首次进入
                            if (infoBlogBean.getData() != null) {
                                blogDatas = infoBlogBean.getData();
                                if (blogDatas.size() < 5) {
                                    for (int i = 0; i < 5; i++) {
                                        CommonBean.Data data = infoBlogBean.new Data();
                                        blogDatas.add(data);
                                    }
                                }
                            }
                            showExhibitions(blogDatas);
                        }
                    }
                    break;
            }
        }
    }
}
