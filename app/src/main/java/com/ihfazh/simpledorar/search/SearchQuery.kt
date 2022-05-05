package com.ihfazh.simpledorar.search

data class SearchQuery(
    val id: Long = 0,
    val query: String,
    val timestamp: Long,
)
