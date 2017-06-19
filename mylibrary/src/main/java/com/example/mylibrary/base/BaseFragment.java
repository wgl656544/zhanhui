package com.example.mylibrary.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.mylibrary.common.CustomLoading;

/**
 * Fragment基类
 * 1. 初始化布局 initView
 * 2. 初始化数据 initData
 *
 * @author Ace
 * @date 2016-2-11
 */
public abstract class BaseFragment extends Fragment {

    //这个activity就是MainActivity
    public Activity mActivity;
    private CustomLoading mCustomLoading;


    /**
     * 获得全局的，防止使用getActivity()为空
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }
//    // Fragment被创建
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mActivity = getActivity();// 获取所在的activity对象
//    }

    // 初始化Fragment布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(mActivity)
                .inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);
        return view;
    }

    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract int getLayoutId();



    // activity创建结束
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }


    /**
     * 初始化数据, 子类可以不实现
     */
    public abstract void initData();

    /**
     * 进度加载动画
     */
    public void startLoading(){
        if(mCustomLoading == null){
            mCustomLoading = CustomLoading.CreateLoadingDialog(mActivity);
            mCustomLoading.setCanceledOnTouchOutside(false);//不允许点击取消

//            Window wd = mCustomLoading.getWindow();
//            WindowManager.LayoutParams lp = wd.getAttributes();
//            lp.alpha = 0.5f;
//            wd.setAttributes(lp);
        }
        mCustomLoading.show();
    }
    /**
     * 进度加载动画
     */
    public void startLoading(String msg){
        if(mCustomLoading == null){
            mCustomLoading = CustomLoading.CreateLoadingDialog(mActivity);
            mCustomLoading.setMessage(msg);
            mCustomLoading.setCanceledOnTouchOutside(false);//不允许点击取消

            Window wd = mCustomLoading.getWindow();
            WindowManager.LayoutParams lp = wd.getAttributes();
            lp.alpha = 0.8f;
            wd.setAttributes(lp);
        }
        mCustomLoading.show();
    }
    /**
     * 关闭加载动画
     */
    public void stopLoading(){
        if(mCustomLoading != null){
            mCustomLoading.dismiss();
//            mCustomLoading.dismiss();
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
