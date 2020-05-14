package com.souptik.maiti.goscaleassignment.data.local.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class MovieBookmark (
    @ColumnInfo(name = "name")
    @NonNull
    val name: String,

    @ColumnInfo(name = "imbd_id")
    @NonNull
    val imdbID: String,

    @ColumnInfo(name = "image_url")
    @NonNull
    val imageUrl: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}