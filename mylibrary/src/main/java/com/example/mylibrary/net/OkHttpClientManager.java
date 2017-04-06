package com.example.mylibrary.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.example.mylibrary.utils.FileUtils;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.FileNameMap;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by user on 2016/4/6.
 */

public class OkHttpClientManager
{
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private Gson mGson;
    private static final String TAG = "OkHttpClientManager";
    private Context context;


    private static final String BOUNDARY =  UUID.randomUUID().toString(); // 边界标识 随机生成
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";
    private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
     /**
      * 请求使用多长时间
     */
    private static int requestTime = 0;

    private static final String CHARSET = "utf-8"; // 设置编码

    private OkHttpClientManager(Context context)
    {
        this.context = context;

        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .cache(new Cache(context.getCacheDir(), cacheSize))
                .cookieJar(new CookieJar() {
                    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                ;

         mOkHttpClient=builder.build();
        //
        mHandler = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    public static OkHttpClientManager getInstance(Context context)
    {
        if (mInstance == null)
        {
            synchronized (OkHttpClientManager.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpClientManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * android上传文件到服务器
     *
     * @param filePath
     *            需要上传的文件的路径
     * @param fileKey
     *            在网页上<input type=file name=xxx/> xxx就是这里的fileKey
     * @param RequestURL
     *            请求的URL
     */
    public void uploadFile(String filePath, String fileKey, String RequestURL,
                           Map<String, String> param) {
        if (filePath == null) {
//            sendMessage(UPLOAD_FILE_NOT_EXISTS_CODE,"文件不存在");
            return;
        }
        try {
            File file = new File(filePath);
            uploadFile(file, fileKey, RequestURL, param);
        } catch (Exception e) {
//            sendMessage(UPLOAD_FILE_NOT_EXISTS_CODE,"文件不存在");
            e.printStackTrace();
            return;
        }
    }

    /**
     * android上传文件到服务器
     *
     * @param file
     *            需要上传的文件
     * @param fileKey
     *            在网页上<input type=file name=xxx/> xxx就是这里的fileKey
     * @param RequestURL
     *            请求的URL
     */
    public void uploadFile(final File file, final String fileKey,
                           final String RequestURL, final Map<String, String> param) {
        if (file == null || (!file.exists())) {
//            sendMessage(UPLOAD_FILE_NOT_EXISTS_CODE,"文件不存在");
            return;
        }

        Log.i(TAG, "请求的URL=" + RequestURL);
        Log.i(TAG, "请求的fileName=" + file.getName());
        Log.i(TAG, "请求的fileKey=" + fileKey);
        new Thread(new Runnable() {  //开启线程上传文件
            @Override
            public void run() {
                toUploadFile(file, fileKey, RequestURL, param);
            }
        }).start();

    }

    private void toUploadFile(File file, String fileKey, String RequestURL,
                              Map<String, String> param) {
        String result = null;
        requestTime= 0;

        long requestTime = System.currentTimeMillis();
        long responseTime = 0;

        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5*1000);
            conn.setConnectTimeout(20*1000);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
//          conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            /**
             * 当文件不为空，把文件包装并且上传
             */
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            StringBuffer sb = null;
            String params = "";

            /***
             * 以下是用于上传参数
             */
            if (param != null && param.size() > 0) {
                Iterator<String> it = param.keySet().iterator();
                while (it.hasNext()) {
                    sb = null;
                    sb = new StringBuffer();
                    String key = it.next();
                    String value = param.get(key);
                    sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
                    sb.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(LINE_END).append(LINE_END);
                    sb.append(value).append(LINE_END);
                    params = sb.toString();
                    Log.i(TAG, key+"="+params+"##");
                    dos.write(params.getBytes());
//                  dos.flush();
                }
            }

            sb = null;
            params = null;
            sb = new StringBuffer();
            /**
             * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
             * filename是文件的名字，包含后缀名的 比如:abc.png
             */
            sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
            sb.append("Content-Disposition:form-data; name=\"" + fileKey
                    + "\"; filename=\"" + file.getName() + "\"" + LINE_END);
            sb.append("Content-Type:image/pjpeg" + LINE_END); // 这里配置的Content-type很重要的 ，用于服务器端辨别文件的类型的
            sb.append(LINE_END);
            params = sb.toString();
            sb = null;

            Log.i(TAG, file.getName()+"=" + params+"##");
            dos.write(params.getBytes());
            /**上传文件*/
            InputStream is = new FileInputStream(file);
//            onUploadProcessListener.initUpload((int)file.length());
            byte[] bytes = new byte[1024];
            int len = 0;
            int curLen = 0;
            while ((len = is.read(bytes)) != -1) {
                curLen += len;
                dos.write(bytes, 0, len);
//                onUploadProcessListener.onUploadProcess(curLen);
            }
            is.close();

            dos.write(LINE_END.getBytes());
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
            dos.write(end_data);
            dos.flush();
//
//          dos.write(tempOutputStream.toByteArray());
            /**
             * 获取响应码 200=成功 当响应成功，获取响应的流
             */
            int res = conn.getResponseCode();
            responseTime = System.currentTimeMillis();
            this.requestTime = (int) ((responseTime-requestTime)/1000);
            Log.e(TAG, "response code:" + res);
            if (res == 200) {
                Log.e(TAG, "request success");
                InputStream input = conn.getInputStream();
                StringBuffer sb1 = new StringBuffer();
                int ss;
                while ((ss = input.read()) != -1) {
                    sb1.append((char) ss);
                }
                result = sb1.toString();
                Log.e(TAG, "result : " + result);
//                sendMessage(UPLOAD_SUCCESS_CODE, "上传结果："
//                        + result);
                return;
            } else {
                Log.e(TAG, "request error");
//                sendMessage(UPLOAD_SERVER_ERROR_CODE,"上传失败：code=" + res);
                return;
            }
        } catch (MalformedURLException e) {
//            sendMessage(UPLOAD_SERVER_ERROR_CODE,"上传失败：error=" + e.getMessage());
            e.printStackTrace();
            return;
        } catch (IOException e) {
//            sendMessage(UPLOAD_SERVER_ERROR_CODE,"上传失败：error=" + e.getMessage());
            e.printStackTrace();
            return;
        }
    }




    /**
     * 上传文件
     * */
    public String uploadFile(String actionUrl,String filePath){

        String result;
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String newName = "head.jpg";
//        String uploadFile = "storage/sdcard1/bagPictures/102.jpg";
        ;
        try {
            URL url = new URL(actionUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
			/* 设置传送的method=POST */
            con.setRequestMethod("POST");
			/* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
			/* 设置DataOutputStream */
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; "
                    + "name=\"file1\";filename=\"" + newName + "\"" + end);
            ds.writeBytes(end);
			/* 取得文件的FileInputStream */
            FileInputStream fStream = new FileInputStream(filePath);
			/* 设置每次写入1024bytes */
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
			/* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
//			/* close streams */
            fStream.close();
            ds.flush();
			/* 取得Response内容 */
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            result = b.toString();
			/* 将Response显示于Dialog */
            Log.e("上传成功", b.toString().trim());
			/* 关闭DataOutputStream */
            ds.close();
        } catch (Exception e) {
            result = "上传失败";
            Log.e("上传失败",  e.toString());
        }
        return result;
    }

    /**
     * 上传文件
     * @param url
     * @param filePath
     * @return 文件名
     * @throws Exception
     */
    @SuppressLint("NewApi")
    public  String uploadFileCommon(String url, String filePath,String token) {
        if (!TextUtils.isEmpty(url)&&!TextUtils.isEmpty(filePath)) {
            try {
                HttpURLConnection conn = getConnection2(url,filePath,token);
                if(conn != null) {
                    conn.connect();
                    InputStream inputStream = conn.getInputStream();
                    byte[] data = readInputStream(inputStream);
                    conn.disconnect();
                    String json = new String(data);
                    Log.e(TAG, "JSON:" + json);
                    return json;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
    private  HttpURLConnection getConnection2(String url,String filePath,String Token) throws Exception {
        URL url1 = new URL(url+"");
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
        conn.setConnectTimeout(5*1000);// 连接时间
        conn.setReadTimeout(20*1000);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Android");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestMethod("POST");
        conn.setInstanceFollowRedirects(true);
        conn.setRequestProperty("hash", Token);//认证token

        File file = new File(filePath);
        String filePrefix = Utility.getFilePrefix(filePath);
        if("jpg".equals(filePrefix)){
            conn.setRequestProperty("datatype", "jpg");
        }else if("png".equals(filePrefix)){
            conn.setRequestProperty("datatype", "png");
        }else if("gif".equals(filePrefix)){
            conn.setRequestProperty("datatype", "gif");
        }else if("aac".equals(filePrefix)){
            conn.setRequestProperty("datatype", "acc");
        }else if("amr".equals(filePrefix)){
            conn.setRequestProperty("datatype", "amr");
        }else if("mp3".equals(filePrefix)){
            conn.setRequestProperty("datatype", "mp3");
        }else if("wav".equals(filePrefix)){
            conn.setRequestProperty("datatype", "wav");
        }else{
            Log.i("library","上传的文件类型不支持");
            return null;
        }

        OutputStream out = conn.getOutputStream();
        InputStream fis =new FileInputStream(filePath);
        BufferedInputStream bis  = new BufferedInputStream(fis);
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = fis.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        fis.close();
        out.flush();
        out.close();
        return conn;
    }


    public static String uploadSubmitData(String urls,String params){
        URL url;
        HttpURLConnection http = null;
        String result = "";
        try {
            url = new URL(urls);
            http = (HttpURLConnection) url.openConnection();
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setUseCaches(false);
            http.setConnectTimeout(50000);//设置连接超时
            //如果在建立连接之前超时期满，则会引发一个 java.net.SocketTimeoutException。超时时间为零表示无穷大超时。
            http.setReadTimeout(50000);//设置读取超时
            //如果在数据可读取之前超时期满，则会引发一个 java.net.SocketTimeoutException。超时时间为零表示无穷大超时。
            http.setRequestMethod("POST");
            // http.setRequestProperty("Content-Type","text/xml; charset=UTF-8");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.connect();

            OutputStreamWriter osw = new OutputStreamWriter(http.getOutputStream(), "utf-8");
            osw.write(params);
            osw.flush();
            osw.close();

            if (http.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    result += inputLine;
                }
                in.close();
                //result = "["+result+"]";
            }
        } catch (Exception e) {
            System.out.println("err");
        } finally {
            if (http != null) http.disconnect();
//            if (fis != null) fis.close();
        }
        return result;
    }


    public static  String uploadFile(String url,String hash,String filePath) throws Exception {

        if (!TextUtils.isEmpty(url)) {

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5 * 1000);// 连接时间
            conn.setReadTimeout(30 * 1000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "close");
            conn.setRequestProperty("user-agent", "Android");
//			conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
//			conn.setRequestProperty("Content-Type", "multipart/form-data; charset=utf-8");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            conn.setRequestProperty("token", hash);//认证token
            File file = new File(filePath);
            //压缩图片
//            InputStream fis = ImageUtils.comp(bitmap);
            InputStream fis = new FileInputStream(file);
            String filePrefix = Utility.getFilePrefix(filePath);

            if("jpg".equals(filePrefix)){
                conn.setRequestProperty("datatype", "jpg");
            }else if("png".equals(filePrefix)){
                conn.setRequestProperty("datatype", "png");
            }else if("gif".equals(filePrefix)){
                conn.setRequestProperty("datatype", "gif");
            }else if("aac".equals(filePrefix)){
                conn.setRequestProperty("datatype", "acc");
            }else if("amr".equals(filePrefix)){
                conn.setRequestProperty("datatype", "amr");
            }else if("mp3".equals(filePrefix)){
                conn.setRequestProperty("datatype", "mp3");
            }else if("wav".equals(filePrefix)){
                conn.setRequestProperty("datatype", "wav");
            }else{
                Log.i("library","上传的文件类型不支持");
                return null;
            }

            // 将图片的流数据写入
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = fis.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            fis.close();
            out.flush();
            out.close();

            if (conn.getResponseCode() == 200) {
                return new String(readInputStream(conn.getInputStream()));
            }
        }
            return null;
    }
    private  static  byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }


    /**
     * 同步的Get请求
     * 需要在UI线程中另外开线程
     * @param url
     * @return Response
     */
    private Response _getAsyn(String url) throws IOException
    {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;
    }


    /**
     * 同步基于post的文件上传
     *
     * @param params
     * @return
     */
    private Response _post(String url, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        return mOkHttpClient.newCall(request).execute();
    }

    private Response _post(String url, File file, String fileKey) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        return mOkHttpClient.newCall(request).execute();
    }

    private Response _post(String url, File file, String fileKey, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 异步基于post的文件上传
     *
     * @param url
     * @param callback
     * @param files
     * @param fileKeys
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件不带参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File file, String fileKey) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        deliveryResult(callback, request);
    }

    /**
     * 异步基于post的文件上传，单文件且携带其他form参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @param params
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File file, String fileKey, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        deliveryResult(callback, request);
    }
 /**
     * 异步基于post的文件上传，单文件且携带其他form参数上传
     *
     * @param url
     * @param callback
     * @param file
     * @param fileKey
     * @param params
     * @throws IOException
     */
    private void _postAsyn(String url, ResultCallback callback, File file, String fileKey, HashMap<String,String> params) throws IOException
    {
        Request request;
        if(file != null){
            request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, changeParams(params));
        }else{
             request = buildMultipartFormRequest(url, null, new String[]{fileKey}, changeParams(params));
        }
        deliveryResult(callback, request);
    }
    private Param[] changeParams(HashMap<String,String> params){
        int i = 0;
        Param[] value = new Param[params.size()];
        for(Map.Entry<String,String> entry:params.entrySet()){
            Param param = new Param(entry.getKey(),entry.getValue());
            value[i++] = param;
        }
        return value;
    }

    /**
     * 异步下载文件
     *
     * @param url
     * @param destFileDir 本地文件存储的文件夹
     * @param callback
     */
    private void _downloadAsyn(final String url, final String destFileDir, final ResultCallback callback)
    {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(final Call request, final IOException e)
            {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(Call call,Response response)
            {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try
                {
                    is = response.body().byteStream();

                    File directory = new File(destFileDir);
                    if(!directory.exists())
                        directory.mkdir();
                    File file = new File(destFileDir, FileUtils.getRealName(url));
                    boolean flag = file.exists();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1)
                    {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    sendSuccessResultCallback(file.getAbsolutePath(), callback);
                } catch (IOException e)
                {
                    sendFailedStringCallback(call, e, callback);
                } finally
                {
                    try
                    {
                        if (is != null) is.close();
                    } catch (IOException e)
                    {
                    }
                    try
                    {
                        if (fos != null) fos.close();
                    } catch (IOException e)
                    {
                    }
                }

            }
        });
    }

//    private String getFileName(String path)
//    {
//        int separatorIndex = path.lastIndexOf("/");
//        int endIndex = path.indexOf("?");
//        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, endIndex);
//    }
/*
    private String getFileName(String path)
    {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }
*/

    /**
     * 加载图片
     *
     * @param view
     * @param url
     * @throws IOException
     * */

    private void _displayImage(final ImageView view, final String url, final int errorResId)
    {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                setErrorResId(view, errorResId);
            }

            @Override
            public void onResponse(Call call,Response response)
            {
                InputStream is = null;
                try
                {
                    is = response.body().byteStream();
//                    ImageUtils.ImageSize actualImageSize = ImageUtils.getImageSize(is);
//                    ImageUtils.ImageSize imageViewSize = ImageUtils.getImageViewSize(view);
//                    int inSampleSize = ImageUtils.calculateInSampleSize(actualImageSize, imageViewSize);
                    try
                    {
                        is.reset();
                    } catch (IOException e) {
                        response = _getAsyn(url);
                        is = response.body().byteStream();
                    }

                    BitmapFactory.Options ops = new BitmapFactory.Options();
                    ops.inJustDecodeBounds = false;
//                    ops.inSampleSize = inSampleSize;
                    final Bitmap bm = BitmapFactory.decodeStream(is, null, ops);
                    mHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            view.setImageBitmap(bm);
                        }
                    });
                } catch (Exception e)
                {
                    setErrorResId(view, errorResId);

                } finally
                {
                    if (is != null) try
                    {
                        is.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    private void setErrorResId(final ImageView view, final int errorResId)
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                view.setImageResource(errorResId);
            }
        });
    }


    /******************************对外公布的方法************************************/

    /**
     * 同步的get请求，需要另开线程执行
     * @param url
     * @return Response
     * @throws IOException
     */
    public  Response getAsyn(String url) throws IOException
    {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        return call.execute();
    }

    /**
     * 同步的get请求，需要另开线程执行
     * @param url
     * @return String
     * @throws IOException
     */
    public  String getAsString(String url) throws IOException
    {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute.body().string();
    }

    /**
     * 异步的get请求
     * @param url
     * @param callback
     */
    public  void getAsyn(String url, ResultCallback callback)
    {
        final Request request = new Request.Builder()
                .url(url)
                .build();

        deliveryResult(callback, request);
    }
    /**
     * 异步的get请求
     * @param url
     * @param callback
     */
    public  void getAsyn(String url, ResultCallback callback,HashMap<String,String> map)
    {
        String httpUrl = getHttpUrl(url,map);
        Log.d("拼接后的URL",httpUrl);
        final Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        deliveryResult(callback, request);
    }

    private static String getHttpUrl(String url, HashMap<String, String> map) {

        StringBuilder builder = new StringBuilder(url);
        builder.append("?");
        for(Map.Entry<String,String> entry: map.entrySet()){
            builder.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

    /**
     * 同步的post请求
     * @param url
     * @param params
     * @return Response
     * @throws IOException
     */
    public  Response post(String url, Param... params) throws IOException
    {
        Request request = buildPostRequest(url, params);
        Response response = mOkHttpClient.newCall(request).execute();
        return response;
    }

    /**
     * 同步的post请求
     * @param url
     * @param params
     * @return String
     * @throws IOException
     */
    public  String postAsString(String url, Param... params) throws IOException
    {
        Request request = buildPostRequest(url, params);
        Response response = mOkHttpClient.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 异步的post请求
     * @param url
     * @param callback
     * @param params 数组类型
     */
    public void postAsyn(String url, final ResultCallback callback, Param... params)
    {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    /**
     *
     * @param url
     * @param params
     * @throws IOException
     */
    public  Response postAsyn(String url,HashMap<String,String> params)throws IOException
    {
        Param[] params1 = map2Params(params);
        Request request = buildPostRequest(url, params1);
        return  mOkHttpClient.newCall(request).execute();
    }

    /**
     * 异步的post请求
     * @param url
     * @param callback
     * @param params Map类型
     */
    public  void postAsyn(String url, final ResultCallback callback, Map<String, String> params)
    {
        Param[] paramsArr = map2Params(params);
        Request request = buildPostRequest(url, paramsArr);
        deliveryResult(callback, request);
    }

    //同步多文件上传，传参（数组）
    public  Response post(String url, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        return mOkHttpClient.newCall(request).execute();
    }
    //同步文件上传
    public  Response post(String url, File file, String fileKey) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        return mOkHttpClient.newCall(request).execute();
    }
    //同步文件上传，传参（数组）
    public  Response post(String url, File file, String fileKey, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        return mOkHttpClient.newCall(request).execute();
    }
    //异步多文件上传，带参数
    public  void postAsyn(String url, ResultCallback callback, File[] files, String[] fileKeys, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliveryResult(callback, request);
    }

    //异步单文件上传，不带参数
    public  void postAsyn(String url, ResultCallback callback, File file, String fileKey) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        deliveryResult(callback, request);
    }

    //异步单文件上传，带参数
    public  void postAsyn(String url, ResultCallback callback, File file, String fileKey, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        deliveryResult(callback, request);
    }
    //异步单文件上传，带参数
    public  void postAsyn(String url, ResultCallback callback, File file, String fileKey, HashMap<String,String> params) throws IOException
    {
        Request request;
        if(file != null){
            request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, changeParams(params));
        }else{
            request = buildMultipartFormRequest(url, null, new String[]{fileKey}, changeParams(params));
        }
        deliveryResult(callback, request);
    }

    public  void displayImage(final ImageView view, final String url, final int errorResId) throws IOException
    {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                setErrorResId(view, errorResId);
            }

            @Override
            public void onResponse(Call call, Response response)
            {
                InputStream is = null;
                try
                {
                    is = response.body().byteStream();
//                    ImageUtils.ImageSize actualImageSize = ImageUtils.getImageSize(is);
//                    ImageUtils.ImageSize imageViewSize = ImageUtils.getImageViewSize(view);
//                    int inSampleSize = ImageUtils.calculateInSampleSize(actualImageSize, imageViewSize);
                    try
                    {
                        is.reset();
                    } catch (IOException e) {
                        response = _getAsyn(url);
                        is = response.body().byteStream();
                    }

                    BitmapFactory.Options ops = new BitmapFactory.Options();
                    ops.inJustDecodeBounds = false;
//                    ops.inSampleSize = inSampleSize;
                    final Bitmap bm = BitmapFactory.decodeStream(is, null, ops);
                    mHandler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            view.setImageBitmap(bm);
                        }
                    });
                } catch (Exception e)
                {
                    setErrorResId(view, errorResId);

                } finally
                {
                    if (is != null) try
                    {
                        is.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public  void displayImage(final ImageView view, String url)
    {
        _displayImage(view, url, -1);
    }
    //异步下载
    public  void downloadAsyn(String url, String destDir, ResultCallback callback)
    {
        _downloadAsyn(url, destDir, callback);
    }

    //****************************


    private Request buildMultipartFormRequest(String url, File[] files,
                                              String[] fileKeys, Param[] params)
    {
        params = validateParam(params);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        for (Param param : params)
        {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(null, param.value));
        }
        if (files != null)
        {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++)
            {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                    "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                    fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    private String guessMimeType(String path)
    {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null)
        {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }


    private Param[] validateParam(Param[] params)
    {
        if (params == null)
            return new Param[0];
        else return params;
    }

    private Param[] map2Params(Map<String, String> params)
    {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
//        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : params.entrySet())
        {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;

    }

//    private static final String SESSION_KEY = "Set-Cookie";
//    private static final String mSessionKey = "JSESSIONID";
//
//    private Map<String, String> mSessions = new HashMap<String, String>();

    private void deliveryResult(final ResultCallback callback, Request request)
    {
        mOkHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                sendFailedStringCallback(call, e, callback);
            }

            @Override
            public void onResponse(Call call, Response response)
            {
                try
                {

                    final String string = response.body().string();
                    if (callback.mType == String.class)
                    {
                        sendSuccessResultCallback(string, callback);
                    } else
                    {
                        Object o = mGson.fromJson(string, callback.mType);
                        sendSuccessResultCallback(o, callback);
                    }


                } catch (IOException e)
                {
                    sendFailedStringCallback(call, e, callback);
                } catch (com.google.gson.JsonParseException e)//Json解析的错误
                {
                    sendFailedStringCallback(call, e, callback);
                }

            }
        });
    }

    private void sendFailedStringCallback(final Call request, final Exception e, final ResultCallback callback)
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (callback != null)
                    callback.onError(request, e);
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final ResultCallback callback)
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (callback != null)
                {
                    callback.onResponse(object);
                }
            }
        });
    }

    private Request buildPostRequest(String url, Param[] params)
    {
        if (params == null)
        {
            params = new Param[0];
        }
        FormBody.Builder builder = new FormBody.Builder();
        try {
            for (Param param : params)
            {
                builder.add(param.key, param.value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    /**
     * 泛型T
     * @param <T>
     */
    public static abstract class ResultCallback<T>
    {
        Type mType;

        public ResultCallback()
        {
            mType = getSuperclassTypeParameter(getClass());
        }
        /**
         * 得到泛型T的实际Type
         */
        static Type getSuperclassTypeParameter(Class<?> subclass)
        {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class)
            {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Call request, Exception e);

        public abstract void onResponse(T response);
    }

    public static class Param
    {

        public Param(String key, String value)
        {
            this.key = key;
            this.value = value;
        }

        String key;
        String value;
    }


}
