package com.ihfazh.simpledorar.note.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ihfazh.simpledorar.bookmark.BookmarkCategory
import com.ihfazh.simpledorar.bookmark.models.BookmarkCategoryEntity

@Entity(
    tableName = "bookmark_note",
    foreignKeys = [
        ForeignKey(BookmarkCategoryEntity::class, ["id"], ["categoryId"], ForeignKey.CASCADE),
    ]
)
data class BookmarkNoteEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val text: String,
    val timestamp: Long,

    // relationship
    val categoryId: Long
)