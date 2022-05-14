package com.ihfazh.simpledorar.data

import androidx.lifecycle.Transformations.map
import androidx.paging.*
import com.ihfazh.dorar.Dorar
import com.ihfazh.simpledorar.bookmark.BookmarkCategory
import com.ihfazh.simpledorar.bookmark.HadithBookmark
import com.ihfazh.simpledorar.bookmark.HadithBookmarkUI
import com.ihfazh.simpledorar.bookmark.models.BookmarkCategoryEntity
import com.ihfazh.simpledorar.note.BookmarkNote
import com.ihfazh.simpledorar.search.SearchQuery
import com.ihfazh.simpledorar.search.models.SearchQueryEntity
import com.ihfazh.simpledorar.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownServiceException


class LocalSearchRepository(database: DorarDatabase) : SearchRepositoryInterface {
    private val searchQueryDao = database.searchQueryDao()
    private val bookmarkDao = database.bookmarkDao()
    private val noteDao = database.noteDao()

    companion object {
        private var instance: LocalSearchRepository? = null
        fun getInstance(db: DorarDatabase): LocalSearchRepository =
            instance ?: synchronized(this){
                instance ?: LocalSearchRepository(db).also{
                    instance = it
                }
            }
    }

    override fun getHistoriesWithLimit(start: Int, limit: Int): Flow<PagingData<SearchQuery>>{
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = { searchQueryDao.getAll() },
        ).flow.map{ pagingData ->
            pagingData.map { entity ->
                SearchQuery(entity.id, entity.query, entity.timestamp)
            }
        }
    }

    override suspend fun deleteAllHistories() {
        searchQueryDao.deleteAll()
    }

    override suspend fun deleteHistory(id: Long){
        searchQueryDao.delete(id)
    }

    override suspend fun appendQuery(value: String): Flow<PagingData<SearchQuery>> {
        searchQueryDao.createOrUpdateTimestamp(value)
        return Pager(
            config = PagingConfig(10),
            pagingSourceFactory = {searchQueryDao.getAll()}
        ).flow.map{ pagingData ->
            pagingData.map { entity ->
                SearchQuery(entity.id, entity.query, entity.timestamp)
            }
        }
    }

    override suspend fun search(query: String, page: Int): DorarResponse {
        val dorar = Dorar()

        return try {
            val resultItem = dorar.search(query, page).toResultItem()
            DorarResponse.Success(resultItem)
        } catch (er: SocketTimeoutException){
            DorarResponse.Error("timed out")
        } catch (er: IOException){
            DorarResponse.Error("io error")
        } catch (er: UnknownServiceException){
            DorarResponse.Error("protocol error")
        } catch (er: Exception){
            DorarResponse.Error("unknown")
        }
    }

    override fun searchCategory(text: String?): Flow<PagingData<BookmarkCategory>> {
        val query = text?.let { "%$it%" }
        return bookmarkDao.searchCategory(query).toBookmarkCategory()
    }

    override fun getAllCategories(): Flow<PagingData<BookmarkCategory>> {
        return bookmarkDao.getAllCategories().toBookmarkCategory()
    }

    override suspend fun inputCategory(text: String): Long {
        val category = BookmarkCategoryEntity(0, text)
        return bookmarkDao.insertCategory(category)
    }

    override suspend fun saveHadith(hadithBookmark: HadithBookmark) {
        bookmarkDao.saveBookmark(hadithBookmark.toHadithBookmarkEntity())
    }

    override suspend fun deleteBookmarkHadith(hadithBookmark: HadithBookmark) {
        return bookmarkDao.deleteHadith(hadithBookmark.toHadithBookmarkEntity())
    }

    override fun searchCategorySync(constraint: CharSequence): List<BookmarkCategory> {
        return bookmarkDao.searchCategorySync("%$constraint%").toBookmarkCategory()
    }

    override fun getCategoriesWithHadith(): Flow<PagingData<BookmarkCategory>> {
        return Pager(
            PagingConfig(10),
        ) {
            bookmarkDao.getCategories()
        }.flow.map { pagingData ->
            pagingData.map {
                BookmarkCategory(it.id, it.title)
            }
        }
    }

    override fun categoryHadithList(id: Long): Flow<PagingData<HadithBookmarkUI>> {
        return Pager(
            PagingConfig(10)
        ) {
            bookmarkDao.getHadithList(id)
        }.flow.map { pagingData ->
            pagingData.map{
                val hadithBookmark = it.toHadithBookmarkEntity()
                HadithBookmarkUI(hadithBookmark)
            }
        }
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

    // NOTE feature

    override fun getNoteCategory(categoryId: Long): Flow<BookmarkNote?> {
        return noteDao.getNote(categoryId).toBookmarkNote()
    }

    override suspend fun createOrUpdateNote(bookmarkNote: BookmarkNote) {
        return noteDao.createOrUpdate(bookmarkNote.toBookmarkNoteEntity())
    }
}

