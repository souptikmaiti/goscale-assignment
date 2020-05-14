package com.souptik.maiti.goscaleassignment.data.remote.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @Expose
    @SerializedName("Actors")
    val actors: String,

    @Expose
    @SerializedName("Awards")
    val awards: String,

    @Expose
    @SerializedName("BoxOffice")
    val boxOffice: String,

    @Expose
    @SerializedName("Country")
    val country: String,

    @Expose
    @SerializedName("DVD")
    val dVD: String,

    @Expose
    @SerializedName("Director")
    val director: String,

    @Expose
    @SerializedName("Genre")
    val genre: String,

    @Expose
    @SerializedName("imdbID")
    val imdbID: String,

    @Expose
    @SerializedName("imdbRating")
    val imdbRating: String,

    @Expose
    @SerializedName("imdbVotes")
    val imdbVotes: String,

    @Expose
    @SerializedName("Language")
    val language: String,

    @Expose
    @SerializedName("Metascore")
    val metascore: String,

    @Expose
    @SerializedName("Plot")
    val plot: String,

    @Expose
    @SerializedName("Poster")
    val poster: String,

    @Expose
    @SerializedName("Production")
    val production: String,

    @Expose
    @SerializedName("Rated")
    val rated: String,

    @Expose
    @SerializedName("Released")
    val released: String,

    @Expose
    @SerializedName("Response")
    val response: String,

    @Expose
    @SerializedName("Runtime")
    val runtime: String,

    @Expose
    @SerializedName("Title")
    val title: String,

    @Expose
    @SerializedName("Type")
    val type: String,

    @Expose
    @SerializedName("Website")
    val website: String,

    @Expose
    @SerializedName("Writer")
    val writer: String,

    @Expose
    @SerializedName("Year")
    val year: String
)