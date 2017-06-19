package com.zyrc.exhibit.view.extrend;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
 * Created by Administrator on 2017/5/28 0028.
 */

public class ExTrendAdvertHeader implements View.OnClickListener {
    @ViewInject(R.id.iv_trend_1)
    private ImageView iv1;
    @ViewInject(R.id.iv_trend_2)
    private ImageView iv2;
    @ViewInject(R.id.iv_trend_3)
    private ImageView iv3;

    private Context context;

    private Model model;
    private String param = "pg-exhib-dync";
    private List<CommonBean.Data> datas;

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

    public ExTrendAdvertHeader(Context context) {
        this.context = context;
        model = new Model();
    }

    public void getView(BaseQuickAdapter adapter, RecyclerView recyclerView) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ex_trends_advert, (ViewGroup) recyclerView.getParent(), false);
        x.view().inject(this, view);
        model.getData(handler, UrlConstant.HTTP_URL_PAGE_ADVERS, HandlerConstant.HOME_PAGE_SUCCESS, "?name=" + param);
        adapter.addHeaderView(view);
    }

    private void showAdvertisement(List<CommonBean.Data> datas) {
        if (datas.size() > 2) {
            Glide.with(context).load(datas.get(0).getImageUrl()).error(R.drawable.error).into(iv1);
            Glide.with(context).load(datas.get(1).getImageUrl()).error(R.drawable.error).into(iv2);
            Glide.with(context).load(datas.get(2).getImageUrl()).error(R.drawable.error).into(iv3);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
