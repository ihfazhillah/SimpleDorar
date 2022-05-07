package com.ihfazh.simpledorar.utils

import com.ihfazh.dorar.HadithItem
import com.ihfazh.simpledorar.search.ResultItem
import com.ihfazh.simpledorar.search.ResultItemHighlight
import com.ihfazh.simpledorar.search.SearchQuery
import com.ihfazh.simpledorar.search.models.SearchQueryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun List<HadithItem>.toResultItem(): List<ResultItem> =
    map{
        ResultItem(
            it.rawText,
            it.rawi,
            it.mohaddith,
            it.mashdar,
            it.shafha,
            it.hokm,
            it.highlights.map{ highlight ->
                ResultItemHighlight(highlight.start, highlight.end)
            }
        )
    }

fun Flow<List<SearchQueryEntity>>.toSearchQuery(): Flow<List<SearchQuery>> {
    return map{ list ->
        list.map { item ->
            SearchQuery(item.id, item.query, item.timestamp)
        }
    }
}