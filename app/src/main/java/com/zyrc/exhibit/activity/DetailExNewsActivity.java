package com.zyrc.exhibit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.detailex.NewsAdapter;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.model.GetDataModel;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * 会议主页跳转的 资讯页面
 * Created by Administrator on 2017/2/21 0021.
 */

public class DetailExNewsActivity extends AppCompatActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_detail_ex_news_back)
    private ImageView ivBack;
    @ViewInject(R.id.lv_detail_ex_news)
    private ListView lvNews;

    private Activity mActivity;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HandlerConstant.SEARCH_SUCCESS) {
                CommonBean detailExCommonBean = (CommonBean) msg.obj;
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
        mActivity = this;
        setListeners();
        int exhibId = getIntent().getIntExtra("entityId", 1);
        GetDataModel model = new GetDataModel();
        model.search(handler, UrlConstant.HTTP_URL_DETAIL_EX_NEWS, "exhibId=" + exhibId);
    }

    //设置监听器
    private void setListeners() {
        ivBack.setOnClickListener(this);
    }

    //显示新闻资讯
    private void showNews(CommonBean bean) {
        final List<CommonBean.Data> datas = bean.getData();
        lvNews.setAdapter(new NewsAdapter(this, datas));
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mActivity, WebViewActivity.class).putExtra(HandlerConstant.DETAIL_BLOG, datas.get(position)));
            }
        });
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
