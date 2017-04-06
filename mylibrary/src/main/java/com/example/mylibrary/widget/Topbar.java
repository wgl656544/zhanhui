package com.example.mylibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
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
 * Created by yuanyukun on 2015/9/7.
 */
public class Topbar extends LinearLayout {

    private TextView leftButton,rightButton;
    private TextView tvTitle;
    private ImageView mImageView;
    private LinearLayout leftLayout;


    private int  leftTextColor;
    private String leftText;

    private int  rightTextColor;
    private String rightText;

    private float titleTextSize;
    private int titleTextColor;
    private String title;

    private TopbarClickListener listenner;

    public  interface TopbarClickListener{
         void leftClick();
         void rightClick();
    }

    public void setOnTopbarClickListener(TopbarClickListener listener){
        this.listenner = listener;
    }
    private LayoutParams leftParams,rightParams,titleParams,ivParams,indivdualLineParams,leftLayoutParams;

    public Topbar(final Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Topbar);

        leftTextColor = ta.getColor(R.styleable.Topbar_leftTextViewColor, 0);
        leftText = ta.getString(R.styleable.Topbar_leftTextStr);

        rightTextColor = ta.getColor(R.styleable.Topbar_rightTextViewColor, 0);
        rightText = ta.getString(R.styleable.Topbar_rightTextStr);

        titleTextSize = ta.getDimension(R.styleable.Topbar_titleTextSize, 0);
        title = ta.getString(R.styleable.Topbar_title);
        titleTextColor = ta.getColor(R.styleable.Topbar_titleTextColor, 0);

        ta.recycle();

        rightButton = new TextView(context);
        leftButton = new TextView(context);
        tvTitle = new TextView(context);
        mImageView = new ImageView(context);

        leftLayout = new LinearLayout(context);


        rightButton.setText(rightText);
        rightButton.setTextColor(rightTextColor);
        rightButton.setTextSize(16);
        rightButton.setGravity(Gravity.CENTER);

        leftButton.setText(leftText);
        leftButton.setTextColor(leftTextColor);
        rightButton.setTextSize(16);
        leftButton.setGravity(Gravity.CENTER);

        tvTitle.setTextColor(titleTextColor);
        tvTitle.setTextSize(titleTextSize);
        tvTitle.setText(title);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setMaxLines(1);
        tvTitle.setEllipsize(TextUtils.TruncateAt.END);
//        tvTitle.setMaxWidth(DimenUtils.dp2px(context.getApplicationContext(),140));

        mImageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.icon_back));
        this.requestLayout();
//        setBackgroundColor(ContextCompat.getColor(context,R.color.white));
        setOrientation(HORIZONTAL);
        setPadding(DimenUtils.dp2px(context,16),0, DimenUtils.dp2px(context,16),0);

        leftLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        leftLayoutParams.gravity = Gravity.CENTER_VERTICAL;
        leftLayout.setOrientation(HORIZONTAL);
        leftLayout.setLayoutParams(leftLayoutParams);
        this.setWeightSum(5);

        ivParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        ivParams.topMargin = 26;
        ivParams.bottomMargin = 26;
        leftLayout.addView(mImageView,ivParams);

        leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        leftParams.leftMargin = DimenUtils.dp2px(context,5);
        leftLayout.addView(leftButton,leftParams);

        LayoutParams left = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        left.weight = 1;
        addView(leftLayout,left);

        titleParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        titleParams.weight = 3;
        addView(tvTitle, titleParams);

        rightParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
        rightParams.weight = 1;
        addView(rightButton, rightParams);


        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER_VERTICAL;
        this.setLayoutParams(lp);

        rightButton.setVisibility(INVISIBLE);
//        leftLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.color_e64));
//        tvTitle.setBackgroundColor(ContextCompat.getColor(context,R.color.color_666));
//        rightButton.setBackgroundColor(ContextCompat.getColor(context,R.color.backGround));



        leftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listenner != null)
                    listenner.leftClick();
            }
        });

        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listenner != null)
                    listenner.rightClick();
            }
        });
    }

    public void HideLeftArrow(){
        mImageView.setVisibility(View.GONE);
    }


    public void setLeftIsVisible(boolean flag){
        if(flag){
            leftButton.setVisibility(View.VISIBLE);
        }else{
            leftButton.setVisibility(View.GONE);
        }
    }


    public void setRightIsVisible(boolean flag){
        if(flag){
            rightButton.setVisibility(View.VISIBLE);
        }else{
            rightButton.setVisibility(View.GONE);
        }
    }
    public void setTvTitle(String title,float textSize,int color){
        tvTitle.setText(title);
        tvTitle.setTextColor(color);
        tvTitle.setTextSize(textSize);
    }
    public void setTvLeftText(String title,float textSize,int color){
        leftButton.setText(title);
        leftButton.setTextColor(color);
        leftButton.setTextSize(textSize);
    }
    public void setTvRightText(String title,float textSize,int color){
        setRightIsVisible(true);
        rightButton.setText(title);
        rightButton.setTextColor(color);
        rightButton.setTextSize(textSize);
    }
}
