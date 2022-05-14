package com.ihfazh.simpledorarnew.bookmark

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.ihfazh.simpledorarnew.data.DorarDatabase
import com.ihfazh.simpledorarnew.data.LocalSearchRepository
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