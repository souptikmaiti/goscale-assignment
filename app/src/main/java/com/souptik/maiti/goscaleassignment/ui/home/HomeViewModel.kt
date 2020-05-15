package com.souptik.maiti.goscaleassignment.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.souptik.maiti.goscaleassignment.data.local.entities.MovieBookmark
import com.souptik.maiti.goscaleassignment.data.remote.response.Movie
import com.souptik.maiti.goscaleassignment.data.repository.MovieRepository
import com.souptik.maiti.goscaleassignment.paging.MoviePagedListRepository
import com.souptik.maiti.goscaleassignment.ui.base.BaseViewModel
import com.souptik.maiti.goscaleassignment.utils.RxSchedulerProviders
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(schedulerProvider: RxSchedulerProviders,
                    compositeDisposable: CompositeDisposable,
                    private val moviePagedListRepository: MoviePagedListRepository,
                    private val movieRepository: MovieRepository)
    : BaseViewModel(schedulerProvider, compositeDisposable){

    var filterText = MutableLiveData<String>()

    var moviePagedList: LiveData<PagedList<Movie>>  = Transformations.switchMap(filterText, ::processData)

    val bookmarkAdded: LiveData<Boolean>
        get() = _bookmarkAdded

    val bookmarkDeleted: LiveData<Boolean>
        get() = _bookmarkDeleted

    private var _bookmarkAdded: MutableLiveData<Boolean> = MutableLiveData()
    private var _bookmarkDeleted: MutableLiveData<Boolean> = MutableLiveData()

    fun processData(str: String):LiveData<PagedList<Movie>>{
        return moviePagedListRepository.fetchMoviePagedList(str)
    }


    override fun onCreate() {

    }

    fun setSearchString(str: String){
        filterText.postValue(str)
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