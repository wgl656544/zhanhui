package com.zyrc.exhibit.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class OKHttpSingle {
    private static OKHttpSingle okHttpSingle;
    private OkHttpClient okHttpClient;

    public static OKHttpSingle getInstance() {
        synchronized (OKHttpSingle.class) {
            if (okHttpSingle == null) {
                okHttpSingle = new OKHttpSingle();
            }
        }
        return okHttpSingle;
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).build();
            return okHttpClient;
        }
        return okHttpClient;
    }
}
