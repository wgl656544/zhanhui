package com.zyrc.exhibit.model;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zyrc.exhibit.constant.HandlerConstant;
import com.zyrc.exhibit.constant.UrlConstant;
import com.zyrc.exhibit.util.OKHttpSingle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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


    /**
     * post请求
     */
    public void postWXPay(final Handler handler, String paramUrl, final int requestCode, Map<String, String> params) {
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
                message.what = requestCode;
                message.obj = s;
                handler.sendMessage(message);
            }
        });
    }

    public void download(final String url, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onDownloadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                is = response.body().byteStream();
                long total = response.body().contentLength();
                File file = new File(Environment.getExternalStorageDirectory(), "zyrc.apk");
                fos = new FileOutputStream(file);
                long sum = 0;
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                    sum += len;
                    int progress = (int) (sum * 1.0f / total * 100);
                    // 下载中
                    listener.onDownloading(progress);
                }
                fos.flush();
                // 下载完成
                listener.onDownloadSuccess();
                is.close();
                fos.close();
            }
        });
    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess();

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }

}
