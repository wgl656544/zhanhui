package com.zyrc.exhibit.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.detailex.HonorAdapter;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.model.GetDataModel;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/4/1 0001.
 */

public class HonorFragment extends Fragment {

    @ViewInject(R.id.rl_branch_honor)
    private RecyclerView mRecyclerView;

    private GetDataModel model = new GetDataModel();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HandlerConstant.SEARCH_SUCCESS) {
                CommonBean detailExCommonBean = (CommonBean) msg.obj;
                if (detailExCommonBean != null) {
                    shoeHonor(detailExCommonBean);
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_honor, null);
        x.view().inject(this, view);
        int entityId = getActivity().getIntent().getIntExtra("entityId", 1);
        model.search(handler, UrlConstant.HTTP_URL_DETAIL_EX_HONOR, "exhibId=" + entityId);
        return view;
    }

    //显示嘉宾
    private void shoeHonor(CommonBean bean) {
        List<CommonBean.Data> datas;
        datas = bean.getData();
        HonorAdapter adapter = new HonorAdapter(getActivity(), datas);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(adapter);
    }
}
