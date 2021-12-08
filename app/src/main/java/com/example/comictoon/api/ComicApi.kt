package com.example.comictoon.api

import com.example.comictoon.model.comic.ComicModel
import retrofit2.Response
import retrofit2.http.GET

interface ComicApi {
    @GET("volumes/?api_key=9c6a8bae62f936621bc6e2b0e4302011673b3122&format=json")
    suspend fun getComic(): Response<ComicModel>
}