package com.ihfazh.simpledorarnew.bookmark.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmark_category")
data class BookmarkCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long,

    @ColumnInfo val title: String
)
