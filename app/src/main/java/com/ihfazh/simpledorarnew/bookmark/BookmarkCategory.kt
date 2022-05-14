package com.ihfazh.simpledorarnew.bookmark

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookmarkCategory(
    val id: Long,
    val title: String
): Parcelable
