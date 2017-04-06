package com.ex.administrator.zhanhui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.ex.administrator.zhanhui.R;
import com.ex.administrator.zhanhui.adapter.detailex.NewsAdapter;
import com.ex.administrator.zhanhui.constant.HandlerConstant;
import com.ex.administrator.zhanhui.entity.DetailExCommonBean;
import com.ex.administrator.zhanhui.model.DetailExModel;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21 0021.
 */

public class DetailExNewsActivity extends AppCompatActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_detail_ex_news_back)
    private ImageView ivBack;
    @ViewInject(R.id.lv_detail_ex_news)
    private ListView lvNews;

    private DetailExCommonBean detailExCommonBean;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HandlerConstant.DETAIL_EX_NEWS_SUCCESS) {
                detailExCommonBean = (DetailExCommonBean) msg.obj;
                if (detailExCommonBean != null) {
                    showNews(detailExCommonBean);
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ex_news);
        x.view().inject(this);
        setListeners();
        DetailExModel model = new DetailExModel();
        model.getDetailExNews(handler, 1);
    }

    //设置监听器
    private void setListeners() {
        ivBack.setOnClickListener(this);
    }

    //显示新闻资讯
    private void showNews(DetailExCommonBean bean) {
        List<DetailExCommonBean.Data> datas = bean.getData();
        lvNews.setAdapter(new NewsAdapter(this, datas));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_detail_ex_news_back:
                finish();
                break;
        }
    }
}
