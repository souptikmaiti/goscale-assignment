package com.souptik.maiti.goscaleassignment.di.components


import com.souptik.maiti.goscaleassignment.di.FragmentScope
import com.souptik.maiti.goscaleassignment.di.modules.FragmentModule
import com.souptik.maiti.goscaleassignment.ui.detail.DetailsFragment
import com.souptik.maiti.goscaleassignment.ui.home.HomeFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {

    fun inject(fragment: HomeFragment)
    fun inject(fragment: DetailsFragment)
}