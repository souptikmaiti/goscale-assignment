package com.souptik.maiti.goscaleassignment.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.souptik.maiti.goscaleassignment.data.local.entities.MovieBookmark


@Dao
interface MoviesDao {

    @Insert
    suspend fun insertMovie(moviesTable: MovieBookmark)

    @Delete
    suspend fun deleteMovie(moviesTable: MovieBookmark)

    @Query("select * from movies_table order by name")
    fun getAllMovies(): LiveData<List<MovieBookmark>>
}