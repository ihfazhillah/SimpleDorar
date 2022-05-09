package com.ihfazh.simpledorar.data

import com.ihfazh.simpledorar.bookmark.BookmarkCategory
import com.ihfazh.simpledorar.bookmark.BookmarkCategoryWithHadith
import com.ihfazh.simpledorar.bookmark.HadithBookmark
import com.ihfazh.simpledorar.bookmark.listExapandable.BookmarkItemUI
import com.ihfazh.simpledorar.search.ResultItem
import com.ihfazh.simpledorar.search.SearchQuery
import kotlinx.coroutines.flow.Flow

interface SearchRepositoryInterface {
    suspend fun getHistoriesWithLimit(start: Int = 0, limit: Int = 0): Flow<List<SearchQuery>>
    suspend fun deleteAllHistories()
    suspend fun deleteHistory(id: Long)
    suspend fun appendQuery(value: String): Flow<List<SearchQuery>>
    suspend fun search(query: String, page: Int = 0): List<ResultItem>

    suspend fun searchCategory(text: String): Flow<List<BookmarkCategory>>
    suspend fun inputCategory(text: String): Long
    suspend fun saveHadith(hadithBookmark: HadithBookmark)
    fun searchCategorySync(constraint: CharSequence): List<BookmarkCategory>
    fun getCategoriesWithHadith(): Flow<List<BookmarkCategory>>
}