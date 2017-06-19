package com.zyrc.exhibit.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.entity.ChannelBean;
import com.zyrc.exhibit.entity.CommonListBean;
import com.zyrc.exhibit.entity.DetailExBean;
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
 * Created by Administrator on 2017/3/17 0017.
 */

public class GetDtaListModel {
    /**
     * 查询展会详细信息
     */
    public void getDetailEx(final Handler handler, int id) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_DETAIL_EX + id;
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
                        DetailExBean detailExBean = gson.fromJson(s, DetailExBean.class);
                        Message message = new Message();
                        message.obj = detailExBean;
                        message.what = HandlerConstant.DETAIL_EX_SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    /**
     * 获取频道
     */
    public void getChannel(final Handler handler, String id) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_CONFENCE_CHANNEL + id;
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
                        Message message = new Message();
                        message.obj = gson.fromJson(s, ChannelBean.class);
                        message.what = HandlerConstant.DETAIL_EX_CHANNEL;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }


    /**
     * 获取数据集合
     */
    public void getDataList(final Handler handler, String detailUrl, String param, final int type) {
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
                        CommonListBean detailExInfoBean = gson.fromJson(s, CommonListBean.class);
                        Message message = new Message();
                        message.obj = detailExInfoBean;
                        message.what = type;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
