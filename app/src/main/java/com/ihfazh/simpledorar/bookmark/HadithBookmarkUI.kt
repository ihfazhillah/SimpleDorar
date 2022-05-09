package com.ihfazh.simpledorar.bookmark

data class HadithBookmarkUI(
    val hadithBookmark: HadithBookmark,
    val isExpandHadith: Boolean = false
) {
    val id = hadithBookmark.id
    val rawText = hadithBookmark.rawText
    val rawi = hadithBookmark.rawi
    val mohaddith = hadithBookmark.mohaddith
    val mashdar = hadithBookmark.mashdar
    val shafha = hadithBookmark.shafha
    val hokm = hadithBookmark.hokm
    val category = hadithBookmark.category
}
