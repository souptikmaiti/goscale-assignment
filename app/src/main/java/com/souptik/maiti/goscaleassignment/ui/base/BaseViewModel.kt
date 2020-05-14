package com.souptik.maiti.goscaleassignment.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.souptik.maiti.goscaleassignment.utils.RxSchedulerProviders
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel (
    protected val schedulerProvider: RxSchedulerProviders,
    protected val compositeDisposable: CompositeDisposable
) : ViewModel() {

    val messageString: MutableLiveData<String> = MutableLiveData()

    abstract fun onCreate()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}