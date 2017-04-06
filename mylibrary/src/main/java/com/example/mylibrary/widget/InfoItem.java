package com.example.mylibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mylibrary.R;
import com.example.mylibrary.utils.DimenUtils;

/**
 * Created by Administrator on 2016/11/29.
 */

public class InfoItem extends LinearLayout {

    private TextView tvLeftInfo,tvRightInfo;
    private ImageView leftImage,rightImage;

    private Drawable leftDrawable;
    private String   leftInfoStr;
    private String   rightInfoStr;
    private int rightInfoBackground;
    
    private LayoutParams leftImageParams,rightImageParams;
    private LayoutParams leftTextParams,rightTextParams;


    public InfoItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.InfoItem);

        leftDrawable = ta.getDrawable(R.styleable.InfoItem_leftDrawable);
        leftInfoStr  = ta.getString(R.styleable.InfoItem_tvInfoStr);
        rightInfoStr = ta.getString(R.styleable.InfoItem_tvRightStr);
        rightInfoBackground = ta.getColor(R.styleable.InfoItem_tvBackground,0);

        ta.recycle();

        tvLeftInfo = new TextView(context);
        tvRightInfo = new TextView(context);

        leftImage = new ImageView(context);
        rightImage = new ImageView(context);

        tvLeftInfo.setText(leftInfoStr);
        tvLeftInfo.setTextColor(ContextCompat.getColor(context,R.color.black));
        tvLeftInfo.setTextSize(14);

        tvRightInfo.setText(rightInfoStr);
        tvRightInfo.setTextColor(rightInfoBackground);

        leftImage.setImageDrawable(leftDrawable);
        rightImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.right_arrow));

        leftImageParams = new LayoutParams(DimenUtils.dp2px(context,20),
                DimenUtils.dp2px(context,20));
        leftImageParams.leftMargin = DimenUtils.dp2px(context,10);
        leftImageParams.gravity = Gravity.CENTER_VERTICAL;
        leftImage.setLayoutParams(leftImageParams);

        rightImageParams = new LayoutParams(DimenUtils.dp2px(context,20),
                DimenUtils.dp2px(context,20));
        rightImageParams.rightMargin = DimenUtils.dp2px(context,16);
        rightImageParams.gravity = Gravity.CENTER_VERTICAL;
        rightImage.setLayoutParams(rightImageParams);

        leftTextParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        leftTextParams.gravity = Gravity.CENTER_VERTICAL;
        leftTextParams.weight = 1;
        leftTextParams.leftMargin = DimenUtils.dp2px(context,10);
        tvLeftInfo.setLayoutParams(leftImageParams);

        rightTextParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rightTextParams.gravity = Gravity.RIGHT|Gravity.CENTER_VERTICAL;
        rightTextParams.rightMargin = DimenUtils.dp2px(context,10);
        tvRightInfo.setTextSize(16);
        tvRightInfo.setPadding(DimenUtils.dp2px(context,5),DimenUtils.dp2px(context,5),
                DimenUtils.dp2px(context,5),DimenUtils.dp2px(context,5));
        tvRightInfo.setLayoutParams(rightImageParams);

        this.requestLayout();
        this.setOrientation(HORIZONTAL);
        this.setPadding(DimenUtils.dp2px(context,5.0f),DimenUtils.dp2px(context,5.0f),
                DimenUtils.dp2px(context,5.0f),DimenUtils.dp2px(context,5.0f));
        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_VERTICAL;

        addView(leftImage,leftImageParams);
        addView(tvLeftInfo,leftTextParams);
        addView(tvRightInfo,rightTextParams);
        addView(rightImage,rightImageParams);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setRightInfo(String s) {
        tvRightInfo.setText(s);
    }
}
