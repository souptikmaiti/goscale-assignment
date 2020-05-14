package com.souptik.maiti.goscaleassignment.di.modules

import android.app.Application
import android.content.Context
import com.souptik.maiti.goscaleassignment.BuildConfig
import com.souptik.maiti.goscaleassignment.GoScaleApplication
import com.souptik.maiti.goscaleassignment.data.local.database.DatabaseMovies
import com.souptik.maiti.goscaleassignment.data.remote.NetworkApiKeyInterceptor
import com.souptik.maiti.goscaleassignment.data.remote.NetworkService
import com.souptik.maiti.goscaleassignment.data.remote.Networking
import com.souptik.maiti.goscaleassignment.data.repository.MovieRepository
import com.souptik.maiti.goscaleassignment.di.ApplicationContext
import com.souptik.maiti.goscaleassignment.paging.MovieDataSource
import com.souptik.maiti.goscaleassignment.utils.RxSchedulerProviders
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: GoScaleApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideRxSchedulerProviders(): RxSchedulerProviders = RxSchedulerProviders()

    @Provides
    fun provideNetworkApiKeyInterceptor(): NetworkApiKeyInterceptor = NetworkApiKeyInterceptor(application, BuildConfig.API_KEY)

    @Provides
    @Singleton
    fun provideNetworkService(networkApiKeyInterceptor: NetworkApiKeyInterceptor): NetworkService =
        Networking.create(
            baseUrl = BuildConfig.BASE_URL,
            cacheDir = application.cacheDir,
            cacheSize = 10 * 1024 * 1024, // 10MB
            interceptor = networkApiKeyInterceptor
        )

    @Provides
    @Singleton
    fun provideDatabase(): DatabaseMovies =
        DatabaseMovies(application)

}