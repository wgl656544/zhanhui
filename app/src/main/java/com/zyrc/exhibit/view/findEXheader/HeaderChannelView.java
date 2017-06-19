package com.zyrc.exhibit.view.findEXheader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.zyrc.exhibit.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderChannelView implements View.OnClickListener {
    @ViewInject(R.id.ll_find_ex_meeting)
    private LinearLayout llFindExhibitionMeeting;
    @ViewInject(R.id.ll_find_ex_exposition)
    private LinearLayout llFindExhibitionExposition;
    @ViewInject(R.id.ll_find_ex_competition)
    private LinearLayout llFindExhibitionCompetition;
    private Context context;
    private Intent intent;

    public HeaderChannelView(Activity context) {
        this.context = context;
    }

    public void getView(ListView listView) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_find_exhibition_channel, listView, false);
        x.view().inject(this, view);
        listView.addHeaderView(view);
        initListener();
    }

    /**
     * 设置监听器
     */
    private void initListener() {
        llFindExhibitionMeeting.setOnClickListener(this);
        llFindExhibitionExposition.setOnClickListener(this);
        llFindExhibitionCompetition.setOnClickListener(this);
    }


    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //会议按钮
            case R.id.ll_find_ex_meeting:
                if (onItemChannelClickListtener != null) {
                    onItemChannelClickListtener.OnChannelClick("会议");
                }
                break;
            //博览会按钮
            case R.id.ll_find_ex_exposition:
                if (onItemChannelClickListtener != null) {
                    onItemChannelClickListtener.OnChannelClick("博览会");
                }
                break;
            //赛事按钮
            case R.id.ll_find_ex_competition:
                if (onItemChannelClickListtener != null) {
                    onItemChannelClickListtener.OnChannelClick("赛事");
                }
                break;
        }
    }

    //频道点击事件
    private OnItemChannelClickListtener onItemChannelClickListtener;

    //设置点击事件
    public void setOnItemChannelClickListtener(OnItemChannelClickListtener onItemChannelClickListtener) {
        this.onItemChannelClickListtener = onItemChannelClickListtener;
    }

    //点击事件回调
    public interface OnItemChannelClickListtener {
        void OnChannelClick(String title);
    }

}
