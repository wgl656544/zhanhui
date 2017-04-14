package com.ex.administrator.zhanhui.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.activity.HomeBusiActivity;
import com.ex.administrator.zhanhui.activity.HomeFindEXActivity;
import com.ex.administrator.zhanhui.activity.HomeInfoActivity;
import com.ex.administrator.zhanhui.activity.HomeTeamActivity;
import com.ex.administrator.zhanhui.activity.HomeTicketActivity;
import com.ex.administrator.zhanhui.activity.MainActivity;
import com.ex.administrator.zhanhui.adapter.HomeAddressAdapter;
import com.ex.administrator.zhanhui.adapter.HomeRollPagerAdapter;
import com.ex.administrator.zhanhui.adapter.HomeTicketAdapter;
import com.ex.administrator.zhanhui.application.MyApplication;
import com.ex.administrator.zhanhui.constant.HandlerConstant;
import com.ex.administrator.zhanhui.entity.HomePageBean;
import com.ex.administrator.zhanhui.entity.HotCityBean;
import com.ex.administrator.zhanhui.model.HomePageModel;
import com.ex.administrator.zhanhui.model.HotCityModel;
import com.ex.administrator.zhanhui.util.FullyGridLayoutManager;
import com.ex.administrator.zhanhui.util.NetUtils;
import com.ex.administrator.zhanhui.util.ToastUtil;
import com.ex.administrator.zhanhui.zxing.activity.CaptureActivity;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class HomeFragment extends Fragment implements View.OnClickListener, PtrHandler {
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
    @ViewInject(R.id.rl_main_add)
    private RelativeLayout rlMainAdd;//更多选项
    @ViewInject(R.id.sp_home_fragment_spinner)
    private Spinner mSpinner;//城市选项
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
    @ViewInject(R.id.home_iv_find)
    private ImageView ivfind;//发现
    @ViewInject(R.id.home_iv_busi)
    private ImageView ivBusi;//招商

    @ViewInject(R.id.et_home_fragment)
    private EditText etHomeFragment;//搜索框


    private PopupWindow mPopuWindow;//"更多"弹出窗口

    private HomePageModel homePageModel = new HomePageModel();//首页广告，业务获取对象
    private HomePageBean homePageBeanAdvers;//首页广告图片实体类
    private HomePageBean homePageBeanBusi;//首页业务实体类

    private HotCityBean hotCityBean;//热门城市实体类对象
    private HotCityModel hotCityModel;//热门城市获取对象

    private List<HomePageBean.DataList> topDataList;//顶部轮播广告
    private List<HomePageBean.DataList> midDataList;//中间广告
    private List<HomePageBean.DataList> addressDataList;//地区展会业务
    private List<HomePageBean.DataList> ticketDataList;//门票业务
    private List<HomePageBean.DataList> teamDataList;//团购业务

    private HomeAddressAdapter homeAddressAdapter;//首页展会业务适配器
    private HomeTicketAdapter homeTicketAdapter;//首页门票业务适配器
    private static final int ZXING_SCAN_SUCCES = 1006;

    private String[] strs = {"如约而至，博鳌健康论坛召开！", "大咖降临，习大大亲临会场！", "史上最高，中科院院士集结！"};


    MainActivity activity;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //热门城市获取成功
            if (msg.what == HandlerConstant.HOT_CITY_SUCCESS) {
                hotCityBean = (HotCityBean) msg.obj;
                showHotCity(hotCityBean);
                MyApplication.setHotCityBean(hotCityBean);
            }
            //首页广告获取成功
            if (msg.what == HandlerConstant.HOME_PAGE_SUCCESS) {
                homePageBeanAdvers = (HomePageBean) msg.obj;
                //对广告进行分类
                sortHomeAdvers(homePageBeanAdvers);
            }
            //首页业务获取成功
            if (msg.what == HandlerConstant.HOME_BUSI_SUCCESS) {
                homePageBeanBusi = (HomePageBean) msg.obj;
                //对业务模块进行分类
                sortHomeBusi(homePageBeanBusi);
                ptrHomeFragment.refreshComplete();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        x.view().inject(this, view);
        initView();
        //显示上下滚动文字广告
        showViewFlipper();
        //设置点击事件
        setListeners();
        //增加数据
        initDatas();
        return view;
    }

    private void initView() {
        final StoreHouseHeader header = new StoreHouseHeader(getActivity());
        header.initWithString("ruichuang", 35);
        header.setTextColor(R.color.colorOrange);
        ptrHomeFragment.setHeaderView(header);
        ptrHomeFragment.addPtrUIHandler(header);
    }

    //显示热点城市
    private void showHotCity(HotCityBean hotCityBean) {
        final List<String> mData = new ArrayList<>();
        for (int i = 0; i < hotCityBean.getData().size(); i++) {
            mData.add(hotCityBean.getData().get(i).getName());
        }
        if (mData != null) {
            SpinnerAdapter mSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mData);
            mSpinner.setAdapter(mSpinnerAdapter);
        }
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView text = (TextView) view;
                text.setTextSize(18);
                text.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                text.setGravity(Gravity.CENTER);
                if (!NetUtils.isConnected(getActivity())) {
                    text.setText("海口");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //对首页广告数据进行分类
    private void sortHomeAdvers(HomePageBean homePageBeanAdvers) {
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

    /**
     * 显示首页顶部与中部广告
     */
    private void showAdvertisement(List<HomePageBean.DataList> topData) {
        if (mRollPagerView != null) {
            //顶部广告
            //设置轮播动画的速度(切换的速度)
            mRollPagerView.setAnimationDurtion(500);
            //设置指示器的颜色
            mRollPagerView.setHintView(new ColorPointHintView(getActivity(), Color.WHITE, Color.GRAY));
            HomeRollPagerAdapter adapter = new HomeRollPagerAdapter(mRollPagerView, topData);
            mRollPagerView.setAdapter(adapter);
        }

        //中部广告
        Glide.with(getActivity()).load(midDataList.get(0).getImageUrl()).into(midImageview);
    }

    //对首页业务进行分类
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

    //显示首页展会业务
    private void showAddress() {
        homeAddressAdapter = new HomeAddressAdapter(getActivity(), addressDataList);
        rlAddress.setLayoutManager(new FullyGridLayoutManager(getActivity(), 2));
        rlAddress.setAdapter(homeAddressAdapter);
    }

    //显示首页门票业务
    private void showTicket() {
        homeTicketAdapter = new HomeTicketAdapter(getActivity(), ticketDataList);
        rlMenpiao.setLayoutManager(new FullyGridLayoutManager(getActivity(), 3));
        rlMenpiao.setAdapter(homeTicketAdapter);
    }

    //显示首页团购业务
    private void showTeam() {
        homeAddressAdapter = new HomeAddressAdapter(getActivity(), teamDataList);
        rlTuangou.setLayoutManager(new FullyGridLayoutManager(getActivity(), 2));
//        rlTuangou.setNestedScrollingEnabled(false);
        rlTuangou.setAdapter(homeAddressAdapter);
    }

    /**
     * 获取数据
     */
    private void initDatas() {
        try {
            //获取热点城市
            hotCityModel = new HotCityModel();
            hotCityModel.getHotCity(handler);

            //获取首页广告
            homePageModel.getHomePageAdvers(handler);

            //获取首页业务
            homePageModel.getHomePageBusi(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置监听器
     */
    private void setListeners() {
        //下拉刷新
        ptrHomeFragment.setPtrHandler(this);
        //轮播图点击事件
        mRollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtil.show(getActivity(), "你点击了第" + position);
            }
        });
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

        //显示更多
        rlMainAdd.setOnClickListener(this);
        //上下轮播广告点击事件
        mViewFlipper.setOnClickListener(this);


        etHomeFragment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                ToastUtil.show(getActivity(), "点击了搜索");
                return true;
            }
        });
    }

    /**
     * 按钮点击事件
     */
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
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.showClickFragment(2);
                break;
            //跳转到招商界面
            case R.id.home_iv_busi:
                startActivity(new Intent(getActivity(), HomeBusiActivity.class));
                break;
            //弹出更多的选项
            case R.id.rl_main_add:
                //显示弹出更多的选项
                showPopupWindow();
                break;
            //切换语言
            case R.id.ll_add_change:
                //切换语言
                changeLanguage();
                //获得对象
                activity = (MainActivity) getActivity();
                //调用mainactivity中的刷新方法
                activity.recreate();
                //隐藏
                mPopuWindow.dismiss();
                break;
            //扫一扫
            case R.id.ll_add_scan:
                startActivityForResult(new Intent(getActivity(), CaptureActivity.class), ZXING_SCAN_SUCCES);
                mPopuWindow.dismiss();
                break;
            //我的展会
            case R.id.ll_add_myex:
                mPopuWindow.dismiss();
                break;
            //我的个人中心
            case R.id.ll_add_personal:
                mPopuWindow.dismiss();
                break;
            //上下轮播广告
            case R.id.viewFlipper:
                break;

        }

    }


    /**
     * 显示右上角更多
     */
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
        mPopuWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //找到布局里的控件
        LinearLayout llAddScan = (LinearLayout) contentView.findViewById(R.id.ll_add_scan);
        LinearLayout llMyEx = (LinearLayout) contentView.findViewById(R.id.ll_add_myex);
        LinearLayout llAPersonal = (LinearLayout) contentView.findViewById(R.id.ll_add_personal);
        LinearLayout llChange = (LinearLayout) contentView.findViewById(R.id.ll_add_change);
        //设置监听器
        llAddScan.setOnClickListener(this);
        llMyEx.setOnClickListener(this);
        llAPersonal.setOnClickListener(this);
        llChange.setOnClickListener(this);
        //显示弹出添加窗口
        mPopuWindow.showAsDropDown(rlMainAdd);
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
    }

    /**
     * 显示上下滚动广告
     */
    private void showViewFlipper() {
        for (String str : strs) {
            //加载布局
            View flipperView = View.inflate(getActivity(), R.layout.item_home_fragment_advert, null);
            //文字控件
            TextView tvFilpperView = (TextView) flipperView.findViewById(R.id.tv_filpperView);
            tvFilpperView.setText(str);
            //图片控件
//            ImageView ivFilpperView = (ImageView) flipperView.findViewById(R.id.iv_filpperView);
            //设置监听器
            flipperView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(getActivity(), mViewFlipper.getDisplayedChild() + "");
                }
            });
            //添加view
            mViewFlipper.addView(flipperView);
        }

    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    //下拉刷新
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        //重新发送请求
        homePageModel.getHomePageBusi(handler);
    }
}
