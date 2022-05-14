package com.ihfazh.simpledorarnew.search

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
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
    fun getExcerpt(n: Int = 75): String = rawText.take(n) + if (rawText.length > n) "..." else ""
    fun getTextForClipboard(): String = """
        $rawText
        
        الراوي: $rawi
        محدث: $mohaddith
        مصدر: $mashdar
        صفحة / رقم: $shafha
        خلاصة حكم الحديث: $hokm
        
        *SimpleDorar* | Powered by dorar.net API
        https://play.google.com/store/apps/details?id=com.ihfazh.simpledorar
    """.trimIndent()
}
