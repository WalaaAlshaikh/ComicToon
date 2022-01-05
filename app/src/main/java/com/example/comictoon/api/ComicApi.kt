package com.example.comictoon.api

import com.example.comictoon.model.comic.ComicModel
import com.example.comictoon.model.comic.Result
import com.example.comictoon.model.videos.VideosModel
import retrofit2.Response
import retrofit2.http.GET

interface ComicApi {
    // getting the comics with their details
    @GET("volumes/?api_key=9c6a8bae62f936621bc6e2b0e4302011673b3122&format=json")
    suspend fun getComic(): Response<ComicModel>


    // getting videos related to comics in general (their famous characters, issues, games.. etc)
    @GET("videos/?api_key=9c6a8bae62f936621bc6e2b0e4302011673b3122&format=json")
    suspend fun getVideos():Response<VideosModel>
}