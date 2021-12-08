package com.example.comictoon.model.comic


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("aliases")
    val aliases: Any,
    @SerializedName("api_detail_url")
    val apiDetailUrl: String,
    @SerializedName("count_of_issues")
    val countOfIssues: Int,
    @SerializedName("date_added")
    val dateAdded: String,
    @SerializedName("date_last_updated")
    val dateLastUpdated: String,
    @SerializedName("deck")
    val deck: Any,
    @SerializedName("description")
    val description: String,
    @SerializedName("first_issue")
    val firstIssue: FirstIssue,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: Image,
    @SerializedName("last_issue")
    val lastIssue: LastIssue,
    @SerializedName("name")
    val name: String,
    @SerializedName("publisher")
    val publisher: Publisher,
    @SerializedName("site_detail_url")
    val siteDetailUrl: String,
    @SerializedName("start_year")
    val startYear: String
)