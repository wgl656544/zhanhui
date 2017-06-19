package com.zyrc.exhibit.util;

import android.content.Context;

import com.google.gson.Gson;
import com.zyrc.exhibit.app.MyApplication;
import com.zyrc.exhibit.entity.UserBean;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class UserUtils {
    public static void saveUserInfo(Context context, UserBean userBean) {
        SPUtils.put(context, "userId", String.valueOf(userBean.getData().getId()));//保存id
        String json = new Gson().toJson(userBean);
        SPUtils.put(context, "userinfo" + userBean.getData().getId(), json);//保存信息
        MyApplication.isLogin = true;
        MyApplication.userId = String.valueOf(userBean.getData().getId());
    }
}
