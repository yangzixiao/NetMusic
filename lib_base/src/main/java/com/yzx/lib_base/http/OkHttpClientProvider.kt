package com.yzx.lib_base.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OkHttpClientProvider(val loggingInterceptor: HttpLoggingInterceptor) {

    fun  getOkHttpClient():OkHttpClient  {
      return  OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor).build()
    }
}