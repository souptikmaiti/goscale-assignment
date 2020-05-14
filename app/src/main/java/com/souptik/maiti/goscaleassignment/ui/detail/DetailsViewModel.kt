package com.souptik.maiti.goscaleassignment.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.souptik.maiti.goscaleassignment.data.local.entities.MovieBookmark
import com.souptik.maiti.goscaleassignment.data.remote.response.MovieDetails
import com.souptik.maiti.goscaleassignment.data.repository.MovieRepository
import com.souptik.maiti.goscaleassignment.ui.base.BaseViewModel
import com.souptik.maiti.goscaleassignment.utils.RxSchedulerProviders
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(schedulerProvider: RxSchedulerProviders,
                       compositeDisposable: CompositeDisposable,
                       private val movieRepository: MovieRepository) : BaseViewModel(schedulerProvider, compositeDisposable) {

    val movieDetails: LiveData<MovieDetails>
        get() = _movieDetails

    private val _movieDetails: MutableLiveData<MovieDetails> = MutableLiveData()

    override fun onCreate() {

    }

    fun getMovieDetails(movieId: String){
        compositeDisposable.addAll(
            movieRepository.getMovieDetails(movieId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    {
                        _movieDetails.postValue(it)
                    },
                    {

                    }
                )
        )
    }

    fun saveBookmarkedMovie(movieBookmark: MovieBookmark){
        // Coroutine that will be canceled when the ViewModel is cleared.
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                movieRepository.saveBookmarkedMovie(movieBookmark)
            }

        }
    }

    fun deleteBookmarkedMovie(movieBookmark: MovieBookmark){
        // Coroutine that will be canceled when the ViewModel is cleared.
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                movieRepository.deleteBookmarkedMovie(movieBookmark)
            }
        }
    }

    fun getAllBookmarkedMovies():LiveData<List<MovieBookmark>> = movieRepository.getAllBookmarkedMovies()
}