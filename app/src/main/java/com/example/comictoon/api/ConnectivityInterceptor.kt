package com.example.comictoon.api

import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

// ConnectivityInterceptor to check for internet access

class ConnectivityInterceptor:Interceptor {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!WifiService.instance.isOnline()) {
            throw IOException("No internet connection\\n Check your internet then swipe down to refresh\"")
        } else {
            return chain.proceed(chain.request())
        }
    }
}