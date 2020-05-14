package com.souptik.maiti.goscaleassignment.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.souptik.maiti.goscaleassignment.R
import com.souptik.maiti.goscaleassignment.di.components.ActivityComponent
import com.souptik.maiti.goscaleassignment.ui.base.BaseActivity
import com.souptik.maiti.goscaleassignment.ui.detail.DetailsFragment

class MainActivity : BaseActivity<MainViewModel>() {

    companion object{
        val IMDB_ID = "IMDB_ID"
    }

    private var activeFragmentt: Fragment? =null

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        addHomeFragment()
    }

    private fun addHomeFragment() {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, HomeFragment.newInstance(), HomeFragment.TAG)
        fragmentTransaction.commit()
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.imdbId.observe(this, Observer {
            addDetailFragment(it)
        })
    }

    private fun addDetailFragment(movieId: String?) {
        Log.d("Mytag", movieId)
        val bundle = Bundle()
        bundle.putString(IMDB_ID, movieId)
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, DetailsFragment.newInstance(bundle), DetailsFragment.TAG)
        val homeFrag = supportFragmentManager.findFragmentByTag(HomeFragment.TAG) as HomeFragment?
        if(homeFrag !=null){
            fragmentTransaction.hide(homeFrag)
        }
        fragmentTransaction.addToBackStack("stack")
        fragmentTransaction.commit()
    }

}
