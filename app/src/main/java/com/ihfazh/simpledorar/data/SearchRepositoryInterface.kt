package com.ihfazh.simpledorar.data

import com.ihfazh.simpledorar.search.ResultItem
import com.ihfazh.simpledorar.search.SearchQuery

interface SearchRepositoryInterface {
    suspend fun getHistoriesWithLimit(start: Int = 0, limit: Int = 0): List<SearchQuery>
    suspend fun deleteAllHistories()
    suspend fun deleteHistory(id: Long): List<SearchQuery>
    suspend fun appendQuery(value: String): List<SearchQuery>
    suspend fun search(query: String, page: Int = 0): List<ResultItem>
}