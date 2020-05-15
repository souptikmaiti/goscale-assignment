package com.souptik.maiti.goscaleassignment.ui.adapter

import com.souptik.maiti.goscaleassignment.data.local.entities.MovieBookmark

interface BookmarkSelectListener {
    fun toggleBookmark(movieBookmark: MovieBookmark)
}