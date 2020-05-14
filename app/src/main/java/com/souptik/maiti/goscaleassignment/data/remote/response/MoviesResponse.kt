package com.souptik.maiti.goscaleassignment.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MoviesResponse(

    @Expose
    @SerializedName("Response")
    val response: String,

    @Expose
    @SerializedName("Search")
    val movieList: List<Movie>,

    @Expose
    @SerializedName("totalResults")
    val totalResults: Int
)