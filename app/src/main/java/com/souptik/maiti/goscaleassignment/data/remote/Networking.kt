package com.souptik.maiti.goscaleassignment.data.remote

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object Networking {

    private const val NETWORK_TIME_OUT: Long = 60

    fun create(cacheDir: File, cacheSize: Long, baseUrl: String, interceptor: NetworkApiKeyInterceptor) : NetworkService {
        val client = OkHttpClient.Builder()
            .cache(Cache(cacheDir, cacheSize))
            .readTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            //.addInterceptor(HttpLoggingInterceptor()
            //    .apply {
            //        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            //        else HttpLoggingInterceptor.Level.NONE
            //    })
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(NetworkService::class.java)
    }
}