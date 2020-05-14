package com.souptik.maiti.goscaleassignment.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

import com.souptik.maiti.goscaleassignment.R
import com.souptik.maiti.goscaleassignment.data.remote.response.MovieDetails
import com.souptik.maiti.goscaleassignment.di.components.FragmentComponent
import com.souptik.maiti.goscaleassignment.ui.base.BaseFragment
import com.souptik.maiti.goscaleassignment.ui.home.MainActivity.Companion.IMDB_ID
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : BaseFragment<DetailsViewModel>() {

    companion object {
        const val TAG = "DetailsFragment"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    var imdbId: String? = null

    override fun provideLayoutId(): Int = R.layout.fragment_details

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupObservers() {
        val bundle: Bundle? = this.arguments
        if(bundle != null){
            imdbId = bundle.getString(IMDB_ID, null )
        }
        super.setupObservers()

        viewModel.movieDetails.observe(this, Observer {
            refreshView(it)
        })
    }

    private fun refreshView(movieDetails: MovieDetails) {
        Glide.with(context!!).load(movieDetails.poster).into(iv_movie)
    }

    override fun setupView(view: View) {
        if(imdbId != null) {
            viewModel.getMovieDetails(imdbId!!)
        }
    }

}
