package com.zyrc.exhibit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mylibrary.base.BaseActivity;
import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.ChoiceCityAdapter;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.entity.HotCityBean;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13 0013.
 */

public class ChoiceCityActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_choice_city_back)
    private ImageView ivBack;
    @ViewInject(R.id.rl_choice_city)
    private RecyclerView rlCity;

    public static final int SUCCESS = 1;
    public static final String CITY = "city";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_city);
        x.view().inject(this);
        setListeners();
        initview();
    }

    private void initview() {
        final List<HotCityBean.Data> datas = MyApplication.getHotCityBean().getData();
        if (datas != null) {
            ChoiceCityAdapter adapter = new ChoiceCityAdapter(datas);
            rlCity.setLayoutManager(new GridLayoutManager(this, 3));
            rlCity.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent();
                    intent.putExtra(CITY,datas.get(position).getName());
                    setResult(SUCCESS,intent);
                    finish();
                }
            });
        }
    }

    //设置监听器
    private void setListeners() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_choice_city_back:
                finish();
                break;
        }
    }
}
