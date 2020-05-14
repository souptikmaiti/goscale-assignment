package com.souptik.maiti.goscaleassignment.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie(

    @Expose
    @SerializedName("Poster")
    val poster: String,

    @Expose
    @SerializedName("Title")
    val title: String,

    @Expose
    @SerializedName("Type")
    val type: String,

    @Expose
    @SerializedName("Year")
    val year: String,

    @Expose
    @SerializedName("imdbID")
    val imdbID: String
)