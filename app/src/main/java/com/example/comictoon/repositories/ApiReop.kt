package com.example.comictoon.repositories

import android.content.Context
import com.example.comictoon.api.ComicApi
import com.example.comictoon.api.ConnectivityInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

private const val BASE_URL = "https://comicvine.gamespot.com/api/"

class ApiReop {
    // Define the retrofit clients
    private val okHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(ConnectivityInterceptor())
            .build()


    private val retrofitService =
        Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
            .build()

    private val apiRetrofit = retrofitService.create(ComicApi::class.java)

    suspend fun getComics() = apiRetrofit.getComic()
    suspend fun getVideos()=apiRetrofit.getVideos()



// create a singleton object
    companion object {
        private var instance: ApiReop? = null

        fun init(context: Context) {
            if (instance == null)
                instance = ApiReop()
        }


        fun get(): ApiReop {
            return instance ?: throw Exception("ApiServiceRepositories must be initialized ")
        }

    }

}