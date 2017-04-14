package com.ex.administrator.zhanhui.view.homeInfoHeader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.ex.administrator.zhanhui.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderHomeInfoAdvertView implements View.OnClickListener {
    @ViewInject(R.id.iv_home_info_big)
    private ImageView ivBig;
    @ViewInject(R.id.iv_home_info_small_1)
    private ImageView ivSmall_1;
    @ViewInject(R.id.iv_home_info_small_2)
    private ImageView ivSmall_2;


    private Context context;

    public HeaderHomeInfoAdvertView(Activity context) {
        this.context = context;
    }

    public void getView(ListView listView) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_home_fragment_info_advers, listView, false);
        x.view().inject(this, view);
        setListeners();
        listView.addHeaderView(view);
    }

    //设置监听器
    private void setListeners() {
        ivBig.setOnClickListener(this);
        ivSmall_1.setOnClickListener(this);
        ivSmall_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home_info_big:
                if (onItemAdvertClickListtener != null) {
                    onItemAdvertClickListtener.OnChannelClick("big");
                }
                break;
            case R.id.iv_home_info_small_1:
                if (onItemAdvertClickListtener != null) {
                    onItemAdvertClickListtener.OnChannelClick("small_1");
                }
                break;
            case R.id.iv_home_info_small_2:
                if (onItemAdvertClickListtener != null) {
                    onItemAdvertClickListtener.OnChannelClick("small_2");
                }
                break;
        }
    }

    //频道点击事件
    private OnItemAdvertClickListtener onItemAdvertClickListtener;

    //设置点击事件
    public void setOnItemAdvertClickListtener(OnItemAdvertClickListtener onItemAdvertClickListtener) {
        this.onItemAdvertClickListtener = onItemAdvertClickListtener;
    }

    //点击事件回调
    public interface OnItemAdvertClickListtener {
        void OnChannelClick(String title);
    }


}
