package com.zyrc.exhibit.fragment;

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

import com.google.gson.Gson;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.detailex.DetailPlanAdapter;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.model.Model;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/4/1 0001.
 */

public class PlanFragment extends Fragment {
    @ViewInject(R.id.rl_branch_plan)
    private RecyclerView rlPlan;
    Model model = new Model();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    String data = (String) msg.obj;
                    CommonBean mCommonBean = new Gson().fromJson(data, CommonBean.class);
                    showPlan(mCommonBean.getData());
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch, null);
        x.view().inject(this, view);
        int entityId = getActivity().getIntent().getIntExtra("entityId", 1);
        String param = "?Page=1&itemsPerPage=100&exhibId=" + entityId;
        model.getData(handler, UrlConstant.HTTP_URL_DETAIL_EX_PLAN, HandlerConstant.SEARCH_SUCCESS, param);
        return view;
    }

    private void showPlan(List<CommonBean.Data> datas) {
        rlPlan.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlPlan.setAdapter(new DetailPlanAdapter(getActivity(), datas));

    }
}
