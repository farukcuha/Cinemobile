package com.pandorina.cinemobile.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_search_query_table", primaryKeys = ["query_name"])
data class MovieQuery(
    @ColumnInfo(name = "query_name") val query: String,
    @ColumnInfo(name = "query_time") val time: Long = System.currentTimeMillis()
) {

}