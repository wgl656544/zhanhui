package com.example.mylibrary.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2017/3/28.
 */

public class PopupMenu extends PopupWindow {

    public PopupMenu(Context context) {
        super(context);
    }

    public PopupMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PopupMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
