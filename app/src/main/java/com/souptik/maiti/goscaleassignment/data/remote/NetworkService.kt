package com.souptik.maiti.goscaleassignment.data.remote

import com.souptik.maiti.goscaleassignment.data.remote.response.MovieDetails
import com.souptik.maiti.goscaleassignment.data.remote.response.MoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET(".")
    fun getMoviesList(@Query("s") search: String,
                      @Query("page") page: Int): Single<MoviesResponse>

    @GET(".")
    fun getMovieDetails(@Query("i") imdbId: String): Single<MovieDetails>
}