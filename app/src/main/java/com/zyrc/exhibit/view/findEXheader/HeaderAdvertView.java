package com.zyrc.exhibit.view.findEXheader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.HomeRollPagerAdapter;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.entity.HomePageBean;
import com.zyrc.exhibit.model.Model;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderAdvertView {
    private Context context;
    @ViewInject(R.id.rpv_find_ex)
    RollPagerView mRollPagerView;//轮播广告
    private String param = "pg-exhib-top";
    private List<CommonBean.Data> datas;

    public HeaderAdvertView(Activity context) {
        this.context = context;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //首页广告获取成功
            if (msg.what == HandlerConstant.HOME_PAGE_SUCCESS) {
                String json = (String) msg.obj;
                HomePageBean homePageBeanAdvers = new Gson().fromJson(json, HomePageBean.class);
                for (int i = 0; i < homePageBeanAdvers.getData().size(); i++) {
                    if (homePageBeanAdvers.getData().get(i).getName().equals(param)) {
                        datas = homePageBeanAdvers.getData().get(i).getDataList();
                    }
                }
                showAdvertisement(datas);
            }
        }
    };

    public void getView(ListView listView) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_find_exhibition_advert, listView, false);
        x.view().inject(this, view);

        listView.addHeaderView(view);
        Model model = new Model();
        model.getData(handler, UrlConstant.HTTP_URL_PAGE_ADVERS, HandlerConstant.HOME_PAGE_SUCCESS, "?name=pg-exhib-top");
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
            mRollPagerView.setHintView(new ColorPointHintView(context, Color.WHITE, Color.GRAY));
            HomeRollPagerAdapter adapter = new HomeRollPagerAdapter(mRollPagerView, topData);
            mRollPagerView.setAdapter(adapter);
            //中部广告
        }
        //轮播图点击事件
        mRollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (onItemAdvertClickListtener != null) {
                    onItemAdvertClickListtener.OnChannelClick(topData.get(position));
                }
            }
        });
    }

    //频道点击事件
    private OnItemAdvertClickListtener onItemAdvertClickListtener;

    //设置点击事件
    public void setOnItemAdvertClickListtener(OnItemAdvertClickListtener onItemAdvertClickListtener) {
        this.onItemAdvertClickListtener = onItemAdvertClickListtener;
    }

    //点击事件回调
    public interface OnItemAdvertClickListtener {
        void OnChannelClick(CommonBean.Data data);
    }
}
