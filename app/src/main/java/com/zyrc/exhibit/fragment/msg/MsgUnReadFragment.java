package com.zyrc.exhibit.fragment.msg;

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
import com.zyrc.exhibit.adapter.MyMsgAdapter;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.MsgBean;
import com.zyrc.exhibit.model.Model;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/1 0001.
 */

public class MsgUnReadFragment extends Fragment {
    @ViewInject(R.id.rl_unread_msg)
    private RecyclerView rlUnReadMsg;

    private Model model = new Model();
    private int page = 1;
    private int itemsPerPage = 20;

    private String TAG;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    String data = (String) msg.obj;
                    MsgBean bean = new Gson().fromJson(data, MsgBean.class);
                    showUnReadMsg(bean);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg_unread, container,false);
        x.view().inject(this, view);
        TAG = getActivity().getLocalClassName();
        String param = "?userId" + MyApplication.userId + "&page=" + page + "&itemsPerPage=" + itemsPerPage;
        model.getData(handler, UrlConstant.HTTP_URL_SEARCH_MSG, HandlerConstant.SEARCH_SUCCESS, param);
        return view;
    }

    //显示消息列表
    private void showUnReadMsg(MsgBean bean) {
        List<MsgBean.Data> datas = new ArrayList<>();
        for (MsgBean.Data data : bean.getData()) {
            if (data.getStatus() == 0) {
                datas.add(data);
            }
        }
        MyMsgAdapter adapter = new MyMsgAdapter(getActivity(), datas);
        rlUnReadMsg.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlUnReadMsg.setAdapter(adapter);
    }
}
