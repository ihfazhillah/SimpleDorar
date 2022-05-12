package com.ihfazh.simpledorar.note

data class BookmarkNote (
    val id : Long = 0,
    val text: String,
    val timestamp: Long,

    val categoryId: Long
)