package com.souptik.maiti.goscaleassignment.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.souptik.maiti.goscaleassignment.R
import com.souptik.maiti.goscaleassignment.data.local.entities.MovieBookmark
import com.souptik.maiti.goscaleassignment.data.remote.response.Movie
import kotlinx.android.synthetic.main.item_layout.view.*

class MoviePagedListAdapter: PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()){

    var movieSelectListener: MovieSelectListener? = null
    var bookmarkListener: BookmarkSelectListener ?= null

    inner class MovieItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(movie: Movie?){
            itemView.tv_title.text = movie?.title
            itemView.tv_year.text = movie?.year
            Glide.with(itemView).load(movie?.poster).into(itemView.iv_poster)

            itemView.rl_movie.setOnClickListener {
                if(movieSelectListener !=null && movie !=null){
                    movieSelectListener!!.selectMovie(movie.imdbID)
                }
            }

            itemView.iv_fav.setOnClickListener {
                if(bookmarkListener != null && movie !=null){
                    var movieBookmark = MovieBookmark(imdbID = movie.imdbID, name = movie.title, imageUrl = movie.poster)
                    bookmarkListener!!.toggleBookmark(movieBookmark)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieItemViewHolder).bind(getItem(position))
    }

    class MovieDiffCallback: DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.imdbID == newItem.imdbID
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
}