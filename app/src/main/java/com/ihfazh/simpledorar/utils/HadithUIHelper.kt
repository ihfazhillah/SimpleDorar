package com.ihfazh.simpledorar.utils

import android.content.Context
import com.ihfazh.simpledorar.R
import com.ihfazh.simpledorar.bookmark.HadithBookmark

class HadithUIHelper(val context: Context) {
    fun setReference(hadithBookmark: HadithBookmark) =
        context.getString(
            R.string.reference_text,
            hadithBookmark.mohaddith,
            hadithBookmark.mashdar,
            hadithBookmark.shafha,
            hadithBookmark.rawi
        )
}