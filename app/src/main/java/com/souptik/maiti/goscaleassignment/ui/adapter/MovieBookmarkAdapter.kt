package com.souptik.maiti.goscaleassignment.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.souptik.maiti.goscaleassignment.R
import com.souptik.maiti.goscaleassignment.data.local.entities.MovieBookmark
import kotlinx.android.synthetic.main.bookmark_layout.view.*

class MovieBookmarkAdapter(private val bookMarkedList: ArrayList<MovieBookmark>): RecyclerView.Adapter<MovieBookmarkAdapter.BookmarkViewHolder>() {

    var bookmarkListener: BookmarkSelectListener ?= null
    var movieSelectListener: MovieSelectListener? = null

    inner class BookmarkViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(movieBookmark: MovieBookmark){
            itemView.tv_title.text = movieBookmark.name
            Glide.with(itemView).load(movieBookmark.imageUrl).into(itemView.iv_movie)

            itemView.rl_book.setOnClickListener {
                if(movieSelectListener != null){
                    movieSelectListener!!.selectMovie(movieBookmark.imdbID)
                }
            }

            itemView.iv_cancel.setOnClickListener {
                if(bookmarkListener != null){
                    bookmarkListener!!.toggleBookmark(movieBookmark)
                }
            }

            itemView.rl_book.setOnLongClickListener {
                if(bookmarkListener != null){
                    bookmarkListener!!.toggleBookmark(movieBookmark)
                }
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return BookmarkViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bookmark_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = bookMarkedList.size

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(bookMarkedList[position])
    }

    fun refreshData(dataList: List<MovieBookmark>){
        bookMarkedList.clear()
        bookMarkedList.addAll(dataList)
        notifyDataSetChanged()
    }
}