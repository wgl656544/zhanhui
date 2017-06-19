package com.zyrc.exhibit.view.homeTicketHeader;

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

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by sunfusheng on 16/4/20.
 */
public class HomeTicketFilterView extends LinearLayout implements View.OnClickListener {
    @ViewInject(R.id.tv_city_title)
    private TextView tvCityTitle;//城市
    @ViewInject(R.id.iv_city_arrow)
    private ImageView ivCityArrow;
    @ViewInject(R.id.tv_near_title)
    private TextView tvNearTitle;//最近
    @ViewInject(R.id.iv_near_arrow)
    private ImageView ivNearArrow;
    @ViewInject(R.id.tv_type_title)
    private TextView tvTypeTitle;//类型
    @ViewInject(R.id.iv_type_arrow)
    private ImageView ivTypeArrow;
    @ViewInject(R.id.tv_sift_title)
    private TextView tvSiftTitle;//筛选
    @ViewInject(R.id.iv_sift_arrow)
    private ImageView ivSiftArrow;
    @ViewInject(R.id.ll_city)
    private LinearLayout llCity;
    @ViewInject(R.id.ll_near)
    private LinearLayout llNear;
    @ViewInject(R.id.ll_type)
    private LinearLayout llType;
    @ViewInject(R.id.lv_left)
    private ListView lvLeft;
    @ViewInject(R.id.lv_right)
    private ListView lvRight;
    @ViewInject(R.id.ll_head_layout)
    private LinearLayout llHeadLayout;
    @ViewInject(R.id.ll_content_list_view)
    private LinearLayout llContentListView;
    @ViewInject(R.id.ll_sift)
    private LinearLayout llSift;
    @ViewInject(R.id.view_mask_bg)
    private View viewMaskBg;

    private Context mContext;
    private Activity mActivity;

    private int filterPosition = -1;
    private int lastFilterPosition = -1;
    public static final int POSITION_CATEGORY = 0; // 分类的位置
    public static final int POSITION_SORT = 1; // 排序的位置
    public static final int POSITION_FILTER = 2; // 筛选的位置
    public static final int POSITION_OTHER = 3; // 其他的位置

    private boolean isShowing = false;
    private int panelHeight;
    private TicketFilterData filterData;

    private FilterRightAdapter cityAdapter;
    private FilterOneAdapter nearAdapter;
    private FilterOneAdapter typeAdapter;
    private FilterOneAdapter siftAdapter;

    private FilterEntity SelectedCityEntity; // 被选择的分类项右侧数据
    private FilterEntity selectedNearEntity; // 被选择的排序项
    private FilterEntity selectedTypeEntity; // 被选择的筛选项
    private FilterEntity selectedSiftEntity; // 被选择的筛选项



    public HomeTicketFilterView(Context context) {
        super(context);
    }

    public HomeTicketFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeTicketFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_filter_layout, this);
        x.view().inject(this, view);

        initView();
        initListener();
    }

    private void initView() {
        viewMaskBg.setVisibility(GONE);
        llContentListView.setVisibility(GONE);
    }

    private void initListener() {
        llCity.setOnClickListener(this);
        llNear.setOnClickListener(this);
        llType.setOnClickListener(this);
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
            case R.id.ll_city:
                filterPosition = 0;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.ll_near:
                filterPosition = 1;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.ll_type:
                filterPosition = 2;
                if (onFilterClickListener != null) {
                    onFilterClickListener.onFilterClick(filterPosition);
                }
                break;
            case R.id.ll_sift:
                filterPosition = 3;
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
        ivCityArrow.setImageResource(R.mipmap.home_down_arrow);

        ivNearArrow.setImageResource(R.mipmap.home_down_arrow);

        ivTypeArrow.setImageResource(R.mipmap.home_down_arrow);

        ivSiftArrow.setImageResource(R.mipmap.home_down_arrow);
    }

    // 复位所有的状态
    public void resetAllStatus() {
        resetFilterStatus();
        hide();
    }

    // 设置城市数据
    private void setCityAdapter() {
        lvRight.setVisibility(VISIBLE);
        cityAdapter = new FilterRightAdapter(mContext, filterData.getCity());
        lvRight.setAdapter(cityAdapter);
        cityAdapter.setSelectedEntity(SelectedCityEntity);
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectedCityEntity = filterData.getCity().get(position);
                String city = filterData.getCity().get(position).getKey();
                if (onItemCityClickListener != null) {
                    onItemCityClickListener.onItemCityClick(city);
                }
                if (TextUtils.equals(filterData.getCity().get(position).getKey(), "不限")) {
                    tvCityTitle.setText("城市");
                } else {
                    tvCityTitle.setText(filterData.getCity().get(position).getKey());
                }
                //修改假视图文本
                HeaderHomeTicketFilterView.setTitle(0, filterData.getCity().get(position).getKey());
                hide();
            }
        });
    }

    // 设置最近数据
    private void setNearAdapter() {
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);

        nearAdapter = new FilterOneAdapter(mContext, filterData.getNear());
        lvRight.setAdapter(nearAdapter);
        nearAdapter.setSelectedEntity(selectedNearEntity);
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedNearEntity = filterData.getNear().get(position);
                int near = 0;
                if (onItemNearClickListener != null) {
                    switch (position) {
                        case 0:
                            near = -1;
                            break;
                        case 1:
                            near = 1;
                            break;
                        case 2:
                            near = 3;
                            break;
                        case 3:
                            near = 6;
                            break;
                        case 4:
                            near = 12;
                            break;
                    }
                    onItemNearClickListener.onItemNearClick(near);
                }
                if (TextUtils.equals(filterData.getNear().get(position).getKey(), "不限")) {
                    tvNearTitle.setText("最近");
                } else {
                    tvNearTitle.setText(filterData.getNear().get(position).getKey());
                }
                //修改假视图文本
                HeaderHomeTicketFilterView.setTitle(1, filterData.getNear().get(position).getKey());
                hide();
            }
        });
    }

    // 设置类型数据
    private void setTypeAdapter() {
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);

        typeAdapter = new FilterOneAdapter(mContext, filterData.getType());
        lvRight.setAdapter(typeAdapter);
        typeAdapter.setSelectedEntity(selectedTypeEntity);
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTypeEntity = filterData.getType().get(position);
                String type = filterData.getType().get(position).getKey();
                if (onItemTypeClickListener != null) {
                    onItemTypeClickListener.onItemTypeClick(position);
                }
                if (TextUtils.equals(filterData.getType().get(position).getKey(), "不限")) {
                    tvTypeTitle.setText("类型");
                } else {
                    tvTypeTitle.setText(filterData.getType().get(position).getKey());
                }
                //修改假视图文本
                HeaderHomeTicketFilterView.setTitle(2, filterData.getType().get(position).getKey());
                hide();
            }
        });
    }

    // 设置筛选数据
    private void setSiftAdapter() {
        lvLeft.setVisibility(GONE);
        lvRight.setVisibility(VISIBLE);

        siftAdapter = new FilterOneAdapter(mContext, filterData.getSift());
        lvRight.setAdapter(siftAdapter);
        siftAdapter.setSelectedEntity(selectedSiftEntity);
        lvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedSiftEntity = filterData.getSift().get(position);
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
                if (TextUtils.equals(filterData.getSift().get(position).getKey(), "不限")) {
                    tvSiftTitle.setText("筛选");
                } else {
                    tvSiftTitle.setText(filterData.getSift().get(position).getKey());
                }
                //修改假视图文本
                HeaderHomeTicketFilterView.setTitle(3, filterData.getSift().get(position).getKey());
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
                ivCityArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setCityAdapter();
                break;
            case POSITION_SORT:
                ivNearArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setNearAdapter();
                break;
            case POSITION_FILTER:
                ivTypeArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setTypeAdapter();
                break;
            case POSITION_OTHER:
                ivSiftArrow.setImageResource(R.mipmap.home_down_arrow_red);
                setSiftAdapter();
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
                rotateArrowUpAnimation(ivCityArrow);
                break;
            case POSITION_SORT:
                rotateArrowUpAnimation(ivNearArrow);
                break;
            case POSITION_FILTER:
                rotateArrowUpAnimation(ivTypeArrow);
                break;
            case POSITION_OTHER:
                rotateArrowUpAnimation(ivSiftArrow);
                break;
        }
    }

    // 旋转箭头向下
    private void rotateArrowDown(int position) {
        switch (position) {
            case POSITION_CATEGORY:
                rotateArrowDownAnimation(ivCityArrow);
                break;
            case POSITION_SORT:
                rotateArrowDownAnimation(ivNearArrow);
                break;
            case POSITION_FILTER:
                rotateArrowDownAnimation(ivTypeArrow);
                break;
            case POSITION_OTHER:
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
    public void setFilterData(Activity activity, TicketFilterData filterData) {
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

    // 城市Item点击
    private OnItemCityClickListener onItemCityClickListener;

    public void setOnItemCityClickListener(OnItemCityClickListener onItemCityClickListener) {
        this.onItemCityClickListener = onItemCityClickListener;
    }

    public interface OnItemCityClickListener {
        void onItemCityClick(String city);
    }

    // 最近Item点击
    private OnItemNearClickListener onItemNearClickListener;

    public void setOnItemNearClickListener(OnItemNearClickListener onItemNearClickListener) {
        this.onItemNearClickListener = onItemNearClickListener;
    }

    public interface OnItemNearClickListener {
        void onItemNearClick(int near);
    }

    // 类型Item点击
    private OnItemTypeClickListener onItemTypeClickListener;

    public void setOnItemTypeClickListener(OnItemTypeClickListener onItemTypeClickListener) {
        this.onItemTypeClickListener = onItemTypeClickListener;
    }

    public interface OnItemTypeClickListener {
        void onItemTypeClick(int type);
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
