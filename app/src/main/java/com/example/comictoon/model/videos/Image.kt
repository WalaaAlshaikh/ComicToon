package com.example.comictoon.model.videos


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("icon_url")
    val iconUrl: String,
    @SerializedName("image_tags")
    val imageTags: String,
    @SerializedName("medium_url")
    val mediumUrl: String,
    @SerializedName("original_url")
    val originalUrl: String,
    @SerializedName("screen_large_url")
    val screenLargeUrl: String,
    @SerializedName("screen_url")
    val screenUrl: String,
    @SerializedName("small_url")
    val smallUrl: String,
    @SerializedName("super_url")
    val superUrl: String,
    @SerializedName("thumb_url")
    val thumbUrl: String,
    @SerializedName("tiny_url")
    val tinyUrl: String
)