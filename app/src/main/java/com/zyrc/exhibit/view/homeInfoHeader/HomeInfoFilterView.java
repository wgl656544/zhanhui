package com.zyrc.exhibit.view.homeInfoHeader;

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
public class HomeInfoFilterView extends LinearLayout implements View.OnClickListener {
    @ViewInject(R.id.tv_home_fragment_category_title)
    private TextView tvCategoryTitle;//分类
    @ViewInject(R.id.iv_home_fragment_category_arrow)
    private ImageView ivCategoryArrow;

    @ViewInject(R.id.tv_home_fragment_info_place_title)
    private TextView tvPlaceTitle;//地方
    @ViewInject(R.id.iv_home_fragment_info_place_arrow)
    private ImageView ivPlaceArrow;

    @ViewInject(R.id.tv_home_fragment_info_date_title)
    private TextView tvDateTitle;//时间
    @ViewInject(R.id.iv_home_fragment_info_date_arrow)
    private ImageView ivDateArrow;

    @ViewInject(R.id.ll_home_fragment_info_category)
    private LinearLayout llCategory;//分类
    @ViewInject(R.id.ll_home_fragment_info_place)
    private LinearLayout llPlace;//地方
    @ViewInject(R.id.ll_home_fragment_info_date)
    private LinearLayout llDate;//时间

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
    private InfoFilterData filterData;

    private FilterRightAdapter categoryAdapter;
    private FilterOneAdapter placeAdapter;
    private FilterOneAdapter dateAdapter;


    private FilterTwoEntity leftSelectedCategoryEntity; // 被选择的分类项左侧数据
    private FilterEntity rightSelectedCategoryEntity; // 被选择的分类项右侧数据
    private FilterEntity selectedSortEntity; // 被选择的排序项
    private FilterEntity selectedFilterEntity; // 被选择的筛选项

    private FilterEntity selectedCategoryEntity; // 分类被选择的筛选项
    private FilterEntity selectedPlaceEntity; // 地方被选择的筛选项
    private FilterEntity selectedDateEntity; // 时间被选择的筛选项

//    private HeaderFilterView headerFilterView = new HeaderFilterView();

    public HomeInfoFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeInfoFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.home_fragment_info_filter, this);
        x.view().inject(this, view);

        initView();
        initListener();
    }

    private void initView() {
        viewMaskBg.setVisibility(GONE);
        llContentListView.setVisibility(GONE);
    }

    private void initListener() {
        llCategory.setOnClickListener(this);
        llPlace.setOnClickListener(this);
        llDate.setOnClickListener(this);
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
            case R.id.ll_home_fragment_info_category:
                filterPosition = 0;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.ll_home_fragment_info_place:
                filterPosition = 1;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.ll_home_fragment_info_date:
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
        ivCategoryArrow.setImageResource(R.mipmap.home_down_arrow);

//        tvNearTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivPlaceArrow.setImageResource(R.mipmap.home_down_arrow);

//        tvTypeTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
        ivDateArrow.setImageResource(R.mipmap.home_down_arrow);

//        tvSiftTitle.setTextColor(mContext.getResources().getColor(R.color.font_black_2));
//        ivSiftArrow.setImageResource(R.mipmap.home_down_arrow);
    }

    // 复位所有的状态
    public void resetAllStatus() {
        resetFilterStatus();
        hide();
    }

    // 设置分类数据
    private void setCityAdapter() {
        lvRight.setVisibility(VISIBLE);

        categoryAdapter = new FilterRightAdapter(mContext, filterData.getCategory());
        lvRight.setAdapter(categoryAdapter);
        categoryAdapter.setSelectedEntity(selectedCategoryEntity);
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCategoryEntity = filterData.getCategory().get(position);
                String type = filterData.getCategory().get(position).getKey();
                if (onItemCategoryClickListener != null) {
                    onItemCategoryClickListener.onItemCategoryClick(position);
                }
                if (TextUtils.equals(filterData.getCategory().get(position).getKey(), "不限")) {
                    tvCategoryTitle.setText("分类");
                } else {
                    tvCategoryTitle.setText(filterData.getCategory().get(position).getKey());
                }
                //修改假视图文本
                HeaderHomeInfoFilterView.setTitle(0, filterData.getCategory().get(position).getKey());
                hide();
            }
        });
    }

    // 设置地方数据
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
                String city = filterData.getPlace().get(position).getKey();
                if (onItemPlaceClickListener != null) {
                    onItemPlaceClickListener.onItemPlaceClick(city);
                }
                if (TextUtils.equals(filterData.getPlace().get(position).getKey(), "不限")) {
                    tvPlaceTitle.setText("地方");
                } else {
                    tvPlaceTitle.setText(filterData.getPlace().get(position).getKey());
                }
                //修改假视图文本
                HeaderHomeInfoFilterView.setTitle(1, filterData.getPlace().get(position).getKey());
                hide();
            }
        });
    }

    // 设置时间数据
    private void setTypeAdapter() {
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);

        dateAdapter = new FilterOneAdapter(mContext, filterData.getDate());
        lvRight.setAdapter(dateAdapter);
        dateAdapter.setSelectedEntity(selectedDateEntity);
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedDateEntity = filterData.getDate().get(position);
                int date = 0;
                if (onItemDateClickListener != null) {
                    switch (position) {
                        case 0:
                            date = -1;
                            break;
                        case 1:
                            date = 1;
                            break;
                        case 2:
                            date = 3;
                            break;
                        case 3:
                            date = 6;
                            break;
                        case 4:
                            date = 12;
                            break;
                    }
                    onItemDateClickListener.onItemDateClick(date);
                }
                if (TextUtils.equals(filterData.getDate().get(position).getKey(), "不限")) {
                    tvDateTitle.setText("时间");
                } else {
                    tvDateTitle.setText(filterData.getDate().get(position).getKey());
                }
                //修改假视图文本
                HeaderHomeInfoFilterView.setTitle(2, filterData.getDate().get(position).getKey());
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
                ivCategoryArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setCityAdapter();
                break;
            case POSITION_SORT:
                ivPlaceArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setNearAdapter();
                break;
            case POSITION_FILTER:
                ivDateArrow.setImageResource(R.mipmap.home_down_arrow_red);
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
                rotateArrowUpAnimation(ivCategoryArrow);
                break;
            case POSITION_SORT:
                rotateArrowUpAnimation(ivPlaceArrow);
                break;
            case POSITION_FILTER:
                rotateArrowUpAnimation(ivDateArrow);
                break;
        }
    }

    // 旋转箭头向下
    private void rotateArrowDown(int position) {
        switch (position) {
            case POSITION_CATEGORY:
                rotateArrowDownAnimation(ivCategoryArrow);
                break;
            case POSITION_SORT:
                rotateArrowDownAnimation(ivPlaceArrow);
                break;
            case POSITION_FILTER:
                rotateArrowDownAnimation(ivDateArrow);
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
    public void setFilterData(Activity activity, InfoFilterData filterData) {
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

    // 分类Item点击
    private OnItemCategoryClickListener onItemCategoryClickListener;

    public void setOnItemCategoryClickListener(OnItemCategoryClickListener onItemCategoryClickListener) {
        this.onItemCategoryClickListener = onItemCategoryClickListener;
    }

    public interface OnItemCategoryClickListener {
        void onItemCategoryClick(int type);
    }

    // 地方Item点击
    private OnItemPlaceClickListener onItemPlaceClickListener;

    public void setOnItemPlaceClickListener(OnItemPlaceClickListener onItemPlaceClickListener) {
        this.onItemPlaceClickListener = onItemPlaceClickListener;
    }

    public interface OnItemPlaceClickListener {
        void onItemPlaceClick(String city);
    }

    // 时间Item点击
    private OnItemDateClickListener onItemDateClickListener;

    public void setOnItemDateClickListener(OnItemDateClickListener onItemDateClickListener) {
        this.onItemDateClickListener = onItemDateClickListener;
    }

    public interface OnItemDateClickListener {
        void onItemDateClick(int date);
    }

}
