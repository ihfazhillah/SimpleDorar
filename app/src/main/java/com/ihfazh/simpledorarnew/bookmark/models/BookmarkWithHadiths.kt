package com.ihfazh.simpledorarnew.bookmark.models

import androidx.room.Embedded
import androidx.room.Relation

data class BookmarkWithHadiths(
    @Embedded val bookmarkCategory: BookmarkCategoryEntity,
    @Relation(HadithBookmarkEntity::class, "id", "categoryId")
    val items: List<HadithBookmarkEntity>
)
