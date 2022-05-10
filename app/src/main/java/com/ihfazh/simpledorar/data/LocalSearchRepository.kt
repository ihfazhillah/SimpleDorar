package com.ihfazh.simpledorar.data

import com.ihfazh.dorar.Dorar
import com.ihfazh.simpledorar.bookmark.BookmarkCategory
import com.ihfazh.simpledorar.bookmark.HadithBookmark
import com.ihfazh.simpledorar.bookmark.HadithBookmarkUI
import com.ihfazh.simpledorar.bookmark.models.BookmarkCategoryEntity
import com.ihfazh.simpledorar.bookmark.models.BookmarkWithHadiths
import com.ihfazh.simpledorar.bookmark.models.HadithBookmarkEntity
import com.ihfazh.simpledorar.search.ResultItem
import com.ihfazh.simpledorar.search.SearchQuery
import com.ihfazh.simpledorar.utils.toResultItem
import com.ihfazh.simpledorar.utils.toSearchQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalSearchRepository(database: DorarDatabase) : SearchRepositoryInterface {
    private val searchQueryDao = database.searchQueryDao()
    private val bookmarkDao = database.bookmarkDao()

    companion object {
        private var instance: LocalSearchRepository? = null
        fun getInstance(db: DorarDatabase): LocalSearchRepository =
            instance ?: synchronized(this){
                instance ?: LocalSearchRepository(db).also{
                    instance = it
                }
            }
    }

    override suspend fun getHistoriesWithLimit(start: Int, limit: Int): Flow<List<SearchQuery>> {
        return searchQueryDao.getAll().toSearchQuery()
    }

    override suspend fun deleteAllHistories() {
        searchQueryDao.deleteAll()
    }

    override suspend fun deleteHistory(id: Long){
        searchQueryDao.delete(id)
    }

    override suspend fun appendQuery(value: String): Flow<List<SearchQuery>> {
        searchQueryDao.createOrUpdateTimestamp(value)
        return searchQueryDao.getAll().toSearchQuery()
    }

    override suspend fun search(query: String, page: Int): List<ResultItem> {
        return Dorar().search(query, page).toResultItem()
    }

    override suspend fun searchCategory(text: String): Flow<List<BookmarkCategory>> {
        return bookmarkDao.searchCategory(text).toBookmarkCategory()
    }

    override suspend fun inputCategory(text: String): Long {
        val category = BookmarkCategoryEntity(0, text)
        return bookmarkDao.insertCategory(category)
    }

    override suspend fun saveHadith(hadithBookmark: HadithBookmark) {
        bookmarkDao.saveBookmark(hadithBookmark.toHadithBookmarkEntity())
    }

    override fun searchCategorySync(constraint: CharSequence): List<BookmarkCategory> {
        return bookmarkDao.searchCategorySync("%$constraint%").toBookmarkCategory()
    }

    override fun getCategoriesWithHadith(): Flow<List<BookmarkCategory>> {
        return bookmarkDao.getCategories().toBookmarkCategory()
    }

    override fun categoryHadithList(id: Long): Flow<List<HadithBookmarkUI>> {
        return bookmarkDao.getHadithList(id).toHadithBookmarkList()
    }

    override suspend fun updateBookmark(category: BookmarkCategory) {
        category.toBookmarkCategoryEntity().let { category ->
            bookmarkDao.updateCategory(category)
        }
    }

    override suspend fun deleteBookmark(category: BookmarkCategory) {
        category.toBookmarkCategoryEntity().let { category ->
            bookmarkDao.deleteCategory(category)
        }
    }
}

private fun BookmarkCategory.toBookmarkCategoryEntity(): BookmarkCategoryEntity  =
    BookmarkCategoryEntity(id, title)


private fun Flow<List<HadithBookmarkEntity>>.toHadithBookmarkList(): Flow<List<HadithBookmarkUI>> {
    return map {  bookmarks ->
        bookmarks.map{ entity ->
            val hadithBookmark = HadithBookmark(
                    entity.id,
                    entity.rawText,
                    entity.rawi,
                    entity.mohaddith,
                    entity.mashdar,
                    entity.shafha,
                    entity.hokm,
                )
            HadithBookmarkUI(hadithBookmark)
        }

    }

}

private fun HadithBookmarkEntity.toHadithBookmarkEntity(): HadithBookmark {
    return HadithBookmark(id, rawText, rawi, mohaddith, mashdar, shafha, hokm)
}

private fun HadithBookmark.toHadithBookmarkEntity(): HadithBookmarkEntity = HadithBookmarkEntity(
    id, rawText, rawi, mohaddith, mashdar, shafha, hokm, category?.id ?: 0L
)



private fun List<BookmarkCategoryEntity>.toBookmarkCategory(): List<BookmarkCategory> {
    return map {
        BookmarkCategory(it.id, it.title)
    }

}

private fun Flow<List<BookmarkCategoryEntity>>.toBookmarkCategory(): Flow<List<BookmarkCategory>>  =
    map{ bookmarks ->
        bookmarks.map{ bookmark ->
            BookmarkCategory(bookmark.id, bookmark.title)
        }
    }

