package com.zyrc.exhibit.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.mylibrary.base.BaseFragment;
import com.google.gson.Gson;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.activity.BranchActivity;
import com.zyrc.exhibit.activity.ChoiceCityActivity;
import com.zyrc.exhibit.activity.ConfenceActivity;
import com.zyrc.exhibit.activity.DetailBuyActivity;
import com.zyrc.exhibit.activity.HomeBusiActivity;
import com.zyrc.exhibit.activity.HomeFindEXActivity;
import com.zyrc.exhibit.activity.HomeInfoActivity;
import com.zyrc.exhibit.activity.HomeTeamActivity;
import com.zyrc.exhibit.activity.HomeTicketActivity;
import com.zyrc.exhibit.activity.LoginActivity;
import com.zyrc.exhibit.activity.MainActivity;
import com.zyrc.exhibit.activity.MyExhibiActivity;
import com.zyrc.exhibit.activity.MyQrcodeActivity;
import com.zyrc.exhibit.activity.SearchActivity;
import com.zyrc.exhibit.activity.WebViewActivity;
import com.zyrc.exhibit.adapter.HomeAddressAdapter;
import com.zyrc.exhibit.adapter.HomeRollPagerAdapter;
import com.zyrc.exhibit.adapter.HomeTicketAdapter;
import com.zyrc.exhibit.adapter.detailex.DetailBlogAdapter;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.entity.HomePageBean;
import com.zyrc.exhibit.entity.HotCityBean;
import com.zyrc.exhibit.model.Model;
import com.zyrc.exhibit.util.ToastUtil;
import com.zyrc.exhibit.view.FullyGridLayoutManager;
import com.zyrc.exhibit.view.FullyLinearLayoutManager;
import com.zyrc.exhibit.zxing.activity.CaptureActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
import java.util.Locale;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener, PtrHandler {
    @ViewInject(R.id.ll_home)
    private LinearLayout llHome;
    @ViewInject(R.id.rollpagerview)
    RollPagerView mRollPagerView;//轮播广告
    @ViewInject(R.id.viewFlipper)
    private ViewFlipper mViewFlipper;//上下轮播广告
    @ViewInject(R.id.rl_address)
    private RecyclerView rlAddress;//地点
    @ViewInject(R.id.rl_menpiao)
    private RecyclerView rlMenpiao;//门票
    @ViewInject(R.id.rl_tuangou)
    private RecyclerView rlTuangou;//团购
    @ViewInject(R.id.iv_main_add)
    private ImageView ivMainAdd;//更多选项
    @ViewInject(R.id.view_mid_adver)
    private View viewMidImage;
    @ViewInject(R.id.iv_home_fragment_mid)
    private ImageView midImageview;//首页中部广告
    @ViewInject(R.id.ptr_home_fragment)
    private PtrFrameLayout ptrHomeFragment;//下拉刷新
    @ViewInject(R.id.home_iv_exhibition)
    private ImageView ivExhibition;//搜展会
    @ViewInject(R.id.home_iv_info)
    private ImageView ivInfo;//资讯
    @ViewInject(R.id.home_iv_team)
    private ImageView ivTeam;//团购
    @ViewInject(R.id.home_iv_ticket)
    private ImageView ivTicket;//门票
    @ViewInject(R.id.rl_info)
    private RecyclerView rlInfo;//资讯
    @ViewInject(R.id.home_iv_find)
    private ImageView ivfind;//发现
    @ViewInject(R.id.home_iv_busi)
    private ImageView ivBusi;//招商
    @ViewInject(R.id.tv_home_local)
    private TextView tvLoacl;
    @ViewInject(R.id.ll_home_exhibit_more)
    private LinearLayout llExhibitMore;//更多展会
    @ViewInject(R.id.ll_home_ticket_more)
    private LinearLayout llTicketMore;//更多门票
    @ViewInject(R.id.ll_home_team_more)
    private LinearLayout llTeamMore;//更多团购
    @ViewInject(R.id.ll_home_more_blog)
    private LinearLayout llMoreBlog;
    @ViewInject(R.id.ll_home_search)
    private LinearLayout llSearch;//搜索框
    @ViewInject(R.id.view_ticket)
    private View viewTicket;//门票分割线
    @ViewInject(R.id.rl_title_ticket)
    private RelativeLayout rlTitleTicket;//门票标题
    @ViewInject(R.id.view_team)
    private View viewTeam;//团购分割线
    @ViewInject(R.id.rl_title_team)
    private RelativeLayout rlTitleTeam;//团购标题
    @ViewInject(R.id.sl_home)
    private ScrollView mScrollView;

    private PopupWindow mPopuWindow;//"更多"弹出窗口
    private Model model;
    private HomePageBean homePageBeanAdvers;//首页广告图片实体类
    private HomePageBean homePageBeanBusi;//首页业务实体类
    private HotCityBean hotCityBean;//热门城市实体类对象
    private List<CommonBean.Data> topDataList;//顶部轮播广告
    private List<CommonBean.Data> midDataList;//中间广告
    private List<CommonBean.Data> addressDataList;//地区展会业务
    private List<CommonBean.Data> ticketDataList;//门票业务
    private List<CommonBean.Data> teamDataList;//团购业务
    private List<CommonBean.Data> infoDatas;//资讯业务

    private HomeAddressAdapter homeAddressAdapter;//首页展会业务适配器
    private HomeAddressAdapter homeTeamAdapter;//首页团购业务适配器
    private HomeTicketAdapter homeTicketAdapter;//首页门票业务适配器
    private DetailBlogAdapter infoAdapter;//首页资讯业务适配器
    private static final int ZXING_SCAN_SUCCESS = 1006;//二维码
    private static final int REQUEST_CODE_PICK_CITY = 100;//城市
    private float mLastX;//滑动距离

    private Context mContext = MyApplication.getmMyApplication().getApplicationContext();
    private final String branch = "branch";//分论坛
    private final String confence = "confence";//会议型(主论坛)


    private String adversParam = "";
    private String busiParam = "?city=";
    private String infoParam = "?page=1&itemsPerPage=10";

    private MainActivity mainActivity;
    private String city;
    private static final String ENTITYNAME = "exhibit";
    private static final String LAYOUT = "branch";


    MainActivity activity;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.HOT_CITY_SUCCESS://获取城市
                    String cityJson = (String) msg.obj;
                    hotCityBean = new Gson().fromJson(cityJson, HotCityBean.class);
                    showHotCity(hotCityBean.getData().get(0).getName());
                    MyApplication.setHotCityBean(hotCityBean);
                    break;
                case HandlerConstant.HOME_PAGE_SUCCESS://获取首页广告
                    String pageJson = (String) msg.obj;
                    homePageBeanAdvers = new Gson().fromJson(pageJson, HomePageBean.class);
                    //对广告进行分类
                    sortHomeAdvers(homePageBeanAdvers);
                    break;
                case HandlerConstant.HOME_BUSI_SUCCESS://获取首页业务成功
                    stopLoading();
                    ptrHomeFragment.refreshComplete();
                    llHome.setVisibility(View.VISIBLE);
                    String busiJson = (String) msg.obj;
                    if (homePageBeanBusi != null) {
                        homePageBeanBusi = new Gson().fromJson(busiJson, HomePageBean.class);
                        upDateHomeBusi(homePageBeanBusi);
                    } else {
                        //对业务模块进行分类
                        homePageBeanBusi = new Gson().fromJson(busiJson, HomePageBean.class);
                        sortHomeBusi(homePageBeanBusi);
                    }
                    break;
                case HandlerConstant.GET_INFO:
                    String infoJson = (String) msg.obj;
                    showInfo(new Gson().fromJson(infoJson, CommonBean.class));
                    break;
                case HandlerConstant.UP_DOWM_BANNER://上下动态轮播广告
                    String bannerJson = (String) msg.obj;
                    CommonBean bannerBean = new Gson().fromJson(bannerJson, CommonBean.class);
                    showViewFlipper(bannerBean.getData());
                    break;
                case HandlerConstant.REQUEST_FAIL://失败
                    ToastUtil.show(getActivity(), "服务器请求失败，请稍后再试！");
                    llHome.setVisibility(View.VISIBLE);
                    stopLoading();
                    ptrHomeFragment.refreshComplete();
                    break;
            }
        }
    };


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        x.view().inject(this, view);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {
        startLoading("加载中");
        initView();
        //设置点击事件
        setListeners();
        //增加数据
        initDatas();
    }

    private void initView() {
        if (MyApplication.local != null) {
            city = MyApplication.local;
        } else {
            city = "海口市";
        }
        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getActivity());
        ptrHomeFragment.setHeaderView(header);
        ptrHomeFragment.addPtrUIHandler(header);
    }

    /**
     * 设置监听器
     */
    private void setListeners() {
        //下拉刷新
        ptrHomeFragment.setPtrHandler(this);
        //跳转展会搜索
        ivExhibition.setOnClickListener(this);
        //跳转资讯页面
        ivInfo.setOnClickListener(this);
        //跳转团购界面
        ivTeam.setOnClickListener(this);
        //跳转门票界面
        ivTicket.setOnClickListener(this);
        //跳转到发现海南
        ivfind.setOnClickListener(this);
        //跳转到招商界面
        ivBusi.setOnClickListener(this);
        //点击展会更多
        llExhibitMore.setOnClickListener(this);
        //更多门票
        llTicketMore.setOnClickListener(this);
        //更多团购
        llTeamMore.setOnClickListener(this);
        //更多资讯
        llMoreBlog.setOnClickListener(this);
        //搜索
        llSearch.setOnClickListener(this);
        //显示更多
        ivMainAdd.setOnClickListener(this);
        //上下轮播广告点击事件
        mViewFlipper.setOnClickListener(this);
        //城市选择
        tvLoacl.setOnClickListener(this);
        //中部广告点击事件
        midImageview.setOnClickListener(this);
    }

    /**
     * 获取数据
     */
    private void initDatas() {
        if (model == null) {
            model = new Model();
        }
        //获取城市
        model.getData(handler, UrlConstant.HTTP_URL_HOT_CITY, HandlerConstant.HOT_CITY_SUCCESS, "");
        //获取首页广告
        adversParam = "?name=pg-home";
        model.getData(handler, UrlConstant.HTTP_URL_PAGE_ADVERS, HandlerConstant.HOME_PAGE_SUCCESS, adversParam);
        //获取首页业务
        model.getData(handler, UrlConstant.HTTP_URL_HOME_BUS, HandlerConstant.HOME_BUSI_SUCCESS, "");
        //获取上下动态轮播
        model.getData(handler, UrlConstant.API_GET_BANNER, HandlerConstant.UP_DOWM_BANNER, "");
        //获取首页资讯
        model.getData(handler, UrlConstant.HTTP_URL_INFO_SEARCH_BLOG, HandlerConstant.GET_INFO, infoParam);
    }

    //显示热点城市
    private void showHotCity(String name) {
        tvLoacl.setText(name);
    }

    //对首页广告数据进行分类
    private void sortHomeAdvers(HomePageBean homePageBeanAdvers) {
        if (homePageBeanAdvers.getData().size() > 0) {
            String topName = "pg-home-top";
            String midName = "pg-home-mid";
            for (int i = 0; i < homePageBeanAdvers.getData().size(); i++) {
                if (homePageBeanAdvers.getData().get(i).getName().equals(topName)) {
                    topDataList = homePageBeanAdvers.getData().get(i).getDataList();
                } else if (homePageBeanAdvers.getData().get(i).getName().equals(midName)) {
                    midDataList = homePageBeanAdvers.getData().get(i).getDataList();
                }
            }
            //显示首页广告
            showAdvertisement(topDataList);
        }
    }

    /**
     * 显示首页顶部与中部广告
     */
    private void showAdvertisement(final List<CommonBean.Data> topData) {
        if (mRollPagerView != null && topData.size() != 0) {
            //顶部广告
            //设置轮播动画的速度(切换的速度)
            mRollPagerView.setAnimationDurtion(500);
            //设置指示器的颜色
            mRollPagerView.setHintView(new ColorPointHintView(getActivity(), Color.WHITE, Color.GRAY));
            HomeRollPagerAdapter adapter = new HomeRollPagerAdapter(mRollPagerView, topData);
            mRollPagerView.setAdapter(adapter);
            //轮播图点击事件
            mRollPagerView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (topData.get(position).getEntityName().equals(ENTITYNAME)) {//表示是论坛
                        if (topData.get(position).getLayout() != null) {//表示分论坛
                            startActivity(new Intent(getActivity(), BranchActivity.class).putExtra("entityId", topData.get(position).getEntityId()));
                        } else {//主论坛
                            startActivity(new Intent(getActivity(), ConfenceActivity.class).putExtra("entityId", topData.get(position).getEntityId()));
                        }
                    } else {//直接跳WebView
                        startActivity(new Intent(mainActivity, WebViewActivity.class).putExtra(HandlerConstant.DETAIL_BLOG, topData.get(position)));
                    }
                }
            });
        }
        //中部广告
        if (midDataList.size() > 0) {
            midImageview.setVisibility(View.VISIBLE);
            viewMidImage.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(midDataList.get(0).getImageUrl()).into(midImageview);
        } else {
            viewMidImage.setVisibility(View.GONE);
            midImageview.setVisibility(View.GONE);
        }
    }

    //对首页业务进行分类,显示数据
    private void sortHomeBusi(HomePageBean homePageBeanBusi) {
        String addressName = "pg-home-exhib";//展会业务
        String ticketName = "pg-home-ticket";//门票业务
        String teamName = "pg-home-team";//团购业务
        for (int i = 0; i < homePageBeanBusi.getData().size(); i++) {
            if (homePageBeanBusi.getData().get(i).getName().equals(addressName)) {
                addressDataList = homePageBeanBusi.getData().get(i).getDataList();
            } else if (homePageBeanBusi.getData().get(i).getName().equals(ticketName)) {
                ticketDataList = homePageBeanBusi.getData().get(i).getDataList();
            } else if (homePageBeanBusi.getData().get(i).getName().equals(teamName)) {
                teamDataList = homePageBeanBusi.getData().get(i).getDataList();
            }
        }
        //显示展会业务
        showAddress();
        //显示门票业务
        showTicket();
        //显示团购业务
        showTeam();
    }

    //刷新业务
    private void upDateHomeBusi(HomePageBean homePageBeanBusi) {
        String addressName = "pg-home-exhib";//展会业务
        String ticketName = "pg-home-ticket";//门票业务
        String teamName = "pg-home-team";//团购业务
        for (int i = 0; i < homePageBeanBusi.getData().size(); i++) {
            String name = homePageBeanBusi.getData().get(i).getName();
            if (name.equals(addressName)) {
                addressDataList.clear();
                addressDataList.addAll(homePageBeanBusi.getData().get(i).getDataList());
            } else if (name.equals(ticketName)) {
                ticketDataList.clear();
                ticketDataList.addAll(homePageBeanBusi.getData().get(i).getDataList());
            } else if (name.equals(teamName)) {
                teamDataList.clear();
                teamDataList.addAll(homePageBeanBusi.getData().get(i).getDataList());
            }
        }
        if (homeTicketAdapter == null) {//刷新门票
            showTicket();
        } else {
            homeTicketAdapter.notifyDataSetChanged();
        }

        if (homeTeamAdapter == null) {//刷新团购
            showTeam();
        } else {
            homeTeamAdapter.notifyDataSetChanged();
        }
        homeAddressAdapter.notifyDataSetChanged();
    }

    //显示首页展会业务
    private void showAddress() {
        homeAddressAdapter = new HomeAddressAdapter(mContext, addressDataList);
        rlAddress.setLayoutManager(new FullyGridLayoutManager(getActivity(), 2));
        rlAddress.setAdapter(homeAddressAdapter);
        homeAddressAdapter.setOnItemClickListener(new HomeAddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (addressDataList.get(position).getLayout().equals(branch)) {//分论坛
                    startActivity(new Intent(getActivity(), BranchActivity.class).putExtra("entityId", addressDataList.get(position).getEntityId()));
                } else {//主论坛
                    startActivity(new Intent(getActivity(), ConfenceActivity.class).putExtra("entityId", addressDataList.get(position).getEntityId()));
                }
            }
        });
    }

    //显示首页门票业务
    private void showTicket() {
        if (ticketDataList.size() > 0) {
            viewTicket.setVisibility(View.VISIBLE);
            rlTitleTicket.setVisibility(View.VISIBLE);
            homeTicketAdapter = new HomeTicketAdapter(mContext, ticketDataList);
            rlMenpiao.setLayoutManager(new FullyGridLayoutManager(getActivity(), 3));
            rlMenpiao.setAdapter(homeTicketAdapter);
            homeTicketAdapter.setOnItemClickListener(new HomeTicketAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    startActivity(new Intent(getActivity(), DetailBuyActivity.class).putExtra(HandlerConstant.DETAIL_BUY, ticketDataList.get(position)));
                }
            });
        } else {
            viewTicket.setVisibility(View.GONE);
            rlTitleTicket.setVisibility(View.GONE);
        }
    }

    //显示首页团购业务
    private void showTeam() {
        if (teamDataList.size() > 0) {
            viewTeam.setVisibility(View.VISIBLE);
            rlTitleTeam.setVisibility(View.VISIBLE);
            homeTeamAdapter = new HomeAddressAdapter(mContext, teamDataList);
            rlTuangou.setLayoutManager(new FullyGridLayoutManager(getActivity(), 2));
            rlTuangou.setAdapter(homeTeamAdapter);
            homeTeamAdapter.setOnItemClickListener(new HomeAddressAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    startActivity(new Intent(getActivity(), DetailBuyActivity.class).putExtra(HandlerConstant.DETAIL_BUY, teamDataList.get(position)));
                }
            });
        } else {
            viewTeam.setVisibility(View.GONE);
            rlTitleTeam.setVisibility(View.GONE);
        }
    }

    //显示资讯业务
    private void showInfo(CommonBean bean) {
        if (infoDatas == null) {//首次进入
            infoDatas = bean.getData();
            infoAdapter = new DetailBlogAdapter(mActivity, infoDatas);
            rlInfo.setLayoutManager(new FullyLinearLayoutManager(mActivity));
            rlInfo.setAdapter(infoAdapter);
            rlInfo.setNestedScrollingEnabled(false);
            infoAdapter.setOnItemClickListener(new DetailBlogAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    startActivity(new Intent(mActivity, WebViewActivity.class).putExtra(HandlerConstant.DETAIL_BLOG, infoDatas.get(position)));
                }
            });
        } else {//下拉刷新
            infoDatas.clear();
            infoDatas.addAll(bean.getData());
            infoAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 按钮点击事件
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //跳转到展会搜索
            case R.id.home_iv_exhibition:
                startActivity(new Intent(getActivity(), HomeFindEXActivity.class));
                break;
            //跳转资讯界面
            case R.id.home_iv_info:
                startActivity(new Intent(getActivity(), HomeInfoActivity.class));
                break;
            //跳转团购界面
            case R.id.home_iv_team:
                startActivity(new Intent(getActivity(), HomeTeamActivity.class));
                break;
            //跳转门票界面
            case R.id.home_iv_ticket:
                startActivity(new Intent(getActivity(), HomeTicketActivity.class));
                break;
            //跳转到发现海南
            case R.id.home_iv_find:
                mainActivity.showClickFragment(2);
                break;
            //跳转到招商界面
            case R.id.home_iv_busi:
                startActivity(new Intent(getActivity(), HomeBusiActivity.class));
                break;
            //弹出更多的选项
            case R.id.iv_main_add:
                //显示弹出更多的选项
                showPopupWindow();
                break;
            //我的二维码
            case R.id.ll_add_qrcode:
                if (MyApplication.isLogin) {
                    startActivity(new Intent(getActivity(), MyQrcodeActivity.class));
                    mPopuWindow.dismiss();
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            //扫一扫
            case R.id.ll_add_scan:
                startActivityForResult(new Intent(getActivity(), CaptureActivity.class), ZXING_SCAN_SUCCESS);
                mPopuWindow.dismiss();
                break;
            //我的展会
            case R.id.ll_add_myex:
                startActivity(new Intent(getActivity(), MyExhibiActivity.class));
                mPopuWindow.dismiss();
                break;
            //我的个人中心
            case R.id.ll_add_personal:
                mainActivity.showClickFragment(3);
                mPopuWindow.dismiss();
                break;
            //上下轮播广告
            case R.id.viewFlipper:
                break;
            case R.id.ll_home_exhibit_more://更多展会
                startActivity(new Intent(getActivity(), HomeFindEXActivity.class));
                break;
            case R.id.ll_home_ticket_more://更多门票
                startActivity(new Intent(getActivity(), HomeTicketActivity.class));
                break;
            case R.id.ll_home_team_more://更多团购
                startActivity(new Intent(getActivity(), HomeTeamActivity.class));
                break;
            case R.id.ll_home_more_blog:
                startActivity(new Intent(getActivity(), HomeInfoActivity.class));
                break;
            case R.id.ll_home_search://搜索
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.tv_home_local:
                startActivityForResult(new Intent(getActivity(), ChoiceCityActivity.class), REQUEST_CODE_PICK_CITY);
                break;
            case R.id.iv_home_fragment_mid:
                startActivity(new Intent(mainActivity, WebViewActivity.class).putExtra(HandlerConstant.DETAIL_BLOG, midDataList.get(0)));
                break;

        }

    }


    /**
     * 显示右上角更多
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showPopupWindow() {
        //加载popupWindow中的布局
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.item_add, null);
        //创建popupWindow对象，并把布局加载进去
        mPopuWindow = new PopupWindow(contentView);
        //设置宽度
        mPopuWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置高度
        mPopuWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //这两行是，设置点击空白处，popupWindow隐藏消失
        mPopuWindow.setFocusable(true);
//        mPopuWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopuWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        if (Build.VERSION.SDK_INT >= 21) {
            mPopuWindow.setElevation(10);
        }
        //找到布局里的控件
        LinearLayout llAddScan = (LinearLayout) contentView.findViewById(R.id.ll_add_scan);
        LinearLayout llMyEx = (LinearLayout) contentView.findViewById(R.id.ll_add_myex);
        LinearLayout llAPersonal = (LinearLayout) contentView.findViewById(R.id.ll_add_personal);
        LinearLayout llChange = (LinearLayout) contentView.findViewById(R.id.ll_add_qrcode);
        //设置监听器
        llAddScan.setOnClickListener(this);
        llMyEx.setOnClickListener(this);
        llAPersonal.setOnClickListener(this);
        llChange.setOnClickListener(this);
        //显示弹出添加窗口
        mPopuWindow.showAsDropDown(ivMainAdd);
    }

    /**
     * 显示上下滚动广告
     */
    private void showViewFlipper(List<CommonBean.Data> datas) {
        for (final CommonBean.Data data : datas) {
            //加载布局
            View flipperView = View.inflate(getActivity(), R.layout.item_home_fragment_advert, null);
            //文字控件
            TextView tvFilpperView = (TextView) flipperView.findViewById(R.id.tv_filpperView);
            tvFilpperView.setText(data.getTitle());
//            图片控件
            ImageView ivFilpperView = (ImageView) flipperView.findViewById(R.id.iv_filpperView);
            Glide.with(mActivity).load(data.getImageUrl()).error(R.drawable.error).into(ivFilpperView);
            //设置监听器
            flipperView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mainActivity, WebViewActivity.class).putExtra(HandlerConstant.DETAIL_BLOG, data));
                }
            });
            //添加view
            mViewFlipper.addView(flipperView);
        }
    }

    //下拉刷新
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    //下拉刷新
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        //获取首页广告
        model.getData(handler, UrlConstant.HTTP_URL_PAGE_ADVERS, HandlerConstant.HOME_PAGE_SUCCESS, adversParam);
        //获取首页业务
        model.getData(handler, UrlConstant.HTTP_URL_HOME_BUS, HandlerConstant.HOME_BUSI_SUCCESS, "");
        //获取首页自选
        model.getData(handler, UrlConstant.HTTP_URL_INFO_SEARCH_BLOG, HandlerConstant.GET_INFO, infoParam);
    }

    /**
     * 切换语言
     */
    private void changeLanguage() {
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        Locale mLocale = null;
        if (language.equals("zh")) {
            mLocale = new Locale("en");
        } else if (language.equals("en")) {
            mLocale = new Locale("zh");
        }
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = mLocale;
        res.updateConfiguration(conf, dm);
        //获得对象
        activity = (MainActivity) getActivity();
        //调用Main activity中的刷新方法
        activity.recreate();
        //隐藏
        mPopuWindow.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == ChoiceCityActivity.SUCCESS) {
            if (data != null) {
                String city = data.getStringExtra(ChoiceCityActivity.CITY);
                this.city = city;
                tvLoacl.setText(city);
//                //获取首页广告
//                model.getData(handler, UrlConstant.HTTP_URL_PAGE_ADVERS, HandlerConstant.HOME_PAGE_SUCCESS, adversParam + city);
//                //获取首页业务
//                model.getData(handler, UrlConstant.HTTP_URL_HOME_BUS, HandlerConstant.HOME_BUSI_SUCCESS, busiParam + city);
            }
        }
    }
}
