package com.souptik.maiti.goscaleassignment.ui.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.souptik.maiti.goscaleassignment.R
import com.souptik.maiti.goscaleassignment.data.local.entities.MovieBookmark
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

    var movieDetails: MovieDetails ?= null
    var movieBookmark: MovieBookmark ?= null

    override fun provideLayoutId(): Int = R.layout.fragment_details

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupObservers() {
        super.setupObservers()
        val bundle: Bundle? = this.arguments
        if(bundle != null){
            imdbId = bundle.getString(IMDB_ID, null )
        }

        viewModel.movieDetails.observe(this, Observer {
            movieDetails = it
            setMovieBookmark(it)
            refreshView(it)
        })
    }

    private fun refreshView(movieDetails: MovieDetails) {
        tv_title.text = "Title: " + movieDetails.title
        tv_rating.text = "IMDB Ratings: " + movieDetails.imdbRating + " /10"
        tv_country.text = "Country: " + movieDetails.country
        tv_genre.text = "Genre: " + movieDetails.genre
        tv_runtime.text = "Runtime: " + movieDetails.runtime
        tv_year.text = "Year: " + movieDetails.year
        tv_language.text = "Language: " + movieDetails.language
        Glide.with(context!!).load(movieDetails.poster).into(iv_movie)
    }

    private fun setMovieBookmark(movieDetails: MovieDetails){
        movieBookmark = MovieBookmark(name = movieDetails.title, imageUrl = movieDetails.poster, imdbID = movieDetails.imdbID)
    }

    override fun setupView(view: View) {
        if(imdbId != null) {
            viewModel.getMovieDetails(imdbId!!)
        }
        iv_bookmark.setOnClickListener {
            if(movieBookmark != null) {
                viewModel.toggleBookmark(movieBookmark!!)
            }
            //return@setOnLongClickListener true
        }
    }

}
