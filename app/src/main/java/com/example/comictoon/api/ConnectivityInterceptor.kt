package com.example.comictoon.api

import android.os.Build
import android.view.Window
import androidx.annotation.RequiresApi
import com.example.comictoon.R
import com.google.android.material.snackbar.Snackbar
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/** ConnectivityInterceptor to check for internet access
 * and override the method intercept(Chain) to check internet connection availability inside it.*/

class ConnectivityInterceptor():Interceptor{
    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!WifiService.instance.isOnline()) {
            throw IOException("No internet connection, swipe down to refresh")
        } else {
            return chain.proceed(chain.request())
        }
    }

}