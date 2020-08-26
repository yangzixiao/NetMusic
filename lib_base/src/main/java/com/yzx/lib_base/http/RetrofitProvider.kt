package com.yzx.lib_base.http

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider(private val gson: Gson, private val okHttpClientProvider: OkHttpClientProvider) {


    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://118.24.63.15:1020/")
            .client(okHttpClientProvider.getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

}