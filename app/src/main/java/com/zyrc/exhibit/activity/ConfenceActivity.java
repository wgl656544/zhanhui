package com.zyrc.exhibit.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mylibrary.base.BaseActivity;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.ChannelAdapter;
import com.zyrc.exhibit.adapter.DetailExRollPagerAdapter;
import com.zyrc.exhibit.adapter.detailex.DetailBlogAdapter;
import com.zyrc.exhibit.adapter.detailex.DetailHonorAdapter;
import com.zyrc.exhibit.adapter.detailex.DetailSubAdapter;
import com.zyrc.exhibit.adapter.detailex.DetailTicketAdapter;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.ChannelBean;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.entity.CommonListBean;
import com.zyrc.exhibit.entity.DetailExBean;
import com.zyrc.exhibit.model.GetDtaListModel;
import com.zyrc.exhibit.model.Model;
import com.zyrc.exhibit.view.FullyGridLayoutManager;
import com.zyrc.exhibit.view.FullyLinearLayoutManager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会议详细页面
 * Created by Administrator on 2017/2/20 0020.
 */

public class ConfenceActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_confence)
    private LinearLayout llConfence;
    @ViewInject(R.id.iv_detail_ex)
    private ImageView ivDetailEx;
    @ViewInject(R.id.tv_detail_ex_name)
    private TextView tvDetailExName;
    @ViewInject(R.id.tv_detail_ex_date)
    private TextView tvDetailExDate;
    @ViewInject(R.id.tv_detail_ex_city)
    private TextView tvDetailExCity;
    @ViewInject(R.id.tv_detail_ex_address)
    private TextView tvDetailExAddress;
    @ViewInject(R.id.tv_detail_ex_busi)
    private TextView tvDetailExBusi;
    @ViewInject(R.id.rpv_detail_ex)
    private RollPagerView rpvDetailEx;//轮播广告
    @ViewInject(R.id.iv_detail_ex_back)
    private ImageView ivBack;//返回
    @ViewInject(R.id.rl_confence_channel)
    private RecyclerView rlChannel;//频道按钮
    @ViewInject(R.id.rl_confence_guest)
    private RecyclerView rlHonor;//嘉宾
    @ViewInject(R.id.rl_confence_branch)
    private RecyclerView rlBranch;//分论坛
    @ViewInject(R.id.rl_confence_ticket)
    private RecyclerView rlTicket;//门票
    @ViewInject(R.id.rl_confence_info)
    private RecyclerView rlInfo;//资讯
    @ViewInject(R.id.ll_detail_more_honor)
    private LinearLayout llMoreHonor;//更多嘉宾
    @ViewInject(R.id.ll_detail_more_sub)
    private LinearLayout llMoreSub;//更多分论坛
    @ViewInject(R.id.ll_detail_more_ticket)
    private LinearLayout llMoreTicket;//更多门票
    @ViewInject(R.id.ll_detail_more_blog)
    private LinearLayout llMoreBlog;//更多资讯

    @ViewInject(R.id.rl_detail_more_honor)
    private RelativeLayout rlMoreHonor;
    @ViewInject(R.id.view_detail_more_honor)
    private View viewMoreHonor;
    @ViewInject(R.id.rl_detail_more_sub)
    private RelativeLayout rlMoreSub;
    @ViewInject(R.id.view_detail_more_sub)
    private View viewMoreSub;
    @ViewInject(R.id.rl_detail_more_ticket)
    private RelativeLayout rlMoreTicket;
    @ViewInject(R.id.view_detail_more_ticket)
    private View viewMoreTicket;
    @ViewInject(R.id.rl_detail_more_blog)
    private RelativeLayout rlMoreBlog;
    @ViewInject(R.id.view_detail_more_blog)
    private View viewMoreBlog;
    @ViewInject(R.id.btn_confence_apply)
    private Button btnApply;//报名
    @ViewInject(R.id.btn_confence_position)
    private Button btnPosition;//展位预定
    @ViewInject(R.id.ll_confence_map)
    private LinearLayout llMap;//跳转到地图


    private String userId = "userId";//参数id
    private String eventType = "eventType";//参数类型
    private String entityName = "entityName";//产品类型
    private String entityId1 = "entityId";//产品id
    private int entityId;
    private Activity mActivity;
    private GetDtaListModel model = new GetDtaListModel();
    private Model mModel;
    private Map<String,String> param;
    private DetailExBean detailExBean;//展会详细信息实体类
    private List<ChannelBean.Data> channelBean;//频道按钮
    private CommonListBean detailExAdvertBean;//展会详细页面广告
    private CommonListBean detailExInfoBean;
    private List<CommonBean.Data> honorList;//嘉宾
    private List<CommonBean.Data> subList;//论坛
    private List<CommonBean.Data> ticketList;//票务
    private List<CommonBean.Data> blogList;//资讯
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HandlerConstant.DETAIL_EX_SUCCESS) {//展会信息
                detailExBean = (DetailExBean) msg.obj;
                //显示详细信息
                if (detailExBean != null) {
                    showExDetail(detailExBean);
                }
            }
            if (msg.what == HandlerConstant.DETAIL_EX_CHANNEL) {//展会频道
                ChannelBean bean = (ChannelBean) msg.obj;
                channelBean = bean.getData();
                showChannel(channelBean);
            }

            if (msg.what == HandlerConstant.DETAIL_EX_ADVERT_SUCCESS) {//展会广告
                detailExAdvertBean = (CommonListBean) msg.obj;
                //显示广告
                if (detailExAdvertBean != null) {
                    showAdvert(detailExAdvertBean);
                }
            }

            if (msg.what == HandlerConstant.DETAIL_EX_INFO_SUCCESS) {//展会资讯
                detailExInfoBean = (CommonListBean) msg.obj;
                for (int i = 0; i < detailExInfoBean.getData().size(); i++) {
                    if (detailExInfoBean.getData().get(i).getName().equals("ex-home-honor")) {
                        honorList = detailExInfoBean.getData().get(i).getDataList();
                    } else if (detailExInfoBean.getData().get(i).getName().equals("ex-home-sub")) {
                        subList = detailExInfoBean.getData().get(i).getDataList();
                    } else if (detailExInfoBean.getData().get(i).getName().equals("ex-home-ticket")) {
                        ticketList = detailExInfoBean.getData().get(i).getDataList();
                    } else {
                        blogList = detailExInfoBean.getData().get(i).getDataList();
                    }
                }
                showContent();
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confence);
        mActivity = this;
        x.view().inject(this);
        entityId = getIntent().getIntExtra("entityId", 1);
        setListeners();
        initData();
        //保存足迹
        save(UrlConstant.HTTP_URL_ADD_COLLECT, 0, "view");
    }

    //请求数据
    private void initData() {
        startLoading("加载中...");
        model.getDetailEx(handler, entityId);
        model.getDataList(handler, UrlConstant.HTTP_URL_DETAIL_EX_ADVERT, "?exhibId=" + entityId + "&name=ex-home-top", HandlerConstant.DETAIL_EX_ADVERT_SUCCESS);//广告
        model.getDataList(handler, UrlConstant.HTTP_URL_DETAIL_EX_INFO, "exhibId=" + entityId, HandlerConstant.DETAIL_EX_INFO_SUCCESS);//模块
        model.getChannel(handler, "?exhibId=" + entityId);//频道
    }

    //设置监听器
    private void setListeners() {
        ivBack.setOnClickListener(this);
        btnApply.setOnClickListener(this);
        llMoreHonor.setOnClickListener(this);
        llMoreSub.setOnClickListener(this);
        llMoreTicket.setOnClickListener(this);
        llMoreBlog.setOnClickListener(this);
        btnPosition.setOnClickListener(this);
        llMap.setOnClickListener(this);
    }


    //显示展会的详细信息
    private void showExDetail(DetailExBean bean) {
        //显示图片
        Glide.with(this).load(bean.getData().getImageUrl()).error(R.drawable.default_pic).into(ivDetailEx);
        //显示展会名称
        tvDetailExName.setText(bean.getData().getName());
        //显示展会时间
        tvDetailExDate.setText(bean.getData().getStartDate() + "至" + bean.getData().getEndDate());
        //显示展会城市
        tvDetailExCity.setText(bean.getData().getAddress().getCity());
        //显示展会地址
        tvDetailExAddress.setText(bean.getData().getAddress().getStreet());
        //显示合作信息
        tvDetailExBusi.setText("商务:" + bean.getData().getTel() + "");
    }

    //显示轮播广告
    private void showAdvert(CommonListBean bean) {
        List<CommonBean.Data> data = new ArrayList<>();
        for (int i = 0; i < bean.getData().size(); i++) {
            if (bean.getData().get(i).getName().equals("ex-home-top")) {
                data = bean.getData().get(i).getDataList();
            }
        }
        rpvDetailEx.setAnimationDurtion(500);
        //设置指示器的颜色
        rpvDetailEx.setHintView(new ColorPointHintView(this, Color.WHITE, Color.GRAY));
        DetailExRollPagerAdapter adapter = new DetailExRollPagerAdapter(rpvDetailEx, data);
        rpvDetailEx.setAdapter(adapter);
        //轮播图点击事件
        final List<CommonBean.Data> finalData = data;
        rpvDetailEx.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(ConfenceActivity.this, WebViewActivity.class).putExtra(HandlerConstant.DETAIL_BLOG, finalData.get(position)));
            }
        });
    }

    //显示频道
    private void showChannel(List<ChannelBean.Data> data) {
        List<ChannelBean.Data> datas = new ArrayList<>();
        for (ChannelBean.Data item : data) {//移除index元素
            if (!item.getPageTag().equals("index")) {
                datas.add(item);
            }
        }
        if (data.size() > 0) {
            ChannelAdapter adapter = new ChannelAdapter(mActivity, datas);
            rlChannel.setLayoutManager(new FullyGridLayoutManager(mActivity, 4));
            rlChannel.setAdapter(adapter);
            adapter.setOnItemClickListener(new ChannelAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String type) {
                    showMoreInfo(type);
                }
            });
        }
    }

    //显示 嘉宾,分论坛,票务,资讯
    private void showContent() {
        stopLoading();
        llConfence.setVisibility(View.VISIBLE);
        //嘉宾
        if (honorList.size() > 0) {
            DetailHonorAdapter honorAdapter = new DetailHonorAdapter(mActivity, honorList);
            rlHonor.setLayoutManager(new FullyGridLayoutManager(mActivity, 3));
            rlHonor.setAdapter(honorAdapter);
            honorAdapter.setOnItemClickListener(new DetailHonorAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    startActivity(new Intent(mActivity, IntroduceActivity.class).putExtra("url", honorList.get(position).getLinkUrl()));
                }
            });
        } else {
            rlMoreHonor.setVisibility(View.GONE);
            viewMoreHonor.setVisibility(View.GONE);
        }

        //分论坛
        if (subList != null && subList.size() > 0) {
            DetailSubAdapter subAdapter = new DetailSubAdapter(mActivity, subList);
            rlBranch.setLayoutManager(new FullyLinearLayoutManager(mActivity));
            rlBranch.setAdapter(subAdapter);
            rlBranch.setNestedScrollingEnabled(false);
            subAdapter.setOnItemClickListener(new DetailSubAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    startActivity(new Intent(mActivity, BranchActivity.class).putExtra("entityId", subList.get(position).getEntityId()));
                }
            });
        } else {
            rlMoreSub.setVisibility(View.GONE);
            viewMoreSub.setVisibility(View.GONE);
        }
        //票务
        if (ticketList.size() > 0) {
            DetailTicketAdapter ticketAdapter = new DetailTicketAdapter(mActivity, ticketList);
            rlTicket.setLayoutManager(new FullyGridLayoutManager(mActivity, 2));
            rlTicket.setAdapter(ticketAdapter);
            ticketAdapter.setOnItemClickListener(new DetailTicketAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    startActivity(new Intent(mActivity, DetailBuyActivity.class).putExtra(HandlerConstant.DETAIL_BUY, ticketList.get(position)));
                }
            });
        } else {
            rlMoreTicket.setVisibility(View.GONE);
            viewMoreTicket.setVisibility(View.GONE);
        }
        //资讯
        if (blogList.size() > 0) {
            DetailBlogAdapter infoAdapter = new DetailBlogAdapter(mActivity, blogList);
            rlInfo.setLayoutManager(new FullyLinearLayoutManager(mActivity));
            rlInfo.setAdapter(infoAdapter);
            rlInfo.setNestedScrollingEnabled(false);
            infoAdapter.setOnItemClickListener(new DetailBlogAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    startActivity(new Intent(mActivity, WebViewActivity.class).putExtra(HandlerConstant.DETAIL_BLOG, blogList.get(position)));
                }
            });
        } else {
            rlMoreBlog.setVisibility(View.GONE);
            viewMoreBlog.setVisibility(View.GONE);
        }
    }

    /**
     * 足迹记录，收藏，取消收藏
     */
    private void save(String url, int requestCode, String type) {
        if (mModel == null) {
            mModel = new Model();
        }
        if (param == null) {
            param = new HashMap<>();
        }
        if (MyApplication.isLogin) {
            param.put(userId, MyApplication.userId);
        }
        param.put(eventType, type);
        param.put(entityName, "exhibit");
        param.put(entityId1, String.valueOf(entityId));
        mModel.postData(handler, url, requestCode, param);
    }


    /**
     * 频道按钮的点击具体事件
     *
     * @param type 类型
     */
    private void showMoreInfo(String type) {
        switch (type) {
            case "index"://首页
                break;
            case "active"://议程
                startActivity(new Intent(mActivity, DetailExPlanActivity.class).putExtra("entityId", entityId));
                break;
            case "content"://展会信息
                startActivity(new Intent(mActivity, IntroduceActivity.class).putExtra("url", UrlConstant.HTTP_URL_EX_IP + "?id=" + entityId));
                break;
            case "guest"://嘉宾
                startActivity(new Intent(this, DetailExHonorActivity.class).putExtra("entityId", entityId));
                break;
            case "branch"://分论坛
                startActivity(new Intent(mActivity, BranchActivity.class).putExtra("entityId", subList.get(0).getEntityId()));
                break;
            case "ticket"://票务
                startActivity(new Intent(this, DetailExTicketActivity.class).putExtra("entityId", entityId));
                break;
            case "position"://场馆
                startActivity(new Intent(this, DetailExPositionActivity.class).putExtra("entityId", entityId));
                break;
            case "product"://团购
                startActivity(new Intent(this, DetailExTeamActivity.class).putExtra("entityId", entityId));
                break;
            case "live"://资讯
                startActivity(new Intent(this, DetailExNewsActivity.class).putExtra("entityId", entityId));
                break;
            case "company"://参展商
                break;
            case "invite"://招商
                break;
            case "vote"://投票
                break;
        }
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_detail_ex_back://返回
                finish();
                break;
            case R.id.btn_confence_apply://报名
                startActivity(new Intent(this, ApplyActivity.class).putExtra("entityId", entityId));
                break;
            case R.id.ll_detail_more_honor://更多嘉宾
                startActivity(new Intent(this, DetailExHonorActivity.class).putExtra("entityId", entityId));
                break;
            case R.id.ll_detail_more_sub://更多分论坛
                startActivity(new Intent(mActivity, BranchActivity.class).putExtra("entityId", entityId));
                break;
            case R.id.ll_detail_more_ticket://更多门票
                startActivity(new Intent(this, DetailExTicketActivity.class).putExtra("entityId", entityId));
                break;
            case R.id.ll_detail_more_blog://更多资讯
                startActivity(new Intent(this, DetailExNewsActivity.class).putExtra("entityId", entityId));
                break;
            case R.id.btn_confence_position://展位预定
                startActivity(new Intent(this, PositionReserveActivity.class).putExtra("entityId", entityId));
                break;
            case R.id.ll_confence_map://跳转地图
                startActivity(new Intent(this, MapActivity.class).putExtra(MapActivity.DETAILEX, detailExBean));
                break;
        }
    }

}
