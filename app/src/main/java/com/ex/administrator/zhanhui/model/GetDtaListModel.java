package com.ex.administrator.zhanhui.model;

import android.os.Handler;
import android.os.Message;

import com.ex.administrator.zhanhui.constant.HandlerConstant;
import com.ex.administrator.zhanhui.constant.UrlConstant;
import com.ex.administrator.zhanhui.entity.CommonListBean;
import com.ex.administrator.zhanhui.entity.DetailExBean;
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

//    /**
//     * 获取详细展会广告
//     */
//    public void getDetailExAdvert(final Handler handler, int id) {
//        //url
//        String url = UrlConstant.HTTP_URL_IP +
//                UrlConstant.HTTP_URL_DETAIL_EX_ADVERT +
//                "exhibId=" + id + "&name=ex-hy-home-top";
//        //okHttpClient对象
//        OkHttpClient okHttpClient = OKHttpSingle.getInstance().getOkHttpClient();
//        //Request对象
//        Request request = new Request.Builder().url(url).build();
//        //Call对象
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String s = response.body().string();
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    String success = jsonObject.getString("successed");
//                    if (success.equals(HandlerConstant.REQUEST_SUCCESS)) {
//                        Gson gson = new Gson();
//                        CommonListBean detailExAdvertBean = gson.fromJson(s, CommonListBean.class);
//                        Message message = new Message();
//                        message.obj = detailExAdvertBean;
//                        message.what = HandlerConstant.DETAIL_EX_ADVERT_SUCCESS;
//                        handler.sendMessage(message);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }
//
//    /**
//     * 获取详细展会信息
//     */
//    public void getDetailExInfo(final Handler handler, int id) {
//        //url
//        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_DETAIL_EX_INFO + "exhibId=" + id;
//        //okHttpClient对象
//        OkHttpClient okHttpClient = OKHttpSingle.getInstance().getOkHttpClient();
//        //Request对象
//        Request request = new Request.Builder().url(url).build();
//        //Call对象
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String s = response.body().string();
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    String success = jsonObject.getString("successed");
//                    if (success.equals(HandlerConstant.REQUEST_SUCCESS)) {
//                        Gson gson = new Gson();
//                        CommonListBean detailExInfoBean = gson.fromJson(s, CommonListBean.class);
//                        Message message = new Message();
//                        message.obj = detailExInfoBean;
//                        message.what = HandlerConstant.DETAIL_EX_INFO_SUCCESS;
//                        handler.sendMessage(message);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }

    /**
     * 获取数据集合
     */
    public void getDataList(final Handler handler, String detailUrl, String param) {
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
                        message.what = HandlerConstant.SEARCH_SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


//    /**
//     * 获取详细展会嘉宾
//     */
//    public void getDetailExHonor(final Handler handler, int id) {
//        //url
//        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_DETAIL_EX_HONOR + "exhibId=" + id;
//        //okHttpClient对象
//        OkHttpClient okHttpClient = OKHttpSingle.getInstance().getOkHttpClient();
//        //Request对象
//        Request request = new Request.Builder().url(url).build();
//        //Call对象
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String s = response.body().string();
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    String success = jsonObject.getString("successed");
//                    if (success.equals(HandlerConstant.REQUEST_SUCCESS)) {
//                        Gson gson = new Gson();
//                        CommonBean detailExCommonBean = gson.fromJson(s, CommonBean.class);
//                        Message message = new Message();
//                        message.obj = detailExCommonBean;
//                        message.what = HandlerConstant.DETAIL_EX_HONOR_SUCCESS;
//                        handler.sendMessage(message);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }

//    /**
//     * 获取详细展会门票
//     */
//    public void getDetailExTicket(final Handler handler, int id) {
//        //url
//        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_DETAIL_EX_TICKET + "ecxhibId=" + id;
//        //okHttpClient对象
//        OkHttpClient okHttpClient = OKHttpSingle.getInstance().getOkHttpClient();
//        //Request对象
//        Request request = new Request.Builder().url(url).build();
//        //Call对象
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String s = response.body().string();
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    String success = jsonObject.getString("successed");
//                    if (success.equals(HandlerConstant.REQUEST_SUCCESS)) {
//                        Gson gson = new Gson();
//                        CommonBean detailExCommonBean = gson.fromJson(s, CommonBean.class);
//                        Message message = new Message();
//                        message.obj = detailExCommonBean;
//                        message.what = HandlerConstant.DETAIL_EX_TICKET_SUCCESS;
//                        handler.sendMessage(message);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }

//    /**
//     * 获取详细展会新闻
//     */
//    public void getDetailExNews(final Handler handler, int id) {
//        //url
//        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_DETAIL_EX_NEWS + "exhibId=" + id;
//        //okHttpClient对象
//        OkHttpClient okHttpClient = OKHttpSingle.getInstance().getOkHttpClient();
//        //Request对象
//        Request request = new Request.Builder().url(url).build();
//        //Call对象
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String s = response.body().string();
//                try {
//                    JSONObject jsonObject = new JSONObject(s);
//                    String success = jsonObject.getString("successed");
//                    if (success.equals(HandlerConstant.REQUEST_SUCCESS)) {
//                        Gson gson = new Gson();
//                        CommonBean detailExCommonBean = gson.fromJson(s, CommonBean.class);
//                        Message message = new Message();
//                        message.obj = detailExCommonBean;
//                        message.what = HandlerConstant.DETAIL_EX_NEWS_SUCCESS;
//                        handler.sendMessage(message);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }


}
