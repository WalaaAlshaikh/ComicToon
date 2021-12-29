package com.example.comictoon.model.videos


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("api_detail_url")
    val apiDetailUrl: String,
    @SerializedName("crew")
    val crew: String,
    @SerializedName("deck")
    val deck: String,
    @SerializedName("embed_player")
    val embedPlayer: String,
    @SerializedName("guid")
    val guid: String,
    @SerializedName("high_url")
    val highUrl: String,
    @SerializedName("hosts")
    val hosts: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: Image,
    @SerializedName("length_seconds")
    val lengthSeconds: Int,
    @SerializedName("low_url")
    val lowUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("premium")
    val premium: Boolean,
    @SerializedName("publish_date")
    val publishDate: String,
    @SerializedName("saved_time")
    val savedTime: Any,
    @SerializedName("site_detail_url")
    val siteDetailUrl: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("user")
    val user: String,
    @SerializedName("video_categories")
    val videoCategories: List<VideoCategory>,
    @SerializedName("video_show")
    val videoShow: Any,
    @SerializedName("video_type")
    val videoType: String,
    @SerializedName("youtube_id")
    val youtubeId: String
)