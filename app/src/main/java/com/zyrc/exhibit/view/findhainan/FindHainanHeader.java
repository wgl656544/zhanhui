package com.zyrc.exhibit.view.findhainan;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.FindHaiNanTypeAdapter;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.entity.FindHaiNanTypeBean;
import com.zyrc.exhibit.entity.HomePageBean;
import com.zyrc.exhibit.model.Model;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/5/28 0028.
 */

public class FindHainanHeader implements View.OnClickListener {
    @ViewInject(R.id.iv_find_hainan_advert)
    private ImageView ivAdvert;
    @ViewInject(R.id.rl_find_hainan_channel)
    private RecyclerView rlChannel;
    private Context context;
    private List<CommonBean.Data> datas;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    String json = (String) msg.obj;
                    FindHaiNanTypeBean bean = new Gson().fromJson(json, FindHaiNanTypeBean.class);
                    showTYpe(bean.getData());
                    break;
                case HandlerConstant.HOME_PAGE_SUCCESS:
                    String adverJson = (String) msg.obj;
                    HomePageBean bean1 = new Gson().fromJson(adverJson, HomePageBean.class);
                    showTop(bean1);
                    break;
            }
        }
    };

    public FindHainanHeader(Context context) {
        this.context = context;
    }

    public void getView(BaseQuickAdapter adapter, RecyclerView recyclerView) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_find_hainan_advert, (ViewGroup) recyclerView.getParent(), false);
        x.view().inject(this, view);
        initView();
        setListeners();
        adapter.addHeaderView(view);
        Model model = new Model();
        String param = "?termName=social";
        model.getData(handler, UrlConstant.HTTP_URL_FIND_HAINAN_TYPE, HandlerConstant.SEARCH_SUCCESS, param);
        //获取首页广告
        String adversParam = "?name=pg-social-top";
        model.getData(handler, UrlConstant.HTTP_URL_PAGE_ADVERS, HandlerConstant.HOME_PAGE_SUCCESS, adversParam);
    }

    private void initView() {
    }

    private void setListeners() {
        ivAdvert.setOnClickListener(this);
    }

    private void showTYpe(final List<FindHaiNanTypeBean.Data> datas) {
        datas.get(0).setStatus(1);
        rlChannel.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        FindHaiNanTypeAdapter adapter = new FindHaiNanTypeAdapter(R.layout.item_find_hainan_type_list, datas);
        rlChannel.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (FindHaiNanTypeBean.Data data : datas) {
                    data.setStatus(0);
                }
                datas.get(position).setStatus(1);
                adapter.notifyDataSetChanged();
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(datas.get(position).getCodeNo());
                }
            }
        });
    }

    private void showTop(HomePageBean bean) {
        if (bean.getData() != null && bean.getData().size() > 0) {
            for (int i = 0; i < bean.getData().size(); i++) {
                if (bean.getData().get(i).getName().equals("pg-social-top")) {
                    datas = bean.getData().get(i).getDataList();
                }
            }
            Glide.with(context).load(datas.get(0).getImageUrl()).error(R.drawable.error).into(ivAdvert);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_find_hainan_advert:
                break;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String typeName);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
