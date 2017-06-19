package com.zyrc.exhibit.app;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.HotCityBean;
import com.zyrc.exhibit.model.Model;
import com.zyrc.exhibit.util.SPUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class MyApplication extends Application implements AMapLocationListener {
    private static MyApplication mMyApplication;
    private static HotCityBean hotCityBean;//通用热门城市数据
    public static boolean isLogin = false;//是否登录状态
    public static String userId;//登录后的用户id
    private Model model;
    private Set<String> set;//推送标签
    public static String local;

    public MyApplication() {
        mMyApplication = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        cityLog();//定位
        isLogin();//判断登录
        JPushInterface.setDebugMode(true); //极光推送初始化
        JPushInterface.init(this);//极光推送
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5901bdb5");//科大讯飞初始化
        ShareSDK.initSDK(this);//shareSdk初始化

        //建议添加tag标签，发送消息的之后就可以指定tag标签来发送了
        set = new HashSet<>();
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

    //定位与记录用户当前所在城市
    private void cityLog() {
        //声明mLocationOption对象
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //声明AMapLocationClient对象
        AMapLocationClient mlocationClient = new AMapLocationClient(this);
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置只定位一次
        mLocationOption.setOnceLocation(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        //开始定位
        mlocationClient.startLocation();
    }

    /**
     * 定位回调方法
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                String city = aMapLocation.getCity();
                local = city;
                if (model == null) {
                    model = new Model();
                }
                Map<String, String> param = new HashMap<>();
                if (isLogin) {//如登录，则连通
                    param.put("userId", userId);
                }
                param.put("city", city);
                set.add(city);//名字任意，可多添加几个
                JPushInterface.setTags(this, set, null);//设置标签
                model.postData(handler, UrlConstant.HTTP_URL_CITY_LOG, HandlerConstant.SEARCH_SUCCESS, param);
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HandlerConstant.SEARCH_SUCCESS:
                    break;
            }
        }
    };
}
