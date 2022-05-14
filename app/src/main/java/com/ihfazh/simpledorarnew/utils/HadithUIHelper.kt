package com.ihfazh.simpledorarnew.utils

import android.content.Context
import com.ihfazh.simpledorarnew.R
import com.ihfazh.simpledorarnew.bookmark.HadithBookmark

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