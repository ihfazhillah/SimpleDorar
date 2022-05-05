package com.ihfazh.simpledorar.data

import com.ihfazh.simpledorar.search.SearchQuery

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
}