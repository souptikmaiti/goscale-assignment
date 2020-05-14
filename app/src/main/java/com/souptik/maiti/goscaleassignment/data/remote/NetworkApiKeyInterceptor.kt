package com.souptik.maiti.goscaleassignment.data.remote

import android.content.Context
import android.net.ConnectivityManager
import com.souptik.maiti.goscaleassignment.di.ApplicationContext
import com.souptik.maiti.goscaleassignment.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class NetworkApiKeyInterceptor (@ApplicationContext private val context: Context, private val apiKey: String): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkAvailable()){
            throw NoInternetException("please connect to the internet")
        }

        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("apikey", apiKey)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }

    private fun isNetworkAvailable():Boolean{
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also{
            return it !=null && it.isConnected
        }
    }
}
