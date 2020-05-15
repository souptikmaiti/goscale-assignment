package com.souptik.maiti.goscaleassignment.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.souptik.maiti.goscaleassignment.data.local.entities.MovieBookmark
import com.souptik.maiti.goscaleassignment.data.remote.response.MovieDetails
import com.souptik.maiti.goscaleassignment.data.repository.MovieRepository
import com.souptik.maiti.goscaleassignment.ui.base.BaseViewModel
import com.souptik.maiti.goscaleassignment.utils.Resource
import com.souptik.maiti.goscaleassignment.utils.RxSchedulerProviders
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*

class DetailsViewModel(schedulerProvider: RxSchedulerProviders,
                       compositeDisposable: CompositeDisposable,
                       private val movieRepository: MovieRepository) : BaseViewModel(schedulerProvider, compositeDisposable) {

    val movieDetails: LiveData<Resource<MovieDetails>>
        get() = _movieDetails

    private val _movieDetails: MutableLiveData<Resource<MovieDetails>> = MutableLiveData()

    val bookmarkAdded: LiveData<Boolean>
        get() = _bookmarkAdded

    val bookmarkDeleted: LiveData<Boolean>
        get() = _bookmarkDeleted

    private var _bookmarkAdded: MutableLiveData<Boolean> = MutableLiveData()
    private var _bookmarkDeleted: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCreate() {

    }

    fun getMovieDetails(movieId: String){
        _movieDetails.postValue(Resource.loading(null))
        compositeDisposable.addAll(
            movieRepository.getMovieDetails(movieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    {
                        _movieDetails.postValue(Resource.success(it))
                    },
                    {
                        _movieDetails.postValue(Resource.error(null, it.message.toString()))
                    }
                )
        )
    }

    fun toggleBookmark(movieBookmark: MovieBookmark){
        // Coroutine that will be canceled when the ViewModel is cleared.
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                var idList = movieRepository.getAllMovieIds()
                if(!idList.isNullOrEmpty() && idList.contains(movieBookmark.imdbID)){
                    movieRepository.deleteBookmarkedMovieById(movieBookmark.imdbID)
                    _bookmarkAdded.postValue(false)
                    _bookmarkDeleted.postValue(true)
                }else {
                    movieRepository.saveBookmarkedMovie(movieBookmark)
                    _bookmarkAdded.postValue(true)
                    _bookmarkDeleted.postValue(false)
                }
            }

        }
    }

    fun getAllBookmarkedMovies():LiveData<List<MovieBookmark>> = movieRepository.getAllBookmarkedMovies()
}