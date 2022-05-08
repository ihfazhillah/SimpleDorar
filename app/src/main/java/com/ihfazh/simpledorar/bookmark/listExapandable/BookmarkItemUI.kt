package com.ihfazh.simpledorar.bookmark.listExapandable

import com.ihfazh.simpledorar.bookmark.BookmarkCategory
import com.ihfazh.simpledorar.bookmark.HadithBookmark

data class BookmarkItemUI(
    val bookmarkCategory: BookmarkCategory,
    val expand: Boolean = false,
    val items: List<HadithBookmark>
)