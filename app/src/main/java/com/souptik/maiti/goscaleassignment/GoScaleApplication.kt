package com.souptik.maiti.goscaleassignment

import android.app.Application
import com.souptik.maiti.goscaleassignment.di.components.ApplicationComponent
import com.souptik.maiti.goscaleassignment.di.components.DaggerApplicationComponent
import com.souptik.maiti.goscaleassignment.di.modules.ApplicationModule

class GoScaleApplication: Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}