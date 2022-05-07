package com.ihfazh.simpledorar.data

import com.ihfazh.dorar.Dorar
import com.ihfazh.simpledorar.search.ResultItem
import com.ihfazh.simpledorar.search.ResultItemHighlight
import com.ihfazh.simpledorar.search.SearchQuery
import com.ihfazh.simpledorar.utils.toResultItem
import com.ihfazh.simpledorar.utils.toSearchQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import java.util.*

class LocalSearchRepository(private val database: DorarDatabase) : SearchRepositoryInterface {
    private val searchQueryDao = database.searchQueryDao()
    private var queries = listOf(
        SearchQuery(1, "ABCDEF", 123),
        SearchQuery(2, "ABCDEF", 124),
        SearchQuery(3, "ABCDEF", 125),
        SearchQuery(4, "ABCDEF", 126),
    )

    private var resultItems = listOf(
        ResultItem(
            "1 - أَتَاكُمْ أهْلُ اليَمَنِ، أضْعَفُ قُلُوبًا، وأَرَقُّ أفْئِدَةً، الفِقْهُ يَمَانٍ والحِكْمَةُ يَمَانِيَةٌ.",
            "أبو هريرة",
            "البخاري",
            "صحيح البخاري",
            "4390",
            "[صحيح]",
            listOf(
                ResultItemHighlight(4, 27)
            )
        )

    )

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
}

