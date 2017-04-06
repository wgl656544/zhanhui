package com.ex.administrator.zhanhui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.SmoothListView.SmoothListView;
import com.ex.administrator.zhanhui.adapter.SearchExhibitAdapter;
import com.ex.administrator.zhanhui.constant.HandlerConstant;
import com.ex.administrator.zhanhui.entity.ExhibitionTypeBean;
import com.ex.administrator.zhanhui.entity.SearchExhibitBean;
import com.ex.administrator.zhanhui.model.FindExhibitionModel;
import com.ex.administrator.zhanhui.model.filter.FilterEntity;
import com.ex.administrator.zhanhui.util.ToastUtil;
import com.ex.administrator.zhanhui.view.ModelUtil;
import com.ex.administrator.zhanhui.view.findEXheader.FilterData;
import com.ex.administrator.zhanhui.view.findEXheader.FilterView;
import com.ex.administrator.zhanhui.view.findEXheader.HeaderAdvertView;
import com.ex.administrator.zhanhui.view.findEXheader.HeaderChannelView;
import com.ex.administrator.zhanhui.view.findEXheader.HeaderFilterView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class FindExhibitionActivity extends BaseActivity implements
        SmoothListView.ISmoothListViewListener, View.OnClickListener {
    @ViewInject(R.id.lv_find_exhibition)
    private SmoothListView mSmoothListView;
    @ViewInject(R.id.fv_find_exhibition)
    private FilterView mFilterView;
    @ViewInject(R.id.iv_find_exhibition_back)
    private ImageView ivBack;

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
    private FindExhibitionModel findExhibitionModel;//展会搜索业页面，业务对象
    private ExhibitionTypeBean exhibitionTypeBean;//展会类型对象实体类
    private List<FilterEntity> exType;//展会类型
    private SearchExhibitBean searchExhibitBean;//搜索展会成功实体类
    private List<SearchExhibitBean.Data> exDatas;//具体展会实体类
    private SearchExhibitAdapter adapter;
    private String city = "";
    private String type = "博览会";
    private int near = 1;
    private int sift = 500;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HandlerConstant.EXHIBITION_TYPE_SUCCESS) {//类型
                exhibitionTypeBean = (ExhibitionTypeBean) msg.obj;
                exType = new ArrayList<>();
                for (int i = 0; i < exhibitionTypeBean.getData().size(); i++) {
                    exType.add(new FilterEntity(exhibitionTypeBean.getData().get(i).getName()));
                }
            }
            if (msg.what == HandlerConstant.SEARCH_EXHIBITION_SUCCESS) {//搜索展会
                stopLoading();//停止加载动画
                searchExhibitBean = (SearchExhibitBean) msg.obj;
                exDatas = searchExhibitBean.getData();
            }


            if (exDatas != null) {
                //如果返回的数据小于5个,则添加几个空数据
                if (exDatas.size() < 5) {
                    for (int i = 0; i < 5; i++) {
                        SearchExhibitBean.Data data = searchExhibitBean.new Data();
                        exDatas.add(data);
                    }
                }
                //显示展会
                showExhibitions(exDatas);
                //设置4个筛选的数据
                setDatas();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_exhibition);
        x.view().inject(this);
        try {
            //获取展会类型
            getExhibitionType();
            //获取展会
            getExhibition();
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
        //添加广告头布局
        mHeaderAdvertlView = new HeaderAdvertView(this);
        mHeaderAdvertlView.getView(mSmoothListView);
        //添加频道头布局
        mHeaderChannelView = new HeaderChannelView(this);
        mHeaderChannelView.getView(mSmoothListView);
        //添加筛选头布局
        mHeaderFilterView = new HeaderFilterView(this);
        mHeaderFilterView.getView(mSmoothListView);

    }

    /**
     * 设置监听器
     */
    private void setListener() {
        //返回
        ivBack.setOnClickListener(this);
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
                if ((filterViewTopMargin <= 0 || firstVisibleItem > filterViewPosition ) && isShow) {
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
            public void onItemCityClick(String city) {
                FindExhibitionActivity.this.city = city;
                ToastUtil.show(FindExhibitionActivity.this, city + near);
            }
        });
        //设置类型监听方法
        mFilterView.setOnItemTypeClickListener(new FilterView.OnItemTypeClickListener() {
            @Override
            public void onItemTypeClick(String type) {
                FindExhibitionActivity.this.type = type;
                ToastUtil.show(FindExhibitionActivity.this, type);
            }
        });
        //设置最近监听方法
        mFilterView.setOnItemNearClickListener(new FilterView.OnItemNearClickListener() {
            @Override
            public void onItemNearClick(int near) {
                FindExhibitionActivity.this.near = near;
                ToastUtil.show(FindExhibitionActivity.this, near + "");
            }
        });
        //设置筛选监听方法
        mFilterView.setOnItemSiftClickListener(new FilterView.OnItemSiftClickListener() {
            @Override
            public void onItemSiftClick(int sift) {
                FindExhibitionActivity.this.sift = sift;
                ToastUtil.show(FindExhibitionActivity.this, sift + "");
            }
        });

        //设置频道头点击事件
        mHeaderChannelView.setOnItemChannelClickListtener(new HeaderChannelView.OnItemChannelClickListtener() {
            @Override
            public void OnChannelClick(String title) {
                if(title.equals("会议")){
                    startActivity(new Intent(FindExhibitionActivity.this,DetailExActivity.class));
                }
                if(title.equals("博览会")){
                    startActivity(new Intent(FindExhibitionActivity.this,DetailExpoActivity.class));
                }
            }
        });


    }

    /**
     * 获取展会类型
     */
    private void getExhibitionType() {
        String name = "name=ex-";
        findExhibitionModel = new FindExhibitionModel();
        findExhibitionModel.getExhibitionType(handler, name);
    }

    /**
     * 搜索展会
     */
    private void getExhibition() {
        String name = "&city=" + city + "&Type=" + type + "&starTtime=" + near + "&attendcount=" + sift;
        startLoading("正在加载中...");//开始加载动画
        findExhibitionModel.searchExhibition(handler, name);
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
    private void showExhibitions(List<SearchExhibitBean.Data> datas) {
        mSmoothListView.setRefreshEnable(true);
        mSmoothListView.setLoadMoreEnable(false);
        mSmoothListView.setSmoothListViewListener(this);
        adapter = new SearchExhibitAdapter(this, datas);
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
            case R.id.iv_find_exhibition_back:
                finish();
                break;
        }
    }
}
