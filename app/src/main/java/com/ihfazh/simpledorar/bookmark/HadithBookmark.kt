package com.ihfazh.simpledorar.bookmark


data class HadithBookmark(
    val id: Long,
    val rawText: String,
    val rawi: String,
    val mohaddith: String,
    val mashdar: String,
    val shafha: String,
    val hokm: String,
    val category: BookmarkCategory
)
