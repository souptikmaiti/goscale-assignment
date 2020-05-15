package com.souptik.maiti.goscaleassignment.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.souptik.maiti.goscaleassignment.data.local.entities.MovieBookmark


@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(moviesTable: MovieBookmark)

    @Delete
    suspend fun deleteMovie(moviesTable: MovieBookmark)

    @Query("select * from movies_table order by name")
    fun getAllMovies(): LiveData<List<MovieBookmark>>

    @Query("delete from movies_table where imbd_id = :imbdId")
    suspend fun deleteRecord(imbdId: String)

    @Query("select imbd_id from movies_table")
    suspend fun getAllIds(): List<String>
}