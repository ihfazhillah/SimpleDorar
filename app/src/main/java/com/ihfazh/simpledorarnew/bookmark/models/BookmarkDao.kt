package com.ihfazh.simpledorarnew.bookmark.models

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class BookmarkDao {

    @Insert
    abstract fun insertCategory(bookmarkCategoryEntity: BookmarkCategoryEntity): Long

    @Query("select * from bookmark_category where (:text is null or title like :text)")
    abstract fun searchCategory(text: String?): PagingSource<Int, BookmarkCategoryEntity>

    @Query("select * from bookmark_category")
    abstract fun getAllCategories(): PagingSource<Int, BookmarkCategoryEntity>

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
    abstract fun getCategories(): PagingSource<Int,BookmarkCategoryEntity>

    @Query("""
        select * from hadith_bookmark
        where categoryId = :id
    """)
    abstract fun getHadithList(id: Long): PagingSource<Int, HadithBookmarkEntity>

    @Delete
    abstract suspend fun deleteHadith(toHadithBookmarkEntity: HadithBookmarkEntity)
}