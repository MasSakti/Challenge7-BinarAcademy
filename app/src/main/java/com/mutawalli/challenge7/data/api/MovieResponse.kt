package com.mutawalli.challenge7.data.api

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page") val page: Int?,
    @SerializedName("results") val results: List<Movies>?,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("total_results") val totalResults: Int?
)