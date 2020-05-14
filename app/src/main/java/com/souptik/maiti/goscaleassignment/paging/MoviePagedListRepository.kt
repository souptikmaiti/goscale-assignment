package com.souptik.maiti.goscaleassignment.paging

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.souptik.maiti.goscaleassignment.data.remote.response.Movie
import com.souptik.maiti.goscaleassignment.data.repository.MovieRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MoviePagedListRepository @Inject constructor(private val movieRepository: MovieRepository, private val compositeDisposable: CompositeDisposable) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchMoviePagedList(search: String): LiveData<PagedList<Movie>>{
        movieDataSourceFactory = MovieDataSourceFactory(movieRepository, compositeDisposable)
        movieDataSourceFactory.search = search

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MovieDataSource.POSTS_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config)
            .build()

        return moviePagedList
    }
}