package com.example.mylibrary.scollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyNearScrollView extends ScrollView {

	float startY;

	public MyNearScrollView(Context context) {
		super(context);
	}

	public MyNearScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyNearScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		onTouchEvent(e);
		if (e.getAction() == MotionEvent.ACTION_DOWN)
			startY = e.getY();
		return (e.getAction() == MotionEvent.ACTION_MOVE)
				&& (Math.abs(startY - e.getY()) > 50);
	}

}
