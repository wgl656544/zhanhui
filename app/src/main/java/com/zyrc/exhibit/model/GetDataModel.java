package com.zyrc.exhibit.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.CommonBean;
import com.zyrc.exhibit.entity.InfoCategoryBean;
import com.zyrc.exhibit.entity.InfoPlaceBean;
import com.zyrc.exhibit.entity.TypeBean;
import com.zyrc.exhibit.util.OKHttpSingle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/7 0007.
 */

public class GetDataModel {
    /**
     * 搜索
     */
    public void search(final Handler handler, String detailUrl, String param) {
        //url
        final String url = UrlConstant.HTTP_URL_IP + detailUrl + param;
        //okHttpClient对象
        OkHttpClient okHttpClient = OKHttpSingle.getInstance().getOkHttpClient();
        //Request对象
        Request request = new Request.Builder().url(url).build();
        //Call对象
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(HandlerConstant.REQUEST_FAIL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Message message = new Message();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String success = jsonObject.getString("successed");
                    if (success.equals(HandlerConstant.REQUEST_SUCCESS)) {
                        Gson gson = new Gson();
                        message.obj = gson.fromJson(s, CommonBean.class);
                        message.what = HandlerConstant.SEARCH_SUCCESS;
                        handler.sendMessage(message);
                    } else {
                        message.obj = jsonObject.getString("message");
                        message.what = HandlerConstant.REQUEST_FAIL;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 获取类型
     */
    public void getType(final Handler handler, String detailUrl, String param) {
        //url
        String url = UrlConstant.HTTP_URL_IP + detailUrl + param;
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
                        TypeBean exhibitionTypeBean = gson.fromJson(s, TypeBean.class);
                        Message message = new Message();
                        message.obj = exhibitionTypeBean;
                        message.what = HandlerConstant.SEARCH_TYPE_SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


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

}
