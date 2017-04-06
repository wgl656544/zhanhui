package com.ex.administrator.zhanhui.model;

import android.os.Handler;
import android.os.Message;

import com.ex.administrator.zhanhui.constant.HandlerConstant;
import com.ex.administrator.zhanhui.constant.UrlConstant;
import com.ex.administrator.zhanhui.entity.InfoBlogBean;
import com.ex.administrator.zhanhui.entity.InfoCategoryBean;
import com.ex.administrator.zhanhui.entity.InfoPlaceBean;
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
 * Created by Administrator on 2017/3/8 0008.
 */

public class InfoCategoryModel {
    /**
     * 资讯分类
     */
    public void getInfoCategory(final Handler handler, String name) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_INFO_CATEGORY + name;
        //okHttpClient对象
        OkHttpClient okHttpClient = OKHttpSingle.getInstance().getOkHttpClient();
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
                        InfoCategoryBean infoCategoryBean = gson.fromJson(s, InfoCategoryBean.class);
                        Message message = new Message();
                        message.obj = infoCategoryBean;
                        message.what = HandlerConstant.INFO_CATEGORY_SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 海南所有县市
     */
    public void getHaiNanAllCity(final Handler handler) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_INFO_HAINAN_ALL_CITY;
        //okHttpClient对象
        OkHttpClient okHttpClient = OKHttpSingle.getInstance().getOkHttpClient();
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
                        InfoPlaceBean infoPlaceBean = gson.fromJson(s, InfoPlaceBean.class);
                        Message message = new Message();
                        message.obj = infoPlaceBean;
                        message.what = HandlerConstant.INFO_HAINAN_ALL_CITY_SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 查询资讯
     */
    public void searchBlog(final Handler handler) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_INFO_SEARCH_BLOG;
        //okHttpClient对象
        OkHttpClient okHttpClient = OKHttpSingle.getInstance().getOkHttpClient();
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
                        InfoBlogBean infoBlogBean = gson.fromJson(s, InfoBlogBean.class);
                        Message message = new Message();
                        message.obj = infoBlogBean;
                        message.what = HandlerConstant.INFO_SEARCH_BLOG_SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
