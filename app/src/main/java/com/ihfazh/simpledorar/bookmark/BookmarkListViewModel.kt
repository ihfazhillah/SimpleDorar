package com.ihfazh.simpledorar.bookmark

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ihfazh.simpledorar.bookmark.listExapandable.BookmarkItemUI
import com.ihfazh.simpledorar.data.DorarDatabase
import com.ihfazh.simpledorar.data.LocalSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.math.exp

class BookmarkListViewModel(application: Application): AndroidViewModel(application) {
    private val bookmarksState = mutableMapOf<Long, Boolean>()
    private val repo = LocalSearchRepository.getInstance(DorarDatabase.getInstance(application.applicationContext))
    val bookmarks = repo.getCategoriesWithHadith().asLiveData(Dispatchers.IO)

//    init {
//        repo.getCategoriesWithHadith()
//    }

//    fun updateBookmarkState(id: Long){
//        // cheating maybe??
//        // update state
//        val currentState = bookmarksState.getOrDefault(id, false)
//        val nextState = !currentState
//        bookmarksState[id] = nextState
//
//        // update bookmarks
//        val currentBookmarks = bookmarks.value!!.map{
//            if (it.bookmarkCategory.id == id){
//                it.copy(expand = nextState)
//            } else {
//                it
//            }
//        }
//        bookmarks.value = currentBookmarks
//    }
}