package com.ihfazh.simpledorar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ihfazh.simpledorar.search.models.SearchQueryDao
import com.ihfazh.simpledorar.search.models.SearchQueryEntity

@Database(
    entities = [SearchQueryEntity::class],
    version = 1,
    exportSchema = true
)
abstract class DorarDatabase : RoomDatabase(){
    abstract fun searchQueryDao(): SearchQueryDao

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