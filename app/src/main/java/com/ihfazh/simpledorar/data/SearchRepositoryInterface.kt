package com.ihfazh.simpledorar.data

import com.ihfazh.simpledorar.search.ResultItem
import com.ihfazh.simpledorar.search.SearchQuery
import kotlinx.coroutines.flow.Flow

interface SearchRepositoryInterface {
    suspend fun getHistoriesWithLimit(start: Int = 0, limit: Int = 0): Flow<List<SearchQuery>>
    suspend fun deleteAllHistories()
    suspend fun deleteHistory(id: Long)
    suspend fun appendQuery(value: String): Flow<List<SearchQuery>>
    suspend fun search(query: String, page: Int = 0): List<ResultItem>
}