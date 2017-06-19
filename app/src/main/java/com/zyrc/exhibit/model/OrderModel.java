package com.zyrc.exhibit.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.order.OrderBean;
import com.zyrc.exhibit.util.OKHttpSingle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class OrderModel {
    //okHttpClient对象
    OkHttpClient okHttpClient = OKHttpSingle.getInstance().getOkHttpClient();

    /**
     * 获得价格
     */
    public void getTotalPrice(final Handler handler, String detailUrl, String param) {
        //url
        final String url = UrlConstant.HTTP_URL_IP + detailUrl + param;
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
                Message message = new Message();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String success = jsonObject.getString("successed");
                    if (success.equals(HandlerConstant.REQUEST_SUCCESS)) {
                        message.obj = jsonObject.getString("data");
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
     * 保存订单
     */
    public void saveOrder(final Handler handler, String json) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_SAVE_ORDER;
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        //请求对象
        Request request = new Request.Builder().url(url).post(body).build();
        //调用请求
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //返回字符串
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String success = jsonObject.getString("successed");
                    if (success.equals(HandlerConstant.REQUEST_SUCCESS)) {
                        String data = jsonObject.getString("data");
                        Message message = new Message();
                        message.what = HandlerConstant.SEARCH_SUCCESS;
                        message.obj = data;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    /**
     * 获取所有订单
     */
    public void getAllOrder(final Handler handler, String detailUrl, String param) {
        //url
        final String url = UrlConstant.HTTP_URL_IP + detailUrl + param;
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
                Message message = new Message();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String success = jsonObject.getString("successed");
                    if (success.equals(HandlerConstant.REQUEST_SUCCESS)) {
                        Gson gson = new Gson();
                        message.obj = gson.fromJson(s, OrderBean.class);
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


}
