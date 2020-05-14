package com.souptik.maiti.goscaleassignment.paging

import androidx.paging.PageKeyedDataSource
import com.souptik.maiti.goscaleassignment.data.remote.response.Movie
import com.souptik.maiti.goscaleassignment.data.repository.MovieRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDataSource @Inject constructor(private val repo: MovieRepository, private val compositeDisposable: CompositeDisposable): PageKeyedDataSource<Int, Movie>() {
    companion object{
        val FIRST_PAGE: Int = 1
        val POSTS_PER_PAGE: Int = 10
    }

    var search:String = ""

    private var page = FIRST_PAGE

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        compositeDisposable.addAll(
            repo.getMoviesList(search, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.response == "True") {
                            callback.onResult(it.movieList, null, page + 1)
                        }
                    },
                    {

                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        compositeDisposable.addAll(
            repo.getMoviesList(search, params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.response == "True") {
                            if (it.totalResults > (params.key * POSTS_PER_PAGE)) {
                                callback.onResult(it.movieList, params.key + 1)
                            }
                        }
                    },
                    {

                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

}