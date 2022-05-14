package com.ihfazh.simpledorarnew.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ihfazh.simpledorarnew.bookmark.models.BookmarkCategoryEntity
import com.ihfazh.simpledorarnew.bookmark.models.BookmarkDao
import com.ihfazh.simpledorarnew.bookmark.models.HadithBookmarkEntity
import com.ihfazh.simpledorarnew.note.models.BookmarkNoteEntity
import com.ihfazh.simpledorarnew.note.models.NoteDao
import com.ihfazh.simpledorarnew.search.models.SearchQueryDao
import com.ihfazh.simpledorarnew.search.models.SearchQueryEntity

@Database(
    entities = [
        SearchQueryEntity::class,
        HadithBookmarkEntity::class,
        BookmarkCategoryEntity::class,
        BookmarkNoteEntity::class
    ],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(1, 2),
        AutoMigration(2, 3),
    ]
)
abstract class DorarDatabase : RoomDatabase(){
    abstract fun searchQueryDao(): SearchQueryDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun noteDao(): NoteDao

    companion object {
        private val TAG = DorarDatabase::class.java.simpleName

        private var INSTANCE : DorarDatabase? = null

        fun getInstance(context: Context): DorarDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context,
                    DorarDatabase::class.java,
                    DorarDatabase::class.java.simpleName
                ).build().also {
                    INSTANCE = it
                }
            }
    }

}