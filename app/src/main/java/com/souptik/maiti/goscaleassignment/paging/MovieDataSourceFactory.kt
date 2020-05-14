package com.souptik.maiti.goscaleassignment.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.souptik.maiti.goscaleassignment.data.remote.response.Movie
import com.souptik.maiti.goscaleassignment.data.repository.MovieRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class MovieDataSourceFactory @Inject constructor(private val movieRepository: MovieRepository, private val compositeDisposable: CompositeDisposable): DataSource.Factory<Int, Movie>() {

    val movieLiveData = MutableLiveData<MovieDataSource>()

    var search: String = ""

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(movieRepository, compositeDisposable)
        movieDataSource.search = search

        movieLiveData.postValue(movieDataSource)
        return movieDataSource
    }

}