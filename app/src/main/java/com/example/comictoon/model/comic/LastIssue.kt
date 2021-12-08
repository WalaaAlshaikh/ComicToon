package com.example.comictoon.model.comic


import com.google.gson.annotations.SerializedName

data class LastIssue(
    @SerializedName("api_detail_url")
    val apiDetailUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("issue_number")
    val issueNumber: String,
    @SerializedName("name")
    val name: String
)