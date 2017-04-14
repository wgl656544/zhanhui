package com.ex.administrator.zhanhui.model;

import android.os.Handler;
import android.os.Message;

import com.ex.administrator.zhanhui.constant.HandlerConstant;
import com.ex.administrator.zhanhui.constant.UrlConstant;
import com.ex.administrator.zhanhui.entity.HomePageBean;
import com.ex.administrator.zhanhui.util.OKHttpSingle;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class HomePageModel {
    //okHttpClient对象
    OkHttpClient okHttpClient = OKHttpSingle.getInstance().getOkHttpClient();

    /**
     * 获取首页广告
     */
    public void getHomePageAdvers(final Handler handler) {

        //url
        final String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_PAGE_ADVERS + "?name=pg-home-";
        //Request对象
        Request request = new Request.Builder().url(url).build();
        //Call对象
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String success = jsonObject.getString("successed");
                    if (success.equals(HandlerConstant.REQUEST_SUCCESS)) {
                        Gson gson = new Gson();
                        HomePageBean homePageBean = gson.fromJson(s, HomePageBean.class);
                        Message message = new Message();
                        message.obj = homePageBean;
                        message.what = HandlerConstant.HOME_PAGE_SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取首页业务
     */
    public void getHomePageBusi(final Handler handler) {
        //url
        final String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_HOME_BUS;
        //Request对象
        Request request = new Request.Builder().url(url).build();
        //Call对象
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String success = jsonObject.getString("successed");
                    if (success.equals(HandlerConstant.REQUEST_SUCCESS)) {
                        Gson gson = new Gson();
                        HomePageBean homePageBean = gson.fromJson(s, HomePageBean.class);
                        Message message = new Message();
                        message.obj = homePageBean;
                        message.what = HandlerConstant.HOME_BUSI_SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
