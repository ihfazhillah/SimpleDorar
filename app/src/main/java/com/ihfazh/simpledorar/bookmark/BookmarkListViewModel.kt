package com.ihfazh.simpledorar.bookmark

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.ihfazh.simpledorar.data.DorarDatabase
import com.ihfazh.simpledorar.data.LocalSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BookmarkListViewModel(application: Application): AndroidViewModel(application) {

    private val repo = LocalSearchRepository.getInstance(DorarDatabase.getInstance(application.applicationContext))
    val bookmarks : Flow<PagingData<BookmarkCategory>> = repo.getCategoriesWithHadith()

    fun delete(bookmarkCategory: BookmarkCategory) {
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteBookmark(bookmarkCategory)
        }
    }

    fun update(bookmarkCategory: BookmarkCategory){
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateBookmark(bookmarkCategory)
        }
    }

}