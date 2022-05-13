package com.ihfazh.simpledorar.search.models

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
abstract class SearchQueryDao {
    @Query("select * from search_query order by timestamp desc")
//    abstract fun getAll(): Flow<List<SearchQueryEntity>>
    abstract fun getAll(): PagingSource<Int, SearchQueryEntity>

    @Query("select * from search_query where query = :query")
    abstract fun getByQuery(query: String): SearchQueryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(searchQuery: SearchQueryEntity)

    @Update
    abstract fun update(searchQuery: SearchQueryEntity)

    @Transaction
    open fun createOrUpdateTimestamp(q: String){
        val query = getByQuery(q)
        val timestamp = Date().time
        if (query == null){
            val newQuery = SearchQueryEntity(0, q, timestamp)
            return insert(newQuery)
        }

        return update(query.copy(timestamp = timestamp))
    }


    @Query("delete from search_query where id = :id")
    abstract fun delete(id: Long)

    @Query("delete from search_query")
    abstract fun deleteAll()
}