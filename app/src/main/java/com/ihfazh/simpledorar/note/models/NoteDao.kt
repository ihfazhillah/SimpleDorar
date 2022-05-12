package com.ihfazh.simpledorar.note.models

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
abstract class NoteDao {
    @Query("select * from bookmark_note where categoryId = :categoryId")
    abstract fun getNotes(categoryId: Long): Flow<List<BookmarkNoteEntity>>

    @Query("select * from bookmark_note where categoryId = :categoryId")
    abstract fun getNote(categoryId: Long): Flow<BookmarkNoteEntity?>

    @Query("select * from bookmark_note where categoryId = :id")
    abstract suspend fun getNoteById(id: Long): BookmarkNoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertBookmarkNote(bookmarkNoteEntity: BookmarkNoteEntity)


    @Update
    abstract fun updateBookmarkNote(bookmarkNoteEntity: BookmarkNoteEntity)

    @Transaction
    open suspend fun createOrUpdate(bookmarkNoteEntity: BookmarkNoteEntity){
        getNoteById(bookmarkNoteEntity.id)?.let{ dbNote ->
            updateBookmarkNote(bookmarkNoteEntity.copy(id = dbNote.id))
        } ?: insertBookmarkNote(bookmarkNoteEntity)
    }

    @Delete
    abstract fun removeBookmarkNote(bookmarkNoteEntity: BookmarkNoteEntity)
}