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
        val HOME_TITLE = "Movies List with Bookmarks"
        val DETAILS_TITLE = "Movie Details"
    }

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        if(savedInstanceState == null) {
            addHomeFragment()
        }
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.imdbId.observe(this, Observer {
            addDetailFragment(it)
        })
    }

    private fun addHomeFragment() {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        var homeFrag = supportFragmentManager.findFragmentByTag(HomeFragment.TAG) as HomeFragment?
        var detailfrag = supportFragmentManager.findFragmentByTag(DetailsFragment.TAG) as DetailsFragment?
        if(detailfrag != null){
            fragmentTransaction.hide(detailfrag)
        }
        if(homeFrag == null) {
            homeFrag = HomeFragment.newInstance(null)
            fragmentTransaction.add(
                R.id.fragment_container,homeFrag,
                HomeFragment.TAG
            )
        }
        fragmentTransaction.commit()
    }

    private fun addDetailFragment(movieId: String?) {
        Log.d("Mytag", movieId)
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        var detailFrag = supportFragmentManager.findFragmentByTag(DetailsFragment.TAG) as DetailsFragment?
        var homeFrag = supportFragmentManager.findFragmentByTag(HomeFragment.TAG) as HomeFragment?
        if (homeFrag != null) {
            fragmentTransaction.hide(homeFrag)
        }
        if (detailFrag != null){
            fragmentTransaction.show(detailFrag)
        }
        if (detailFrag == null){
            val bundle = Bundle()
            bundle.putString(IMDB_ID, movieId)
            detailFrag = DetailsFragment.newInstance(bundle)
            fragmentTransaction.add(R.id.fragment_container, detailFrag, DetailsFragment.TAG)
            fragmentTransaction.addToBackStack("stack")
        }

        fragmentTransaction.commit()
    }

}
