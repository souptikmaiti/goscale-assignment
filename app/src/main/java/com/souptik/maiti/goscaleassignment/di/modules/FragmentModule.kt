package com.souptik.maiti.goscaleassignment.di.modules

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.souptik.maiti.goscaleassignment.data.local.entities.MovieBookmark
import com.souptik.maiti.goscaleassignment.data.remote.response.Movie
import com.souptik.maiti.goscaleassignment.data.repository.MovieRepository
import com.souptik.maiti.goscaleassignment.paging.MoviePagedListRepository
import com.souptik.maiti.goscaleassignment.ui.adapter.MovieBookmarkAdapter
import com.souptik.maiti.goscaleassignment.ui.adapter.MoviePagedListAdapter
import com.souptik.maiti.goscaleassignment.ui.base.BaseFragment
import com.souptik.maiti.goscaleassignment.ui.detail.DetailsViewModel
import com.souptik.maiti.goscaleassignment.ui.home.HomeViewModel
import com.souptik.maiti.goscaleassignment.utils.RxSchedulerProviders
import com.souptik.maiti.goscaleassignment.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Named

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @Provides
    fun provideHomeViewModel(
        schedulerProvider: RxSchedulerProviders,
        compositeDisposable: CompositeDisposable,
        moviePagedListRepository: MoviePagedListRepository,
        movieRepository: MovieRepository
    ): HomeViewModel =
        ViewModelProviders.of(fragment,
            ViewModelProviderFactory(HomeViewModel::class) {
                HomeViewModel(schedulerProvider, compositeDisposable, moviePagedListRepository, movieRepository)
            }
        ).get(HomeViewModel::class.java)

    @Provides
    fun provideDetailsViewModel(
        schedulerProvider: RxSchedulerProviders,
        compositeDisposable: CompositeDisposable,
        movieRepository: MovieRepository
    ): DetailsViewModel =
        ViewModelProviders.of(fragment,
            ViewModelProviderFactory(DetailsViewModel::class) {
                DetailsViewModel(schedulerProvider, compositeDisposable, movieRepository)
            }
        ).get(DetailsViewModel::class.java)

    @Provides
    fun provideEmptyArrayList(): ArrayList<Movie>  = ArrayList()

    @Provides
    fun provideMoviePagedListAdapter(): MoviePagedListAdapter = MoviePagedListAdapter()

    @Provides
    fun provideBookmarkAdapter(): MovieBookmarkAdapter = MovieBookmarkAdapter(ArrayList<MovieBookmark>())

    @Provides
    @Named("Vertical")
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(fragment.context)

    @Provides
    @Named("Horizontal")
    fun provideHorizontalLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(fragment.context, LinearLayoutManager.HORIZONTAL, false)
}