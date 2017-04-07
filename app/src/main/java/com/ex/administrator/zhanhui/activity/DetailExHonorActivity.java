package com.ex.administrator.zhanhui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.adapter.detailex.HonorAdapter;
import com.ex.administrator.zhanhui.constant.HandlerConstant;
import com.ex.administrator.zhanhui.constant.UrlConstant;
import com.ex.administrator.zhanhui.entity.CommonBean;
import com.ex.administrator.zhanhui.model.GetDataModel;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21 0021.
 */

public class DetailExHonorActivity extends AppCompatActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_detail_ex_honor_back)
    private ImageView ivBack;
    @ViewInject(R.id.rl_detail_ex_honor)
    private RecyclerView rlHonor;

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
    private GetDataModel model = new GetDataModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ex_honor);
        x.view().inject(this);
        seListeners();
        model.search(handler, UrlConstant.HTTP_URL_DETAIL_EX_HONOR, "exhibId=1");
    }

    //设置监听器
    private void seListeners() {
        ivBack.setOnClickListener(this);
    }


    //显示嘉宾
    private void shoeHonor(CommonBean bean) {
        List<CommonBean.Data> datas;
        datas = bean.getData();
        HonorAdapter adapter = new HonorAdapter(this, datas);
        rlHonor.setLayoutManager(new GridLayoutManager(this, 2));
        rlHonor.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.iv_detail_ex_honor_back:
                finish();
                break;
        }
    }
}
