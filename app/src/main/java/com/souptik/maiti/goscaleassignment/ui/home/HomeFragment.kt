package com.souptik.maiti.goscaleassignment.ui.home

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.souptik.maiti.goscaleassignment.R
import com.souptik.maiti.goscaleassignment.di.components.FragmentComponent
import com.souptik.maiti.goscaleassignment.ui.adapter.MoviePagedListAdapter
import com.souptik.maiti.goscaleassignment.ui.adapter.MovieSelectListener
import com.souptik.maiti.goscaleassignment.ui.base.BaseFragment
import com.souptik.maiti.goscaleassignment.utils.Toaster
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeViewModel>(), MovieSelectListener {

    companion object {
        const val TAG = "HomeFragment"

        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var movieAdapter: MoviePagedListAdapter

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    override fun provideLayoutId(): Int = R.layout.fragment_home

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupView(view: View) {
        viewModel.setSearchString("friends")
        setHasOptionsMenu(true)
        rv_items.layoutManager = linearLayoutManager
        rv_items.setHasFixedSize(true)
        rv_items.adapter = movieAdapter
        movieAdapter.movieSelectListener = this
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.moviePagedList.observe(this, Observer {
            if (it != null) {
                movieAdapter.submitList(it)
            }
        })
    }

    override fun selectMovie(imdbId: String) {
        (activity as MainActivity).viewModel.setMovieId(imdbId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.goscale_menu, menu)
        var searchItem = menu.findItem(R.id.search_menu)
        var searchView = searchItem.actionView as SearchView
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE)

        compositeDisposable.addAll(Observable.create(
            ObservableOnSubscribe<String>{emitter ->  
                searchView.setOnQueryTextListener (object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if(!emitter.isDisposed){
                            if(newText != null){
                                emitter.onNext(newText)
                            }
                        }
                        return false
                    }

                }
                )

            }
        ).debounce(1000, TimeUnit.MILLISECONDS)
            .filter {text-> text.isNotBlank() }
            .distinctUntilChanged()
            //.switchMap { query-> viewModel.fetchSearchResults(query) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    viewModel.setSearchString(it)
                },
                {

                }
            )
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}
