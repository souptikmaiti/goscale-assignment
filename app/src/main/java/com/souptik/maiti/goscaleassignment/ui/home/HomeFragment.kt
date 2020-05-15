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
import com.souptik.maiti.goscaleassignment.data.local.entities.MovieBookmark
import com.souptik.maiti.goscaleassignment.di.components.FragmentComponent
import com.souptik.maiti.goscaleassignment.ui.adapter.BookmarkSelectListener
import com.souptik.maiti.goscaleassignment.ui.adapter.MovieBookmarkAdapter
import com.souptik.maiti.goscaleassignment.ui.adapter.MoviePagedListAdapter
import com.souptik.maiti.goscaleassignment.ui.adapter.MovieSelectListener
import com.souptik.maiti.goscaleassignment.ui.base.BaseFragment
import com.souptik.maiti.goscaleassignment.utils.Status
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
import javax.inject.Named

class HomeFragment : BaseFragment<HomeViewModel>(), MovieSelectListener, BookmarkSelectListener {

    companion object {
        const val TAG = "HomeFragment"

        fun newInstance(bundle: Bundle?): HomeFragment {
            val fragment = HomeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    @field:Named("Vertical")
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    @field:Named("Horizontal")
    lateinit var horizontalLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var movieAdapter: MoviePagedListAdapter

    @Inject
    lateinit var bookmarkAdapter: MovieBookmarkAdapter

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    override fun provideLayoutId(): Int = R.layout.fragment_home

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun setupView(view: View) {

        (activity as MainActivity).title = MainActivity.HOME_TITLE

        viewModel.setSearchString("friends")
        setHasOptionsMenu(true)
        rv_items.layoutManager = linearLayoutManager
        rv_items.setHasFixedSize(true)
        rv_items.adapter = movieAdapter
        movieAdapter.movieSelectListener = this
        movieAdapter.bookmarkListener = this

        rv_bookmarks.layoutManager = horizontalLinearLayoutManager
        rv_bookmarks.setHasFixedSize(true)
        rv_bookmarks.adapter = bookmarkAdapter
        bookmarkAdapter.bookmarkListener = this
        bookmarkAdapter.movieSelectListener = this

    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.moviePagedList.observe(this, Observer {
            if (it != null) {
                movieAdapter.submitList(it)
            }
        })

        viewModel.getAllBookmarkedMovies().observe(this, Observer {
            if(it != null) {
                bookmarkAdapter.refreshData(it)
            }
        })

        viewModel.bookmarkAdded.observe(this, Observer {
            if(it){
                Toaster.showShort(context!!, "Bookmark Added")
            }
        })

        viewModel.bookmarkDeleted.observe(this, Observer {
            if(it){
                Toaster.showShort(context!!, "Bookmark Deleted")
            }
        })

        viewModel.progressLiveData.observe(this, Observer {
            when(it.status){
                Status.LOADING ->{
                    progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS ->{
                    progressBar.visibility = View.GONE
                }
                Status.ERROR ->{
                    progressBar.visibility = View.GONE
                    Toaster.showLong(context!!, it.msg!!)
                }
            }
        })
    }

    override fun selectMovie(imdbId: String) {
        (activity as MainActivity).viewModel.setMovieId(imdbId)
    }

    override fun toggleBookmark(movieBookmark: MovieBookmark) {
        viewModel.toggleBookmark(movieBookmark)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.goscale_menu, menu)
        val searchItem = menu.findItem(R.id.search_menu)
        val searchView = searchItem.actionView as SearchView
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
