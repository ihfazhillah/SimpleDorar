package com.ihfazh.simpledorarnew.utils

import androidx.paging.*
import com.ihfazh.dorar.HadithItem
import com.ihfazh.simpledorarnew.bookmark.BookmarkCategory
import com.ihfazh.simpledorarnew.bookmark.HadithBookmark
import com.ihfazh.simpledorarnew.bookmark.HadithBookmarkUI
import com.ihfazh.simpledorarnew.bookmark.models.BookmarkCategoryEntity
import com.ihfazh.simpledorarnew.bookmark.models.HadithBookmarkEntity
import com.ihfazh.simpledorarnew.note.BookmarkNote
import com.ihfazh.simpledorarnew.note.models.BookmarkNoteEntity
import com.ihfazh.simpledorarnew.search.ResultItem
import com.ihfazh.simpledorarnew.search.ResultItemHighlight
import com.ihfazh.simpledorarnew.search.SearchQuery
import com.ihfazh.simpledorarnew.search.models.SearchQueryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val pagingConfig = PagingConfig(10)


fun List<HadithItem>.toResultItem(): List<ResultItem> =
    map{
        ResultItem(
            it.rawText,
            it.rawi,
            it.mohaddith,
            it.mashdar,
            it.shafha,
            it.hokm,
            it.highlights.map{ highlight ->
                ResultItemHighlight(highlight.start, highlight.end)
            }
        )
    }

fun Flow<List<SearchQueryEntity>>.toSearchQuery(): Flow<List<SearchQuery>> {
    return map{ list ->
        list.map { item ->
            SearchQuery(item.id, item.query, item.timestamp)
        }
    }
}

fun BookmarkNote.toBookmarkNoteEntity(): BookmarkNoteEntity {
    return BookmarkNoteEntity(id,text, timestamp, categoryId)
}

fun Flow<BookmarkNoteEntity?>.toBookmarkNote(): Flow<BookmarkNote?> {
    return map { entity ->
        entity?.let{
            BookmarkNote(it.id, it.text, it.timestamp, it.categoryId)
        }
    }
}

fun BookmarkCategory.toBookmarkCategoryEntity(): BookmarkCategoryEntity =
    BookmarkCategoryEntity(id, title)


fun PagingSource<Int, HadithBookmarkEntity>.toHadithBookmarkList(): Flow<PagingData<HadithBookmarkUI>> {
    return Pager(
        pagingConfig
    ) { this }
        .flow.map { pagingData ->
            pagingData.map{ entity ->
                val hadithBookmark = HadithBookmark(
                    entity.id,
                    entity.rawText,
                    entity.rawi,
                    entity.mohaddith,
                    entity.mashdar,
                    entity.shafha,
                    entity.hokm,
                )
                HadithBookmarkUI(hadithBookmark)
            }

        }
}

fun HadithBookmarkEntity.toHadithBookmarkEntity(): HadithBookmark {
    return HadithBookmark(id, rawText, rawi, mohaddith, mashdar, shafha, hokm)
}

fun HadithBookmark.toHadithBookmarkEntity(): HadithBookmarkEntity = HadithBookmarkEntity(
    id, rawText, rawi, mohaddith, mashdar, shafha, hokm, category?.id ?: 0L
)



fun List<BookmarkCategoryEntity>.toBookmarkCategory(): List<BookmarkCategory> {
    return map {
        BookmarkCategory(it.id, it.title)
    }

}

fun PagingSource<Int, BookmarkCategoryEntity>.toBookmarkCategory(): Flow<PagingData<BookmarkCategory>>  =
    Pager(
        pagingConfig
    ) {
        this
    }.flow.map { pagingData ->
        pagingData.map { entity ->
            BookmarkCategory(entity.id, entity.title)
        }
    }
