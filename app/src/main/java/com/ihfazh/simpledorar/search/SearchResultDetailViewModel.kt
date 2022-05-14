package com.ihfazh.simpledorar.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.map
import androidx.paging.PagingData
import com.ihfazh.simpledorar.bookmark.BookmarkCategory
import com.ihfazh.simpledorar.bookmark.HadithBookmark
import com.ihfazh.simpledorar.data.DorarDatabase
import com.ihfazh.simpledorar.data.LocalSearchRepository
import com.ihfazh.simpledorar.data.SearchRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchResultDetailViewModel(application: Application): AndroidViewModel(application) {
//    private val repo : SearchRepositoryInterface
    val selectedBookmark = MutableLiveData<BookmarkCategory>()

    val enableSaveHadith = MutableLiveData(false)

    val searchText = MutableLiveData<String?>(null)
    val repo = DorarDatabase.getInstance(application.applicationContext).let{
        LocalSearchRepository.getInstance(it)
    }

    @ExperimentalCoroutinesApi
    val categories: Flow<PagingData<BookmarkCategory>> = searchText.asFlow().flatMapLatest {
        repo.searchCategory(it)
    }


    fun saveHadith(hadith: ResultItem){
        if (selectedBookmark.value == null) return

        // if no selected bookmark, create it then use it
        viewModelScope.launch(Dispatchers.IO) {
            repo.saveHadith(HadithBookmark(
                0,
                hadith.rawText,
                hadith.rawi,
                hadith.mohaddith,
                hadith.mashdar,
                hadith.shafha,
                hadith.hokm,
                selectedBookmark.value!!
            ))
            searchText.postValue("")
        }

    }


    fun setSelectedBookmark(bookmarkCategory: BookmarkCategory) {
        viewModelScope.launch(Dispatchers.IO) {
            if (bookmarkCategory.id == 0L){
                val id = repo.inputCategory(bookmarkCategory.title)
                selectedBookmark.postValue(bookmarkCategory.copy(id=id))
            } else {
                selectedBookmark.postValue(bookmarkCategory)
            }

            enableSaveHadith.postValue(true)
        }

    }
}