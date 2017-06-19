package com.zyrc.exhibit.fragment.browse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.activity.DetailBuyActivity;
import com.zyrc.exhibit.adapter.CollectAdapter;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.model.Model;
import com.zyrc.exhibit.util.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/1 0001.
 */

public class BrowseProductFragment extends Fragment {
    @ViewInject(R.id.rl_view_product)
    private RecyclerView rlProduct;
    private String param = "";
    private int page = 1;
    private int itemPerPage = 20;
    private String paramNum = "";
    private String id;//用户id
    private String userId = "&userId=";//id参数
    private String eventType = "&eventType=view";//查询的类型(大分类，收藏)
    private String entityName = "&entityName=product";//查询的类型(小分类，产品)
    private CollectAdapter adapter;
    private List<CommonBean.Data> datas;
    private Model model;
    private Map<String, String> mapParam;
    private int index;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    String json = (String) msg.obj;
                    CommonBean mCommonBean = new Gson().fromJson(json, CommonBean.class);
                    datas = mCommonBean.getData();
                    if (datas == null) {
                        datas = new ArrayList<>();
                    }
                    showProduct(datas);
                    break;
                case HandlerConstant.COLLECT_CANCEL:
                    datas.remove(index);
                    adapter.notifyDataSetChanged();
                    ToastUtil.show(getActivity(),"已删除!");
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_product, container,false);
        x.view().inject(this, view);
        id = MyApplication.userId;
        paramNum = "?page=" + page + "&itemPerPage=" + itemPerPage;
        userId = userId + id;
        param = paramNum + userId + eventType + entityName;
        Model model = new Model();
        model.getData(handler, UrlConstant.HTTP_URL_SEARCH_COLLECT, HandlerConstant.SEARCH_SUCCESS, param);
        return view;
    }

    private void showProduct(final List<CommonBean.Data> datas) {
        rlProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CollectAdapter(R.layout.item_collect_list, datas);
        rlProduct.setAdapter(adapter);
        if(datas.size() == 0){
//            adapter.setNewData(null);
            adapter.setEmptyView(R.layout.item_no_data_layout, (ViewGroup) rlProduct.getParent());
        }
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(),DetailBuyActivity.class).putExtra(HandlerConstant.DETAIL_BUY,datas.get(position)));
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                index = position;
                collect(UrlConstant.HTTP_URL_CANCEL_COLLECT, HandlerConstant.COLLECT_CANCEL, position);
            }
        });
    }

    /**
     * 进行收藏，是否已经收藏的查询
     */
    private void collect(String url, int requestCode, int position) {
        if (MyApplication.isLogin) {
            if (model == null) {
                model = new Model();
            }
            if (mapParam == null) {
                mapParam = new HashMap<>();
            }
            mapParam.put("userId", MyApplication.userId);
            mapParam.put("eventType", "view");
            mapParam.put("entityName", datas.get(position).getEntityName());
            mapParam.put("entityId", String.valueOf(datas.get(position).getEntityId()));
            model.postData(handler, url, requestCode, mapParam);
        }
    }
}