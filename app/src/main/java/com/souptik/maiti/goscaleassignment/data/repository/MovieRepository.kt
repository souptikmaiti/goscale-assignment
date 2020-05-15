package com.souptik.maiti.goscaleassignment.data.repository

import com.souptik.maiti.goscaleassignment.data.local.dao.MoviesDao
import com.souptik.maiti.goscaleassignment.data.local.database.DatabaseMovies
import com.souptik.maiti.goscaleassignment.data.local.entities.MovieBookmark
import com.souptik.maiti.goscaleassignment.data.remote.response.MoviesResponse
import com.souptik.maiti.goscaleassignment.data.remote.NetworkService
import com.souptik.maiti.goscaleassignment.data.remote.response.MovieDetails
import io.reactivex.Single
import javax.inject.Inject

class MovieRepository @Inject constructor(private val networkService: NetworkService, private val db: DatabaseMovies) {

    private val movieDao: MoviesDao = db.moviesDao()

    fun getMoviesList(search: String, page: Int): Single<MoviesResponse>{
        return networkService.getMoviesList(search, page)
    }

    fun getMovieDetails(imdbId: String): Single<MovieDetails>{
        return networkService.getMovieDetails(imdbId)
    }

    suspend fun saveBookmarkedMovie(movieBookmark: MovieBookmark) = movieDao.insertMovie(movieBookmark)

    suspend fun deleteBookmarkedMovie(movieBookmark: MovieBookmark) = movieDao.deleteMovie(movieBookmark)

    suspend fun deleteBookmarkedMovieById(movieId: String) = movieDao.deleteRecord(movieId)

    suspend fun getAllMovieIds() = movieDao.getAllIds()

    fun getAllBookmarkedMovies() = movieDao.getAllMovies()

}