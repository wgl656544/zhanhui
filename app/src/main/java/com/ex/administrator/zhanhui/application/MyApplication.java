package com.ex.administrator.zhanhui.application;

import android.app.Application;

import com.ex.administrator.zhanhui.entity.HotCityBean;
import com.ex.administrator.zhanhui.util.SPUtils;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class MyApplication extends Application {
    private static MyApplication mMyApplication;
    private static HotCityBean hotCityBean;//通用热门城市数据
    public static boolean isLogin = false;//是否登录状态
    public static String userId;//登录后的用户id

    public MyApplication() {
        mMyApplication = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isLogin();
    }

    public static MyApplication getmMyApplication() {
        return mMyApplication;
    }


    public static HotCityBean getHotCityBean() {
        return hotCityBean;
    }

    public static void setHotCityBean(HotCityBean hotCityBean) {
        MyApplication.hotCityBean = hotCityBean;
    }

    //判断是否已经登录
    private void isLogin() {
        if (SPUtils.contains(this, "userId")) {
            userId = (String) SPUtils.get(this, "userId", "-1");
            isLogin = true;
        }
    }
}
