package com.ihfazh.simpledorar.data

import com.ihfazh.simpledorar.search.SearchQuery
import kotlinx.coroutines.flow.Flow

interface SearchRepositoryInterface {
    suspend fun getHistoriesWithLimit(start: Int = 0, limit: Int = 0): List<SearchQuery>
}