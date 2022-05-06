package com.ihfazh.simpledorar.search

data class ResultItemHighlight (
    val start: Int,
    val end: Int
)

data class ResultItem(
    val rawText: String,
    val rawi: String,
    val mohaddith: String,
    val mashdar: String,
    val shafha: String,
    val hokm: String,
    val highlights: List<ResultItemHighlight> = listOf()
) {
    fun getExcerpt(): String = rawText.take(75) + if (rawText.length > 75) "..." else ""
}
