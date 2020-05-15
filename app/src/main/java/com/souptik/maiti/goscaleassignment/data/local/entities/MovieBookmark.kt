package com.souptik.maiti.goscaleassignment.data.local.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class MovieBookmark (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "imbd_id")
    @NonNull
    val imdbID: String,

    @ColumnInfo(name = "name")
    @NonNull
    val name: String,

    @ColumnInfo(name = "image_url")
    @NonNull
    val imageUrl: String
)