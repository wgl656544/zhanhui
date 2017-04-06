package com.example.mylibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mylibrary.R;
import com.example.mylibrary.utils.DimenUtils;

import static android.widget.RelativeLayout.TRUE;

/**
 * Created by Administrator on 2016/11/29.
 */

public class Switcher extends LinearLayout{
    
    private RelativeLayout leftLayout,rightLayout;

    private TextView leftTitle,rightTitle;

    private Drawable leftLayoutColor;
    private Drawable rightLayotuColor;

    private ImageView circlePoint;

    private float leftTextSize;
    private String leftText;
    private int leftTextColor;

    private float rightTextSize;
    private String rightText;
    private int rightTextColor;
    
    private RelativeLayout.LayoutParams leftParams,rightParams;
    private RelativeLayout.LayoutParams circlePointParams;
    private LayoutParams leftLayoutParams;
    private LayoutParams rightLayoutParams;

    private Context mContext;

    private OnSwitcherClickedListener mOnSwitcherClickedListener;
    public void setOnSwitcherClickedListener(OnSwitcherClickedListener listener){
        this.mOnSwitcherClickedListener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Switcher(Context context) {
        this(context,null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Switcher(final Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Switcher);

        leftText = ta.getString(R.styleable.Switcher_leftText);
        leftTextColor = ta.getColor(R.styleable.Switcher_leftTextColor,0);
        leftTextSize = ta.getDimension(R.styleable.Switcher_leftTextSize,0);

        rightText = ta.getString(R.styleable.Switcher_rightText);
        rightTextColor = ta.getColor(R.styleable.Switcher_rightTextColor,0);
        rightTextSize = ta.getDimension(R.styleable.Switcher_rightTextSize,0);

        leftLayoutColor = ta.getDrawable(R.styleable.Switcher_leftLayoutColor);
        rightLayotuColor = ta.getDrawable(R.styleable.Switcher_rightLayoutColor);

        ta.recycle();

        this.requestLayout();
        this.setOrientation(HORIZONTAL);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        this.setLayoutParams(lp);



        leftTitle = new TextView(context);
        rightTitle = new TextView(context);
        circlePoint = new ImageView(context);

        leftTitle.setText(leftText);
        leftTitle.setTextColor(leftTextColor);
        leftTitle.setTextSize(leftTextSize);

        rightTitle.setText(rightText);
        rightTitle.setTextColor(rightTextColor);
        rightTitle.setTextSize(rightTextSize);

        leftLayout = new RelativeLayout(context);
        leftParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        leftLayout.addView(leftTitle,leftParams);

        circlePointParams = new RelativeLayout.LayoutParams(DimenUtils.dp2px(context,8),DimenUtils.dp2px(context,8));
        circlePointParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        circlePointParams.rightMargin = DimenUtils.dp2px(context,5);
        circlePointParams.topMargin   = DimenUtils.dp2px(context,5);
        circlePoint.setBackground(ContextCompat.getDrawable(context,R.drawable.circle_point_background));
        leftLayout.addView(circlePoint,circlePointParams);

        rightLayout = new RelativeLayout(context);
        rightParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        rightLayout.addView(rightTitle,rightParams);


        leftLayout.setBackground(leftLayoutColor);
        rightLayout.setBackground(rightLayotuColor);

        leftLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int value = DimenUtils.dp2px(context,1);
        leftLayoutParams.setMargins(value,value,0,value);
        leftLayoutParams.weight = 1;


        rightLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rightLayoutParams.setMargins(0,value,value,value);
        rightLayoutParams.weight = 1;

        addView(leftLayout,leftLayoutParams);
        addView(rightLayout,rightLayoutParams);
        setBackground(ContextCompat.getDrawable(context,R.drawable.switcher_background));

        leftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSwitcherClickedListener.leftButtonClicked();
                rightLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.switcher_right_selector));
                leftLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.left_swither_hoverl));
                leftTitle.setTextColor(ContextCompat.getColor(context,R.color.white));
                rightTitle.setTextColor(ContextCompat.getColor(context,R.color.black));
            }
        });

        rightLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSwitcherClickedListener.rightButtonClicked();
                rightLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.right_swither_hover));
                leftLayout.setBackground(ContextCompat.getDrawable(context,R.drawable.switcher_left_selector));
                leftTitle.setTextColor(ContextCompat.getColor(context,R.color.black));
                rightTitle.setTextColor(ContextCompat.getColor(context,R.color.white));
            }
        });
        circlePoint.setVisibility(INVISIBLE);
    }
    public void setTipsVisible(boolean flag){
        circlePoint.setVisibility(flag ? VISIBLE : INVISIBLE);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void chooseLeft(){
        rightLayout.setBackground(ContextCompat.getDrawable(mContext,R.drawable.switcher_right_selector));
        leftLayout.setBackground(ContextCompat.getDrawable(mContext,R.drawable.left_swither_hoverl));
        leftTitle.setTextColor(ContextCompat.getColor(mContext,R.color.white));
        rightTitle.setTextColor(ContextCompat.getColor(mContext,R.color.black));
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void chooseRight(){
        rightLayout.setBackground(ContextCompat.getDrawable(mContext,R.drawable.right_swither_hover));
        leftLayout.setBackground(ContextCompat.getDrawable(mContext,R.drawable.switcher_left_selector));
        leftTitle.setTextColor(ContextCompat.getColor(mContext,R.color.black));
        rightTitle.setTextColor(ContextCompat.getColor(mContext,R.color.white));
    }
    public interface OnSwitcherClickedListener{
        void leftButtonClicked();
        void rightButtonClicked();
    }
}
