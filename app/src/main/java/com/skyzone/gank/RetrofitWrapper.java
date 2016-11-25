package com.skyzone.gank;

import com.elvishew.xlog.XLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Skyzone on 11/22/2016.
 */
public enum RetrofitWrapper {

    Instance;

    private GankApi mGankApi;

    private OkHttpClient client;
    private Retrofit retrofit;
    private Gson mGson;

    protected static final int time_out = 10;

    public static final int page_size = 50;


    private RetrofitWrapper() {
        XLog.d("RetrofitWrapper Constructor");
        initClient();
        mGson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .serializeNulls()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_SERVER_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mGankApi = retrofit.create(GankApi.class);
    }

    private void initClient() {

        //打印网络请求日志
        HttpLoggingInterceptor mLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);

        client = new OkHttpClient.Builder().connectTimeout(time_out, TimeUnit.SECONDS)
                .writeTimeout(time_out, TimeUnit.SECONDS)
                .readTimeout(time_out, TimeUnit.SECONDS)
                .addInterceptor(mLoggingInterceptor)
                .build();
    }

    public GankApi getDemoApi() {
        return mGankApi;
    }
}
