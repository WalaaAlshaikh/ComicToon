package com.example.comictoon.model.comic


import com.google.gson.annotations.SerializedName

data class Publisher(
    @SerializedName("api_detail_url")
    val apiDetailUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)