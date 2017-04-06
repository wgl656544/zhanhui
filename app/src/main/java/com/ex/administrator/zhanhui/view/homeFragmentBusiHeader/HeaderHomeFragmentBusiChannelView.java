package com.ex.administrator.zhanhui.view.homeFragmentBusiHeader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.ex.administrator.zhanhui.R;

import org.xutils.x;


/**
 * Created by sunfusheng on 16/4/20.
 */
public class HeaderHomeFragmentBusiChannelView {

    private Context context;

    public HeaderHomeFragmentBusiChannelView(Activity context) {
        this.context = context;
    }

    public void getView(ListView listView) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_home_fragment_busi_channel, listView, false);
        x.view().inject(this, view);
        listView.addHeaderView(view);
    }
}
