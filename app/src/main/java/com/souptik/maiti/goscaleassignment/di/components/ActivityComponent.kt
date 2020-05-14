package com.souptik.maiti.goscaleassignment.di.components


import com.souptik.maiti.goscaleassignment.di.ActivityScope
import com.souptik.maiti.goscaleassignment.di.modules.ActivityModule
import com.souptik.maiti.goscaleassignment.ui.home.MainActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)
}