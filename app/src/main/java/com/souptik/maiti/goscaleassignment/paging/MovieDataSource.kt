package com.souptik.maiti.goscaleassignment.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.souptik.maiti.goscaleassignment.data.remote.response.Movie
import com.souptik.maiti.goscaleassignment.data.repository.MovieRepository
import com.souptik.maiti.goscaleassignment.utils.Resource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDataSource @Inject constructor(private val repo: MovieRepository, private val compositeDisposable: CompositeDisposable): PageKeyedDataSource<Int, Movie>() {
    companion object{
        val FIRST_PAGE: Int = 1
        val POSTS_PER_PAGE: Int = 10
        val NETWORKSTATE = "NETWORKSTATE"
    }

    val networkState: MutableLiveData<Resource<String>> = MutableLiveData()

    var search:String = ""

    private var page = FIRST_PAGE

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {

        networkState.postValue(Resource.loading(NETWORKSTATE))

        compositeDisposable.addAll(
            repo.getMoviesList(search, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.response == "True") {
                            callback.onResult(it.movieList, null, page + 1)
                            networkState.postValue(Resource.success(NETWORKSTATE))
                        }
                    },
                    {
                        networkState.postValue(Resource.error(NETWORKSTATE, it.message.toString()))
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

        networkState.postValue(Resource.loading(NETWORKSTATE))

        compositeDisposable.addAll(
            repo.getMoviesList(search, params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.response == "True") {
                            if (it.totalResults > (params.key * POSTS_PER_PAGE)) {
                                callback.onResult(it.movieList, params.key + 1)
                                networkState.postValue(Resource.success(NETWORKSTATE))
                            }
                        }
                    },
                    {
                        networkState.postValue(Resource.error(NETWORKSTATE, it.message.toString()))
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

}