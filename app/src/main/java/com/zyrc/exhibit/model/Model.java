package com.zyrc.exhibit.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.util.OKHttpSingle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class Model {
    //okHttpClient对象
    private OkHttpClient okHttpClient = OKHttpSingle.getInstance().getOkHttpClient();

    /**
     * get请求
     */
    public void getData(final Handler handler, String detailUrl, final int requestCode, String param) {
        //url
        final String url = UrlConstant.HTTP_URL_IP + detailUrl + param;
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
                    if (jsonObject.has("successed")) {
                        String success = jsonObject.getString("successed");
                        if (success.equals(HandlerConstant.REQUEST_SUCCESS)) {
                            message.obj = s;
                            message.what = requestCode;
                            handler.sendMessage(message);
                        } else {
                            message.obj = jsonObject.getString("message");
                            message.what = HandlerConstant.REQUEST_FAIL;
                            handler.sendMessage(message);
                        }
                    } else {
                        handler.sendEmptyMessage(HandlerConstant.REQUEST_FAIL);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * post请求
     */
    public void postData(final Handler handler, String paramUrl, final int requestCode, Map<String, String> params) {
        final Message message = new Message();
        //url
        String url = UrlConstant.HTTP_URL_IP + paramUrl;
        //请求实体
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        RequestBody body = builder.build();
        //请求对象
        Request request = new Request.Builder().url(url).post(body).build();
        //调用请求
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(HandlerConstant.REQUEST_FAIL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //返回字符串
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.has("successed")) {
                        String success = jsonObject.getString("successed");
                        if (success.equals(HandlerConstant.REQUEST_SUCCESS)) {
                            message.what = requestCode;
                            message.obj = s;
                            handler.sendMessage(message);
                        } else {
                            message.obj = jsonObject.getString("message");
                            message.what = HandlerConstant.REQUEST_FAIL;
                            handler.sendMessage(message);
                        }
                    } else {
                        handler.sendEmptyMessage(HandlerConstant.REQUEST_FAIL);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


}
