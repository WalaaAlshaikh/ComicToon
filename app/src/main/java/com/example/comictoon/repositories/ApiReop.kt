package com.example.comictoon.repositories

import android.content.Context
import com.example.comictoon.api.ComicApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

private const val BASE_URL = "https://comicvine.gamespot.com/api/"

class ApiReop {

    private val retrofitService =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

    private val apiRetrofit = retrofitService.create(ComicApi::class.java)
    suspend fun getComics() = apiRetrofit.getComic()

    suspend fun getDetailComic()=apiRetrofit.getDetailComic()

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