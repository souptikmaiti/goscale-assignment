package com.souptik.maiti.goscaleassignment.di.components

import android.app.Application
import android.content.Context
import com.souptik.maiti.goscaleassignment.GoScaleApplication
import com.souptik.maiti.goscaleassignment.data.repository.MovieRepository
import com.souptik.maiti.goscaleassignment.di.ApplicationContext
import com.souptik.maiti.goscaleassignment.di.modules.ApplicationModule
import com.souptik.maiti.goscaleassignment.utils.RxSchedulerProviders
import com.souptik.maiti.goscaleassignment.data.remote.NetworkService
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(goScaleApplication: GoScaleApplication)

    fun getApplication(): Application

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getMovieRepository(): MovieRepository

    fun getRxSchedulerProviders(): RxSchedulerProviders

    fun getCompositeDisposable(): CompositeDisposable
}