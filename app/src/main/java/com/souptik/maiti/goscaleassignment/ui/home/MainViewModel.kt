package com.souptik.maiti.goscaleassignment.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.souptik.maiti.goscaleassignment.data.repository.MovieRepository
import com.souptik.maiti.goscaleassignment.ui.base.BaseViewModel
import com.souptik.maiti.goscaleassignment.utils.RxSchedulerProviders
import io.reactivex.disposables.CompositeDisposable

class MainViewModel (schedulerProvider: RxSchedulerProviders,
                     compositeDisposable: CompositeDisposable,
                     private val movieRepository: MovieRepository)
    : BaseViewModel(schedulerProvider, compositeDisposable) {

    val imdbId: LiveData<String>
        get() = _imdbId

    private val _imdbId: MutableLiveData<String> = MutableLiveData()

    override fun onCreate() {
    }

    fun setMovieId(movieId: String){
        _imdbId.postValue(movieId)
    }
}