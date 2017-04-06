package com.ex.administrator.zhanhui.model;

import android.os.Handler;
import android.os.Message;

import com.ex.administrator.zhanhui.constant.HandlerConstant;
import com.ex.administrator.zhanhui.constant.UrlConstant;
import com.ex.administrator.zhanhui.entity.SearchTeamBean;
import com.ex.administrator.zhanhui.entity.TeamTypeBean;
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
 * Created by Administrator on 2017/3/9 0009.
 */

public class TeamModel {
    /**
     * 查询团购的所有分类
     */
    public void getTeamCategory(final Handler handler, String name) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_TEAM_CATEGOTY + name;
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
                        TeamTypeBean teamTypeBean = gson.fromJson(s, TeamTypeBean.class);
                        Message message = new Message();
                        message.obj = teamTypeBean;
                        message.what = HandlerConstant.TEAM_TYPE_SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 搜索门票
     */
    public void searchTeam(final Handler handler) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_SEARCH_TEAM;
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
                        SearchTeamBean searchTeamBean = gson.fromJson(s, SearchTeamBean.class);
                        Message message = new Message();
                        message.obj = searchTeamBean;
                        message.what = HandlerConstant.SEARCH_TEAM_SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
