package com.yzx.lib_base.di

import com.google.gson.GsonBuilder
import com.yzx.lib_base.http.OkHttpClientProvider
import com.yzx.lib_base.http.RetrofitProvider
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

@JvmField
val networkModule = module {
    single {
        GsonBuilder().setLenient().create()
    }
    single {
        HttpLoggingInterceptor()
    }
    single {
        OkHttpClientProvider(get())
    }
    single {
        RetrofitProvider(get(), get())
    }
}
