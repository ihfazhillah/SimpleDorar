package com.ihfazh.simpledorarnew.bookmark.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "hadith_bookmark",
    foreignKeys = [
        ForeignKey(BookmarkCategoryEntity::class, ["id"], ["categoryId"], ForeignKey.CASCADE)
    ]
)
data class HadithBookmarkEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo
    val rawText: String,
    @ColumnInfo
    val rawi: String,
    @ColumnInfo
    val mohaddith: String,
    @ColumnInfo
    val mashdar: String,
    @ColumnInfo
    val shafha: String,
    @ColumnInfo
    val hokm: String,

    // relationship
    @ColumnInfo
    val categoryId: Long
)