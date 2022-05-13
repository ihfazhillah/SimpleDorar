package com.ihfazh.simpledorar.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.ihfazh.simpledorar.bookmark.BookmarkCategory
import com.ihfazh.simpledorar.bookmark.HadithBookmark
import com.ihfazh.simpledorar.data.DorarDatabase
import com.ihfazh.simpledorar.data.LocalSearchRepository
import com.ihfazh.simpledorar.data.SearchRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SearchResultDetailViewModel(application: Application): AndroidViewModel(application) {
//    private val repo : SearchRepositoryInterface
    val selectedBookmark = MutableLiveData<BookmarkCategory>()

    val enableSaveHadith = MutableLiveData(false)

    val searchText = MutableLiveData<String?>(null)
    val repo = DorarDatabase.getInstance(application.applicationContext).let{
        LocalSearchRepository.getInstance(it)
    }
    val categories: LiveData<List<BookmarkCategory>> = searchText.switchMap{ text ->

        Log.d("SearchResultDetailViewModel", "current text: $text: ")
        repo.searchCategory(text).asLiveData()
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