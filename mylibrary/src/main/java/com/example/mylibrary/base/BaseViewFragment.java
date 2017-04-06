package com.example.mylibrary.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.mylibrary.R;
import com.example.mylibrary.common.CustomLoading;
import com.example.mylibrary.utils.CustomWaitDialog;


/**
 * Fragment基类
 * 1. 初始化布局 initView
 * 2. 初始化数据 initData
 * 3. 懒加载Fragment isInit字段
 * @author Ace
 * @date 2016-2-11
 */
public abstract class BaseViewFragment extends Fragment {

    //这个activity就是MainActivity
    public Activity mActivity;
    private CustomLoading mCustomLoading;

    private FrameLayout contentLayout;
    private FrameLayout loadingLayout;
    private FrameLayout noDataLayout;
    private boolean isInit;
    private CustomWaitDialog customWaitDialog;
    /**
     * 获得全局的，防止使用getActivity()为空
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint() && isInit){
            initData();
        }else{
            isInit = false;
        }
    }

    // Fragment被创建
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();// 获取所在的activity对象
    }

    // 初始化Fragment布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(mActivity)
                .inflate(R.layout.base_fragment_view_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        contentLayout = (FrameLayout) view.findViewById(R.id.frame_content);
        loadingLayout = (FrameLayout) view.findViewById(R.id.frame_loading);
        noDataLayout = (FrameLayout) view.findViewById(R.id.frame_no_data);


        View content_view  = mActivity.getLayoutInflater().inflate(getLayoutId(),null);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        content_view.setLayoutParams(lp);
        initView(content_view,savedInstanceState);
        contentLayout.addView(content_view);
        isInit = true;
    }
    /**
     * 添加loadingView
     */
    public void setLoadingView(@LayoutRes int loadingViewResId){
        View view = mActivity.getLayoutInflater().inflate(loadingViewResId,loadingLayout,true);
    }
    public void setLoadingView(View view){
        this.loadingLayout.addView(view);
    }
    /**
     * 添加errorView
     */
    public void setErrorView(@LayoutRes int errorViewResId){
        noDataLayout.removeAllViews();
        View view = mActivity.getLayoutInflater().inflate(errorViewResId,noDataLayout,true);
        noDataLayout.addView(view);
    }
    public void setErrorView(View view){
        this.noDataLayout.removeAllViews();
        this.noDataLayout.addView(view);
    }

    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract int getLayoutId();



    // activity创建结束
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        initData();
    }


    /**
     * 初始化数据, 子类可以不实现
     */
    public abstract void initData();

    /**
     * 进度加载动画
     */
    public void startLoading(){
        if(customWaitDialog == null){
            customWaitDialog = new CustomWaitDialog(getContext());
        }
        customWaitDialog.show();
    }
    public void setShowType(requestType type){

        contentLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.GONE);
        switch (type){
            case Loading:
                loadingLayout.setVisibility(View.VISIBLE);
                break;
            case noData:
                noDataLayout.setVisibility(View.VISIBLE);
                break;
            case showContent:
                contentLayout.setVisibility(View.VISIBLE);
                break;
        }
    }
    public enum requestType{
        Loading,noData,showContent
    }
    /**
     * 进度加载动画
     */
    public void startLoading(String msg){
        if(customWaitDialog == null){
            customWaitDialog = new CustomWaitDialog(getContext());
        }
        customWaitDialog.show(msg);
    }
    /**
     * 关闭加载动画
     */
    public void stopLoading(){
        if(customWaitDialog != null){
            customWaitDialog.dismiss();
            customWaitDialog = null;
        }
    }
    /**
     * [防止快速点击]
     *
     * @return
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }
    
}
