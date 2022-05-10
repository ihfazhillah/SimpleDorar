package com.ihfazh.simpledorar.bookmark.models

import androidx.room.*
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

    @Update
    abstract suspend fun updateCategory(bookmarkCategoryEntity: BookmarkCategoryEntity)

    @Delete
    abstract suspend fun deleteCategory(bookmarkCategoryEntity: BookmarkCategoryEntity)

    @Query("select * from bookmark_category")
    abstract fun getCategoriesWithHadith(): Flow<List<BookmarkWithHadiths>>

    @Query("select * from bookmark_category")
    abstract fun getCategories(): Flow<List<BookmarkCategoryEntity>>

    @Query("""
        select * from hadith_bookmark
        where categoryId = :id
    """)
    abstract fun getHadithList(id: Long): Flow<List<HadithBookmarkEntity>>
}