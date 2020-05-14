package com.souptik.maiti.goscaleassignment.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.souptik.maiti.goscaleassignment.data.remote.response.Movie
import com.souptik.maiti.goscaleassignment.paging.MoviePagedListRepository
import com.souptik.maiti.goscaleassignment.ui.base.BaseViewModel
import com.souptik.maiti.goscaleassignment.utils.RxSchedulerProviders
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(schedulerProvider: RxSchedulerProviders,
                    compositeDisposable: CompositeDisposable,
                    private val moviePagedListRepository: MoviePagedListRepository)
    : BaseViewModel(schedulerProvider, compositeDisposable){

    var filterText = MutableLiveData<String>()

    var moviePagedList: LiveData<PagedList<Movie>>  = Transformations.switchMap(filterText, ::processData)

    fun processData(str: String):LiveData<PagedList<Movie>>{
        return moviePagedListRepository.fetchMoviePagedList(str)
    }


    override fun onCreate() {

    }

    fun isListEmpty(): Boolean{
        return moviePagedList.value?.isEmpty() ?: true
    }

    fun setSearchString(str: String){
        filterText.postValue(str)
    }
}