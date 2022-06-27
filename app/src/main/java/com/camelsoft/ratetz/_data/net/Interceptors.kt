package com.camelsoft.ratetz._data.net

import com.camelsoft.ratetz.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun OkHttpClient.Builder.addLoggingInterceptor(): OkHttpClient.Builder {
    if (BuildConfig.DEBUG) {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        addInterceptor(loggingInterceptor)
    }
    return this
}