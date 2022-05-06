package com.ihfazh.simpledorar.search

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultItemHighlight (
    val start: Int,
    val end: Int
): Parcelable

@Parcelize
data class ResultItem(
    val rawText: String,
    val rawi: String,
    val mohaddith: String,
    val mashdar: String,
    val shafha: String,
    val hokm: String,
    val highlights: List<ResultItemHighlight> = listOf()
): Parcelable {
    fun getExcerpt(): String = rawText.take(75) + if (rawText.length > 75) "..." else ""
}
