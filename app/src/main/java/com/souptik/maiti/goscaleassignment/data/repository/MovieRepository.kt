package com.souptik.maiti.goscaleassignment.data.repository

import com.souptik.maiti.goscaleassignment.data.remote.response.MoviesResponse
import com.souptik.maiti.goscaleassignment.data.remote.NetworkService
import com.souptik.maiti.goscaleassignment.data.remote.response.MovieDetails
import io.reactivex.Single
import javax.inject.Inject

class MovieRepository @Inject constructor(private val networkService: NetworkService) {

    fun getMoviesList(search: String, page: Int): Single<MoviesResponse>{
        return networkService.getMoviesList(search, page)
    }

    fun getMovieDetails(imdbId: String): Single<MovieDetails>{
        return networkService.getMovieDetails(imdbId)
    }
}