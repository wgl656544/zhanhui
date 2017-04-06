package com.ex.administrator.zhanhui.model;

import android.os.Handler;
import android.os.Message;

import com.ex.administrator.zhanhui.constant.HandlerConstant;
import com.ex.administrator.zhanhui.constant.UrlConstant;
import com.ex.administrator.zhanhui.entity.ExhibitionTypeBean;
import com.ex.administrator.zhanhui.entity.SearchExhibitBean;
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
 * Created by Administrator on 2017/3/7 0007.
 * 展会搜索接口
 */

public class FindExhibitionModel {
    /**
     * 获取展会类型
     */
    public void getExhibitionType(final Handler handler, String name) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_FIND_EXHIBITION_TYPE + name;
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
                        ExhibitionTypeBean exhibitionTypeBean = gson.fromJson(s, ExhibitionTypeBean.class);
                        Message message = new Message();
                        message.obj = exhibitionTypeBean;
                        message.what = HandlerConstant.EXHIBITION_TYPE_SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    /**
     * 搜索展会
     */
    public void searchExhibition(final Handler handler, String name) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_SEARCH_EXHIBITION + name;
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
                        SearchExhibitBean searchExhibitBean = gson.fromJson(s, SearchExhibitBean.class);
                        Message message = new Message();
                        message.obj = searchExhibitBean;
                        message.what = HandlerConstant.SEARCH_EXHIBITION_SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


}
