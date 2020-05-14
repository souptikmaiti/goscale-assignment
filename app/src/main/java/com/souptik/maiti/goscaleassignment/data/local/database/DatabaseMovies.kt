package com.souptik.maiti.goscaleassignment.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.souptik.maiti.goscaleassignment.data.local.dao.MoviesDao
import com.souptik.maiti.goscaleassignment.data.local.entities.MovieBookmark

@Database(entities = [MovieBookmark::class], version = 1)
abstract class DatabaseMovies : RoomDatabase(){
    abstract fun moviesDao(): MoviesDao

    companion object{

        @Volatile
        private var instance: DatabaseMovies? = null

        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, DatabaseMovies::class.java,
            "movies_bookmark.db").build()
    }
}