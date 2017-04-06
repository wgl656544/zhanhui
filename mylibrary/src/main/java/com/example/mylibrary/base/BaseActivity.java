package com.example.mylibrary.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.example.mylibrary.R;
import com.example.mylibrary.utils.CustomWaitDialog;
import com.example.mylibrary.widget.Topbar;

public class BaseActivity extends AppCompatActivity implements Topbar.TopbarClickListener {
    private FrameLayout containerLayout;
    private FrameLayout loadingView;
    private FrameLayout errorView;

    //private CustomLoading mCustomLoading;
    private Topbar mTopbar;

    private CustomWaitDialog customWaitDialog;

    private OnRightButtonClicked onRightButtonClicked;

    public BaseActivity() {
    }

    public void setOnRightClick(OnRightButtonClicked onRightClick){
        this.onRightButtonClicked = onRightClick;
    }
    public interface OnRightButtonClicked{
        void onRightClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(R.layout.activity_base);

        initView();
        initHeaderLayout();
        initLoadingView();
        
//        //设置为沉浸式
//        StatusBarCompat.transparent(this);
//        StatusUtils.StatusBarLightMode(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    protected void initView() {
        containerLayout = (FrameLayout) findViewById(R.id.fl_base_container );
        loadingView =  (FrameLayout) findViewById(R.id.fl_base_loading);
        errorView = (FrameLayout) findViewById(R.id.fl_base_error );
    }
    public void setTitle(String title){
        mTopbar.setTvTitle(title,16, ContextCompat.getColor(this,R.color.black));
    }
    //设置右边的字体
    public void setRightStr(String rightStr){
        mTopbar.setRightIsVisible(true);
        mTopbar.setTvRightText(rightStr,16,R.color.black);
    }

    private void initHeaderLayout() {
        mTopbar = (Topbar) findViewById(R.id.fl_base_header);
        //titlebar事件监听
        mTopbar.setOnTopbarClickListener(this);
    }

    private void initLoadingView() {
    }
    /**
     * 进度加载动画
     */
    public void startLoading(){
        if(customWaitDialog == null){
            customWaitDialog = new CustomWaitDialog(this);
        }
        customWaitDialog.show();
    }
    /**
     * 进度加载动画
     */
    public void startLoading(String msg){
        if(customWaitDialog == null){
            customWaitDialog = new CustomWaitDialog(this);
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
    public void hideTitleBar(){
        mTopbar.setVisibility(View.GONE);
    }
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
//        super.setContentView(layoutResID);
        getLayoutInflater().inflate(layoutResID,containerLayout,true);
        initAcitivy();
    }

    @Override
    protected void onDestroy() {
        if(customWaitDialog != null){
            customWaitDialog = null;
        }
        super.onDestroy();
    }

    private void initAcitivy() {

    }

    @Override
    public void setContentView(View view) {
        containerLayout.addView(view);
        initAcitivy();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        this.containerLayout.addView(view,params);
        initAcitivy();

    }
    /**
     * 添加loadingView
     */
    public void setLoadingView(@LayoutRes int loadingViewResId){
        View view = getLayoutInflater().inflate(loadingViewResId,loadingView,true);
    }
    public void setLoadingView(View view){
        this.loadingView.addView(view);
    }
    /**
     * 添加errorView
     */
    public void setErrorView(@LayoutRes int errorViewResId){
        errorView.removeAllViews();
        View view = getLayoutInflater().inflate(errorViewResId,errorView,true);
    }
    public void setErrorView(View view){
        this.errorView.removeAllViews();
        this.errorView.addView(view);
    }
    /**
     * 通过状态显示加载页面
     */
    public void setShowView(ViewType viewType){
        hideAllView();
        switch (viewType){
            case CONTAINER_LAYOUT:
                containerLayout.setVisibility(View.VISIBLE);
                break;
            case LOADING_LAYOUT:
                loadingView.setVisibility(View.VISIBLE);
                break;
            case ERROR_LAYOUT:
                errorView.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void hideAllView() {
        containerLayout.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void leftClick() {
       this.finish();
    }

    @Override
    public void rightClick() {
        if(onRightButtonClicked != null)
            onRightButtonClicked.onRightClick();
    }


    /**
     * [防止快速点击]
     *
     * @return
     */
    long lastClick ;
    public  boolean fastClick() {

        if (System.currentTimeMillis() - lastClick <= 1000) {
            return true;
        }else
            lastClick = System.currentTimeMillis();
            return false;
    }

    public  enum ViewType {
        CONTAINER_LAYOUT,
        LOADING_LAYOUT,
        ERROR_LAYOUT
    }

}
