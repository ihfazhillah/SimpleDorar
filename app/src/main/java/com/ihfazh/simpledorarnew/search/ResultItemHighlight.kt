package com.ihfazh.simpledorarnew.search

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class ResultItemHighlight (
    val start: Int,
    val end: Int
): Parcelable