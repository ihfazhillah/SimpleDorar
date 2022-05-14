package com.ihfazh.simpledorarnew.search.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_query")
data class SearchQueryEntity (
    @PrimaryKey(autoGenerate = true) val id: Long,

    @ColumnInfo
    val query: String,

    @ColumnInfo
    val timestamp: Long
)