package com.zyrc.exhibit.view.homeInfoHeader;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zyrc.exhibit.R;
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
public class HeaderHomeInfoAdvertView implements View.OnClickListener {
    @ViewInject(R.id.iv_home_info_big)
    private ImageView ivBig;
    @ViewInject(R.id.iv_home_info_small_1)
    private ImageView ivSmall_1;
    @ViewInject(R.id.iv_home_info_small_2)
    private ImageView ivSmall_2;

    private List<CommonBean.Data> datas;

    private Context context;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //首页广告获取成功
            if (msg.what == HandlerConstant.HOME_PAGE_SUCCESS) {
                String json = (String) msg.obj;
                HomePageBean homePageBeanAdvers = new Gson().fromJson(json, HomePageBean.class);
                for (int i = 0; i < homePageBeanAdvers.getData().size(); i++) {
                    if (homePageBeanAdvers.getData().get(i).getName().equals("pg-news-top")) {
                        datas = homePageBeanAdvers.getData().get(i).getDataList();
                    }
                }
                showAdver(datas);
            }
        }
    };

    public HeaderHomeInfoAdvertView(Activity context) {
        this.context = context;
    }

    public void getView(ListView listView) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_home_fragment_info_advers, listView, false);
        x.view().inject(this, view);
        setListeners();
        listView.addHeaderView(view);
        Model model = new Model();
        model.getData(handler, UrlConstant.HTTP_URL_PAGE_ADVERS, HandlerConstant.HOME_PAGE_SUCCESS, "?name=pg-news-top");
    }

    //设置监听器
    private void setListeners() {
        ivBig.setOnClickListener(this);
        ivSmall_1.setOnClickListener(this);
        ivSmall_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home_info_big:
                if (onItemAdvertClickListtener != null) {
                    onItemAdvertClickListtener.OnChannelClick("big");
                }
                break;
            case R.id.iv_home_info_small_1:
                if (onItemAdvertClickListtener != null) {
                    onItemAdvertClickListtener.OnChannelClick("small_1");
                }
                break;
            case R.id.iv_home_info_small_2:
                if (onItemAdvertClickListtener != null) {
                    onItemAdvertClickListtener.OnChannelClick("small_2");
                }
                break;
        }
    }

    private void showAdver(List<CommonBean.Data> datas) {
        if (datas.size() > 2) {
            Glide.with(context).load(datas.get(0).getImageUrl()).error(R.drawable.error).into(ivBig);
            Glide.with(context).load(datas.get(1).getImageUrl()).error(R.drawable.error).into(ivSmall_1);
            Glide.with(context).load(datas.get(2).getImageUrl()).error(R.drawable.error).into(ivSmall_2);
        }
    }

    //频道点击事件
    private OnItemAdvertClickListtener onItemAdvertClickListtener;

    //设置点击事件
    public void setOnItemAdvertClickListtener(OnItemAdvertClickListtener onItemAdvertClickListtener) {
        this.onItemAdvertClickListtener = onItemAdvertClickListtener;
    }

    //点击事件回调
    public interface OnItemAdvertClickListtener {
        void OnChannelClick(String title);
    }


}
