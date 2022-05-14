package com.ihfazh.simpledorar.data

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.ihfazh.simpledorar.bookmark.BookmarkCategory
import com.ihfazh.simpledorar.bookmark.HadithBookmark
import com.ihfazh.simpledorar.bookmark.HadithBookmarkUI
import com.ihfazh.simpledorar.note.BookmarkNote
import com.ihfazh.simpledorar.search.ResultItem
import com.ihfazh.simpledorar.search.SearchQuery
import kotlinx.coroutines.flow.Flow

interface SearchRepositoryInterface {
    fun getHistoriesWithLimit(start: Int = 0, limit: Int = 0): Flow<PagingData<SearchQuery>>
    suspend fun deleteAllHistories()
    suspend fun deleteHistory(id: Long)
    suspend fun appendQuery(value: String): Flow<PagingData<SearchQuery>>
    suspend fun search(query: String, page: Int = 0): DorarResponse

    // BOOKMARK feature
    fun searchCategory(text: String?): Flow<PagingData<BookmarkCategory>>
    fun getAllCategories(): Flow<PagingData<BookmarkCategory>>
    suspend fun inputCategory(text: String): Long
    suspend fun saveHadith(hadithBookmark: HadithBookmark)
    suspend fun deleteBookmarkHadith(hadithBookmark: HadithBookmark)
    fun searchCategorySync(constraint: CharSequence): List<BookmarkCategory>
    fun getCategoriesWithHadith(): Flow<PagingData<BookmarkCategory>>
    fun categoryHadithList(id: Long): Flow<PagingData<HadithBookmarkUI>>
    suspend fun updateBookmark(category: BookmarkCategory)
    suspend fun deleteBookmark(category: BookmarkCategory)

    // NOTE feature
    fun getNoteCategory(categoryId: Long): Flow<BookmarkNote?>
    suspend fun createOrUpdateNote(bookmarkNote: BookmarkNote)
}