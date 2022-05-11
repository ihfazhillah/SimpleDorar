package com.ihfazh.simpledorar.data

import com.ihfazh.simpledorar.bookmark.BookmarkCategory
import com.ihfazh.simpledorar.bookmark.HadithBookmark
import com.ihfazh.simpledorar.bookmark.HadithBookmarkUI
import com.ihfazh.simpledorar.search.ResultItem
import com.ihfazh.simpledorar.search.SearchQuery
import kotlinx.coroutines.flow.Flow

interface SearchRepositoryInterface {
    suspend fun getHistoriesWithLimit(start: Int = 0, limit: Int = 0): Flow<List<SearchQuery>>
    suspend fun deleteAllHistories()
    suspend fun deleteHistory(id: Long)
    suspend fun appendQuery(value: String): Flow<List<SearchQuery>>
    suspend fun search(query: String, page: Int = 0): List<ResultItem>

    // BOOKMARK feature
    suspend fun searchCategory(text: String): Flow<List<BookmarkCategory>>
    suspend fun inputCategory(text: String): Long
    suspend fun saveHadith(hadithBookmark: HadithBookmark)
    suspend fun deleteBookmarkHadith(hadithBookmark: HadithBookmark)
    fun searchCategorySync(constraint: CharSequence): List<BookmarkCategory>
    fun getCategoriesWithHadith(): Flow<List<BookmarkCategory>>
    fun categoryHadithList(id: Long): Flow<List<HadithBookmarkUI>>
    suspend fun updateBookmark(category: BookmarkCategory)
    suspend fun deleteBookmark(category: BookmarkCategory)
}