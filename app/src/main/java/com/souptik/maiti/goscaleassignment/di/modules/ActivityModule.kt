package com.souptik.maiti.goscaleassignment.di.modules

import androidx.lifecycle.ViewModelProviders
import com.souptik.maiti.goscaleassignment.data.repository.MovieRepository
import com.souptik.maiti.goscaleassignment.ui.base.BaseActivity
import com.souptik.maiti.goscaleassignment.ui.home.MainViewModel
import com.souptik.maiti.goscaleassignment.utils.RxSchedulerProviders
import com.souptik.maiti.goscaleassignment.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    fun provideMainViewModel(
        schedulerProvider: RxSchedulerProviders,
        compositeDisposable: CompositeDisposable,
        movieRepository: MovieRepository
    ): MainViewModel =
        ViewModelProviders.of(activity,
            ViewModelProviderFactory(MainViewModel::class) {
                MainViewModel(schedulerProvider, compositeDisposable, movieRepository)
            }
        ).get(MainViewModel::class.java)
}