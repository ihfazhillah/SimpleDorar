package com.ihfazh.simpledorar.data

import com.ihfazh.simpledorar.search.SearchQuery
import java.util.*

class LocalSearchRepository : SearchRepositoryInterface {
    private var queries = listOf(
        SearchQuery(1, "ABCDEF", 123),
        SearchQuery(2, "ABCDEF", 124),
        SearchQuery(3, "ABCDEF", 125),
        SearchQuery(4, "ABCDEF", 126),
    )

    override suspend fun getHistoriesWithLimit(start: Int, limit: Int): List<SearchQuery> {
        return queries
    }

    override suspend fun deleteAllHistories() {
        queries = listOf()
    }

    override suspend fun deleteHistory(id: Long): List<SearchQuery>{
        queries = queries.filter { query -> query.id != id }
        return queries
    }

    override suspend fun appendQuery(value: String): List<SearchQuery> {
        val latestId = queries.lastOrNull()?.id ?: 0
        val newQuery = SearchQuery(latestId + 1, value, Date().time)
        queries = queries + newQuery
        return queries
    }
}