package com.ex.administrator.zhanhui.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ex.administrator.zhanhui.constant.HandlerConstant;
import com.ex.administrator.zhanhui.constant.UrlConstant;
import com.ex.administrator.zhanhui.entity.OneUserAddress;
import com.ex.administrator.zhanhui.entity.ShipAddressBean;
import com.ex.administrator.zhanhui.entity.UserBean;
import com.ex.administrator.zhanhui.entity.UserPhotoBean;
import com.ex.administrator.zhanhui.entity.UserUpDateBean;
import com.ex.administrator.zhanhui.util.OKHttpSingle;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/1 0001.
 */

public class UserModel {
    //okHttpClient对象
    private OkHttpClient okHttpClient = OKHttpSingle.getInstance().getOkHttpClient();

    /**
     * 获取验证码
     */
    public void getMessageCode(final Handler handler, String mobile) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_GET_MESSAGE_CODE;

        //请求实体
        RequestBody formBody = new FormBody.Builder().add("mobile", mobile).build();
        //请求对象
        Request request = new Request.Builder().url(url).post(formBody).build();
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
                handler.sendEmptyMessage(100);
            }
        });
    }

    /**
     * 用户名登录
     */
    public void userLogin(String username, String password) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_USER_LOGIN;
        //请求实体
        RequestBody formBody = new FormBody.Builder().add("username", username).add("password", password).build();
        //请求对象
        Request request = new Request.Builder().url(url).post(formBody).build();
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
            }
        });
    }

    /**
     * 手机号码登录
     */
    public void mobileLogin(final Handler handler, String mobile, String msgCode) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_MOBILE_LOGIN;
        //请求实体
        RequestBody formBody = new FormBody.Builder().add("mobile", mobile).add("msgCode", msgCode).build();
        //请求对象
        Request request = new Request.Builder().url(url).post(formBody).build();
        //调用请求
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(HandlerConstant.LOGIN_FAIL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //返回字符串
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String success = jsonObject.getString("successed");
                    if (success.equals(HandlerConstant.REQUEST_SUCCESS)) {
                        Gson gson = new Gson();
                        UserBean userBean = gson.fromJson(s, UserBean.class);
                        Message message = new Message();
                        message.what = HandlerConstant.LOGIN_SUCCESS;
                        message.obj = userBean;
                        handler.sendMessage(message);
                    } else {
                        handler.sendEmptyMessage(HandlerConstant.LOGIN_FAIL);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //上传文件(图片)
    public void fileUpLoad(final Handler handler, File file, String id) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_FILE_UPLOAD;
        //请求实体
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder().addFormDataPart("userId", id).addFormDataPart("file", file.getName(), fileBody).build();
        //请求对象
        Request request = new Request.Builder().url(url).post(requestBody).build();
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
                        Gson gson = new Gson();
                        UserPhotoBean userPhotoBean = gson.fromJson(s, UserPhotoBean.class);
                        Message message = new Message();
                        message.obj = userPhotoBean;
                        message.what = HandlerConstant.FILE_UPLOAD_SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 修改用户资料
     */
    public void userUpDate(final Handler handler, String id, String key, String value) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_USER_UPDATE;
        //请求实体
        RequestBody formBody = new FormBody.Builder().add("id", id).add(key, value).build();
        //请求对象
        Request request = new Request.Builder().url(url).post(formBody).build();
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
                        Gson gson = new Gson();
                        UserUpDateBean userPhotoBean = gson.fromJson(s, UserUpDateBean.class);
                        Message message = new Message();
                        message.obj = userPhotoBean;
                        message.what = HandlerConstant.USER_UPDATE_SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    /**
     * 查询用户地址
     */
    public void findUserAddress(final Handler handler, String userId) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_FIND_USER_ADDRESS;
        //请求实体
        RequestBody formBody = new FormBody.Builder().
                add("userId", userId).
                build();
        //请求对象
        Request request = new Request.Builder().url(url).post(formBody).build();
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
                        Gson gson = new Gson();
                        ShipAddressBean shipAddressBean = gson.fromJson(s, ShipAddressBean.class);
                        Message message = new Message();
                        message.obj = shipAddressBean;
                        message.what = HandlerConstant.FIND_USER_ADDRESS_SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    /**
     * 修改或新增用户地址
     */
    public void userAddress(final Handler handler, Map<String, String> params) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_SAVE_USER_ADDRESS;
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
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //返回字符串
                String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String success = jsonObject.getString("successed");
                    if (success.equals(HandlerConstant.REQUEST_SUCCESS)) {
                        handler.sendEmptyMessage(HandlerConstant.SAVE_USER_ADDRESS_SUCCESS);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    /**
     * 删除用户地址
     */
    public void deleteUserAddress(final Handler handler, String id) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_DELETE_USER_ADDRESS;
        //请求实体
        RequestBody formBody = new FormBody.Builder().
                add("id", id).
                build();
        //请求对象
        Request request = new Request.Builder().url(url).post(formBody).build();
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
                Log.d("lingyi", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String success = jsonObject.getString("successed");
                    if (success.equals(HandlerConstant.REQUEST_SUCCESS)) {
                        handler.sendEmptyMessage(HandlerConstant.DELETE_USER_ADDRESS_SUCCESS);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * 查询单个用户地址
     */
    public void findOneUserAddress(final Handler handler, String id) {
        //url
        String url = UrlConstant.HTTP_URL_IP + UrlConstant.HTTP_URL_FIND_ONE_USER_ADDRESS;
        //请求实体
        RequestBody formBody = new FormBody.Builder().
                add("id", id).
                build();
        //请求对象
        Request request = new Request.Builder().url(url).post(formBody).build();
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
                        Gson gson = new Gson();
                        OneUserAddress oneUserAddress = gson.fromJson(s, OneUserAddress.class);
                        Message message = new Message();
                        message.obj = oneUserAddress;
                        message.what = HandlerConstant.FIND_ONE_ADDRESS_SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


}
