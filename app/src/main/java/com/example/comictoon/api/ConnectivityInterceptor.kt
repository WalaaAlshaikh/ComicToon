package com.example.comictoon.api

import android.os.Build
import android.view.Window
import androidx.annotation.RequiresApi
import com.example.comictoon.R
import com.google.android.material.snackbar.Snackbar
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

// ConnectivityInterceptor to check for internet access

class ConnectivityInterceptor():Interceptor{
    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!WifiService.instance.isOnline()) {
            var text ="No internet connection \nCheck your internet then swipe down to refresh"
            text.

            throw IOException(text)
        } else {
            return chain.proceed(chain.request())
        }
    }
    fun snack(view: Int){


    }
}