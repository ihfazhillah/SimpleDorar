package com.ihfazh.simpledorar.bookmark.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class BookmarkDao {

    @Insert
    abstract fun insertCategory(bookmarkCategoryEntity: BookmarkCategoryEntity): Long

    @Query("select * from bookmark_category where title like :text")
    abstract fun searchCategory(text: String): Flow<List<BookmarkCategoryEntity>>

    @Query("select * from bookmark_category where title like :s")
    abstract fun searchCategorySync(s: String): List<BookmarkCategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveBookmark(hadithBookmark: HadithBookmarkEntity)
}