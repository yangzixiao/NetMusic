package com.yzx.lib_base.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yzx.lib_base.BuildConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author yzx
 * @date 2018/8/21
 * Description Retrofit工具类
 */
public class RetrofitHelper {

    public static final String TAG = "RetrofitHelper";
    /**
     * 链接超时
     */
    private static final int TIME_OUT = 60;

    private Retrofit retrofit;
    private boolean inited;

    private Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

    private Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson));
    private Map<String, Retrofit> retrofitMap = new HashMap<>();

    private RetrofitHelper() {

    }


    public static RetrofitHelper getInstance() {
        return Inner.retrofitHelper;
    }

    private static class Inner {
        private static RetrofitHelper retrofitHelper = new RetrofitHelper();
    }

    /**
     * 初始化
     */
    public void init(String baseUrl) {

        //如果是debug设置拦截器
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder
                    .addInterceptor(loggingInterceptor);
        }

        //链接超时
        okHttpClientBuilder
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS);


        retrofit = retrofitBuilder
                .client(okHttpClientBuilder.build())
                .baseUrl(baseUrl)
//                .addCallAdapterFactory(CoroutineCallAdapterFactory.create())
                .build();

        if (!retrofitMap.containsKey(baseUrl)) {
            retrofitMap.put(baseUrl, retrofit);
        }
        inited = true;
    }


    /**
     * 获取Retrofit实例
     *
     * @return
     */
    public Retrofit getRetrofit() {

        if (!inited || retrofit == null) {
            throw new IllegalStateException("请先初始化RetrofitHelper");
        }
        return retrofit;
    }


    /**
     * 设置baseUrl
     *
     * @param baseUrl
     */
    public Retrofit setBaseUrl(String baseUrl) {

        if (retrofitMap.containsKey(baseUrl)) {
            return retrofitMap.get(baseUrl);
        } else {
            retrofit = retrofitBuilder
                    .baseUrl(baseUrl)
                    .client(okHttpClientBuilder.build())
                    .build();
            retrofitMap.put(baseUrl, retrofit);
            return retrofit;
        }
    }
}
