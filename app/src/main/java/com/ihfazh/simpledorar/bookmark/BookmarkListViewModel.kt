package com.ihfazh.simpledorar.bookmark

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ihfazh.simpledorar.data.DorarDatabase
import com.ihfazh.simpledorar.data.LocalSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarkListViewModel(application: Application): AndroidViewModel(application) {

    private val repo = LocalSearchRepository.getInstance(DorarDatabase.getInstance(application.applicationContext))
    val bookmarks = repo.getCategoriesWithHadith().asLiveData(Dispatchers.IO)

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