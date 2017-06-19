package com.zyrc.exhibit.view.homeTeamHeader;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zyrc.exhibit.R;
import com.zyrc.exhibit.adapter.filter.FilterOneAdapter;
import com.zyrc.exhibit.adapter.filter.FilterRightAdapter;
import com.zyrc.exhibit.model.filter.FilterEntity;
import com.zyrc.exhibit.model.filter.FilterTwoEntity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by sunfusheng on 17/3/8.
 */
public class HomeTeamFilterView extends LinearLayout implements View.OnClickListener {
    @ViewInject(R.id.tv_home_fragment_team_type_title)
    private TextView tvTypeTitle;//类型
    @ViewInject(R.id.iv_home_fragment_team_type_arrow)
    private ImageView ivTypeArrow;

    @ViewInject(R.id.tv_home_fragment_team_place_title)
    private TextView tvPlaceTitle;//产地
    @ViewInject(R.id.iv_home_fragment_team_place_arrow)
    private ImageView ivPlaceArrow;

    @ViewInject(R.id.tv_home_fragment_team_sift_title)
    private TextView tvSiftTitle;//筛选
    @ViewInject(R.id.iv_home_fragment_team_sift_arrow)
    private ImageView ivSiftArrow;

    @ViewInject(R.id.ll_home_fragment_team_type)
    private LinearLayout llType;//分类
    @ViewInject(R.id.ll_home_fragment_team_place)
    private LinearLayout llPlace;//产地
    @ViewInject(R.id.ll_home_fragment_team_sift)
    private LinearLayout llSift;//筛选

    @ViewInject(R.id.lv_left)
    private ListView lvLeft;
    @ViewInject(R.id.lv_right)
    private ListView lvRight;
    @ViewInject(R.id.ll_head_layout)
    private LinearLayout llHeadLayout;
    @ViewInject(R.id.ll_content_list_view)
    private LinearLayout llContentListView;
    @ViewInject(R.id.view_mask_bg)
    private View viewMaskBg;

    private Context mContext;
    private Activity mActivity;

    private int filterPosition = -1;
    private int lastFilterPosition = -1;
    public static final int POSITION_CATEGORY = 0; // 分类的位置
    public static final int POSITION_SORT = 1; // 地方的位置
    public static final int POSITION_FILTER = 2; // 时间的位置

    private boolean isShowing = false;
    private int panelHeight;
    private TeamFilterData filterData;


    private FilterRightAdapter typeAdapter;
    private FilterOneAdapter placeAdapter;
    private FilterOneAdapter siftAdapter;

    private FilterTwoEntity leftSelectedCategoryEntity; // 被选择的分类项左侧数据
    private FilterEntity rightSelectedCategoryEntity; // 被选择的分类项右侧数据
    private FilterEntity selectedSortEntity; // 被选择的排序项
    private FilterEntity selectedTypeEntity; // 被选择的筛选项
    private FilterEntity selectedPlaceEntity; // 被选择的筛选项
    private FilterEntity selectedSiftEntity; // 被选择的筛选项

//    private HeaderFilterView headerFilterView = new HeaderFilterView();

    public HomeTeamFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeTeamFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.home_fragment_team_filter, this);
        x.view().inject(this, view);

        initView();
        initListener();
    }

    private void initView() {
        viewMaskBg.setVisibility(GONE);
        llContentListView.setVisibility(GONE);
    }

    private void initListener() {
        llType.setOnClickListener(this);
        llPlace.setOnClickListener(this);
        llSift.setOnClickListener(this);
        viewMaskBg.setOnClickListener(this);
        llContentListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home_fragment_team_type:
                filterPosition = 0;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.ll_home_fragment_team_place:
                filterPosition = 1;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.ll_home_fragment_team_sift:
                filterPosition = 2;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.view_mask_bg:
                hide();
                break;
        }
    }

    // 复位筛选的显示状态
    public void resetFilterStatus() {
//        tvCityTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivTypeArrow.setImageResource(R.mipmap.home_down_arrow);

//        tvNearTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivPlaceArrow.setImageResource(R.mipmap.home_down_arrow);

//        tvTypeTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivSiftArrow.setImageResource(R.mipmap.home_down_arrow);
    }

    // 复位所有的状态
    public void resetAllStatus() {
        resetFilterStatus();
        hide();
    }

    // 设置类型数据
    private void setCityAdapter() {
        lvRight.setVisibility(VISIBLE);

        typeAdapter = new FilterRightAdapter(mContext, filterData.getCategory());
        lvRight.setAdapter(typeAdapter);
        typeAdapter.setSelectedEntity(selectedTypeEntity);
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTypeEntity = filterData.getCategory().get(position);
                String type = filterData.getCategory().get(position).getKey();
                if (onItemTypeClickListener != null) {
                    onItemTypeClickListener.onItemTypeClick(position);
                }
                if(TextUtils.equals(filterData.getCategory().get(position).getKey(),"不限")){
                    tvTypeTitle.setText("类型");
                } else {
                    tvTypeTitle.setText(filterData.getCategory().get(position).getKey());
                }
                HeaderHomeTeamFilterView.setTitle(0, filterData.getCategory().get(position).getKey());
                hide();
            }
        });
    }

    // 设置产地数据
    private void setNearAdapter() {
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);

        placeAdapter = new FilterOneAdapter(mContext, filterData.getPlace());
        lvRight.setAdapter(placeAdapter);
        placeAdapter.setSelectedEntity(selectedPlaceEntity);
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPlaceEntity = filterData.getPlace().get(position);
                String place = filterData.getPlace().get(position).getKey();
                if (onItemPlaceClickListener != null) {
                    onItemPlaceClickListener.onItemPlaceClick(place);
                }
                if(TextUtils.equals(filterData.getPlace().get(position).getKey(),"不限")){
                    tvPlaceTitle.setText("产地");
                } else {
                    tvPlaceTitle.setText(filterData.getPlace().get(position).getKey());
                }
                HeaderHomeTeamFilterView.setTitle(1, filterData.getPlace().get(position).getKey());
                hide();
            }
        });
    }

    // 设置筛选数据
    private void setTypeAdapter() {
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);

        siftAdapter = new FilterOneAdapter(mContext, filterData.getType());
        lvRight.setAdapter(siftAdapter);
        siftAdapter.setSelectedEntity(selectedSiftEntity);
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedSiftEntity = filterData.getType().get(position);
                int price = 0;
                if (onItemSiftClickListener != null) {
                    switch (position) {
                        case 0:
                            price = -1;
                            break;
                        case 1:
                            price = 200;
                            break;
                        case 2:
                            price = 500;
                            break;
                        case 3:
                            price = 1000;
                            break;
                        case 4:
                            price = 5000;
                            break;
                        case 5:
                            price = 5001;
                            break;
                    }
                    onItemSiftClickListener.onItemSiftClick(price);
                }
                if(TextUtils.equals(filterData.getType().get(position).getKey(),"不限")){
                    tvSiftTitle.setText("产地");
                } else {
                    tvSiftTitle.setText(filterData.getType().get(position).getKey());
                }
                HeaderHomeTeamFilterView.setTitle(2, filterData.getType().get(position).getKey());
                hide();
            }
        });
    }


    // 动画显示
    public void show(int position) {
        if (isShowing && lastFilterPosition == position) return;
        resetFilterStatus();
        rotateArrowUp(position);
        rotateArrowDown(lastFilterPosition);
        lastFilterPosition = position;

        switch (position) {
            case POSITION_CATEGORY:
//                tvCityTitle.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                ivTypeArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setCityAdapter();
                break;
            case POSITION_SORT:
//                tvNearTitle.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                ivPlaceArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setNearAdapter();
                break;
            case POSITION_FILTER:
//                tvTypeTitle.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
                ivSiftArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setTypeAdapter();
                break;
        }

        if (isShowing) return;
        isShowing = true;
        viewMaskBg.setVisibility(VISIBLE);
        llContentListView.setVisibility(VISIBLE);
        llContentListView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llContentListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                panelHeight = llContentListView.getHeight();
                ObjectAnimator.ofFloat(llContentListView, "translationY", -panelHeight, 0).setDuration(200).start();
            }
        });
    }

    // 隐藏动画
    public void hide() {
        isShowing = false;
        resetFilterStatus();
        rotateArrowDown(filterPosition);
        rotateArrowDown(lastFilterPosition);
        filterPosition = -1;
        lastFilterPosition = -1;
        viewMaskBg.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(llContentListView, "translationY", 0, -panelHeight).setDuration(200).start();
    }

    // 旋转箭头向上
    private void rotateArrowUp(int position) {
        switch (position) {
            case POSITION_CATEGORY:
                rotateArrowUpAnimation(ivTypeArrow);
                break;
            case POSITION_SORT:
                rotateArrowUpAnimation(ivPlaceArrow);
                break;
            case POSITION_FILTER:
                rotateArrowUpAnimation(ivSiftArrow);
                break;
        }
    }

    // 旋转箭头向下
    private void rotateArrowDown(int position) {
        switch (position) {
            case POSITION_CATEGORY:
                rotateArrowDownAnimation(ivTypeArrow);
                break;
            case POSITION_SORT:
                rotateArrowDownAnimation(ivPlaceArrow);
                break;
            case POSITION_FILTER:
                rotateArrowDownAnimation(ivSiftArrow);
                break;
        }
    }

    // 旋转箭头向上
    public static void rotateArrowUpAnimation(final ImageView iv) {
        if (iv == null) return;
        RotateAnimation animation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        iv.startAnimation(animation);
    }

    // 旋转箭头向下
    public static void rotateArrowDownAnimation(final ImageView iv) {
        if (iv == null) return;
        RotateAnimation animation = new RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });
        iv.startAnimation(animation);
    }

    // 设置筛选数据
    public void setFilterData(Activity activity, TeamFilterData filterData) {
        this.mActivity = activity;
        this.filterData = filterData;
    }

    // 是否显示
    public boolean isShowing() {
        return isShowing;
    }

    public int getFilterPosition() {
        return filterPosition;
    }

    // 筛选视图点击
    private OnFilterClickListener onFilterClickListener;

    public void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        this.onFilterClickListener = onFilterClickListener;
    }

    public interface OnFilterClickListener {
        void onFilterClick(int position);
    }

    // 类型Item点击
    private OnItemTypeClickListener onItemTypeClickListener;

    public void setOnItemCategoryClickListener(OnItemTypeClickListener onItemTypeClickListener) {
        this.onItemTypeClickListener = onItemTypeClickListener;
    }

    public interface OnItemTypeClickListener {
        void onItemTypeClick(int type);
    }

    // 产地Item点击
    private OnItemPlaceClickListener onItemPlaceClickListener;

    public void setOnItemPlaceClickListener(OnItemPlaceClickListener onItemPlaceClickListener) {
        this.onItemPlaceClickListener = onItemPlaceClickListener;
    }

    public interface OnItemPlaceClickListener {
        void onItemPlaceClick(String place);
    }

    // 筛选Item点击
    private OnItemSiftClickListener onItemSiftClickListener;

    public void setOnItemSiftClickListener(OnItemSiftClickListener onItemSiftClickListener) {
        this.onItemSiftClickListener = onItemSiftClickListener;
    }

    public interface OnItemSiftClickListener {
        void onItemSiftClick(int price);
    }

}
