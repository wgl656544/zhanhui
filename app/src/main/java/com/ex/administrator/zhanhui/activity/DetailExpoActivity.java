package com.ex.administrator.zhanhui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.adapter.DetailExRollPagerAdapter;
import com.ex.administrator.zhanhui.constant.HandlerConstant;
import com.ex.administrator.zhanhui.constant.UrlConstant;
import com.ex.administrator.zhanhui.entity.CommonListBean;
import com.ex.administrator.zhanhui.entity.DetailExBean;
import com.ex.administrator.zhanhui.model.GetDtaListModel;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class DetailExpoActivity extends AppCompatActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_detail_expo)
    private ImageView ivDetailEx;
    @ViewInject(R.id.tv_detail_expo_name)
    private TextView tvDetailExName;
    @ViewInject(R.id.tv_detail_expo_date)
    private TextView tvDetailExDate;
    @ViewInject(R.id.tv_detail_expo_city)
    private TextView tvDetailExCity;
    @ViewInject(R.id.tv_detail_expo_address)
    private TextView tvDetailExAddress;
    @ViewInject(R.id.tv_detail_expo_busi)
    private TextView tvDetailExBusi;
    @ViewInject(R.id.rpv_detail_expo)
    private RollPagerView rpvDetailEx;//轮播广告
    @ViewInject(R.id.iv_detail_expo_back)
    private ImageView ivDetailExBack;//返回
    @ViewInject(R.id.ll_detail_expo_place)
    private LinearLayout llPlace;//场所
    @ViewInject(R.id.ll_detail_expo_event)
    private LinearLayout llEvent;//活动安排
    @ViewInject(R.id.ll_detail_expo_honor)
    private LinearLayout llHonor;//嘉宾
    @ViewInject(R.id.ll_detail_expo_ticket)
    private LinearLayout llTicket;//购票
    @ViewInject(R.id.ll_detail_expo_busi)
    private LinearLayout llBusi;//商务
    @ViewInject(R.id.ll_detail_expo_vote)
    private LinearLayout llVote;//投票
    @ViewInject(R.id.ll_detail_expo_news)
    private LinearLayout llNews;//新闻
    @ViewInject(R.id.ll_detail_expo_exhibitor)
    private LinearLayout llExhibitor;//参展商
    @ViewInject(R.id.ll_detail_expo_team)
    private LinearLayout llTeam;//团购

    private GetDtaListModel model = new GetDtaListModel();
    private DetailExBean detailExBean;//展会详细信息实体类
    private CommonListBean detailExAdvertBean;//展会详细页面广告
    private CommonListBean detailExInfoBean;
    private List<CommonListBean.DataList> honorList;//嘉宾
    private List<CommonListBean.DataList> subList;//论坛
    private List<CommonListBean.DataList> ticketList;//票务
    private List<CommonListBean.DataList> blogList;//资讯
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
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_expo);
        x.view().inject(this);
        setListeners();
        model.getDetailEx(handler, 1);
        model.getDataList(handler, UrlConstant.HTTP_URL_DETAIL_EX_ADVERT, "exhibId=1&name=ex-hy-home-top");
        model.getDataList(handler, UrlConstant.HTTP_URL_DETAIL_EX_INFO, "exhibId=1");
    }

    //设置监听器
    private void setListeners() {
        ivDetailExBack.setOnClickListener(this);
        llPlace.setOnClickListener(this);
        llEvent.setOnClickListener(this);
        llHonor.setOnClickListener(this);
        llTicket.setOnClickListener(this);
        llBusi.setOnClickListener(this);
        llVote.setOnClickListener(this);
        llNews.setOnClickListener(this);
        llExhibitor.setOnClickListener(this);
        llTeam.setOnClickListener(this);
    }


    //显示展会的详细信息
    private void showExDetail(DetailExBean bean) {
        //显示图片
//        Picasso.with(this).load(bean.getData().getImageUrl()).into(ivDetailEx);
        Glide.with(this).load(bean.getData().getImageUrl()).into(ivDetailEx);
        //显示展会名称
        tvDetailExName.setText(bean.getData().getName());
        //显示展会时间
        tvDetailExDate.setText(bean.getData().getStartDate() + "至" + bean.getData().getEndDate());
        //显示展会城市
        tvDetailExCity.setText(bean.getData().getAddress().getCity());
        //显示展会地址
        tvDetailExAddress.setText(bean.getData().getAddress().getStreet());
        //显示合作信息
        tvDetailExBusi.setText("商务:" + bean.getData().getTel());
    }

    //显示轮播广告
    private void showAdvert(CommonListBean bean) {
        List<CommonListBean.DataList> data = new ArrayList<>();
        for (int i = 0; i < bean.getData().size(); i++) {
            if (bean.getData().get(i).getName().equals("ex-hy-home-top")) {
                data = bean.getData().get(i).getDataList();
            }
        }
        rpvDetailEx.setAnimationDurtion(500);
        //设置指示器的颜色
        rpvDetailEx.setHintView(new ColorPointHintView(this, Color.WHITE, Color.GRAY));
        DetailExRollPagerAdapter adapter = new DetailExRollPagerAdapter(rpvDetailEx, data);
        rpvDetailEx.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_detail_expo_back:
                finish();
                break;
            //场所
            case R.id.ll_detail_expo_place:
                break;
            //活动安排
            case R.id.ll_detail_expo_event:
                break;
            //嘉宾
            case R.id.ll_detail_expo_honor:
                startActivity(new Intent(this, DetailExHonorActivity.class));
                break;
            //购票
            case R.id.ll_detail_expo_ticket:
                startActivity(new Intent(this, DetailExTicketActivity.class));
                break;
            //商务
            case R.id.ll_detail_expo_busi:
                break;
            //投票
            case R.id.ll_detail_expo_vote:
                break;
            //新闻
            case R.id.ll_detail_expo_news:
                startActivity(new Intent(this, DetailExNewsActivity.class));
                break;
            //参展商
            case R.id.ll_detail_expo_exhibitor:
                break;
            //团购
            case R.id.ll_detail_expo_team:
                break;
        }
    }
}
