package com.ihfazh.simpledorarnew.search

data class SearchQuery(
    val id: Long = 0,
    val query: String,
    val timestamp: Long,
)
