package com.example.mylibrary.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/3/24.
 * 自定义ViewPager 可禁止左右滑动
 */

public class CustomViewPager extends ViewPager {
    private boolean scrollble = true;
    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }
}
