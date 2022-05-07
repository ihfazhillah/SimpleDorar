package com.ihfazh.simpledorar.search

import android.app.Application
import androidx.lifecycle.*
import com.ihfazh.simpledorar.bookmark.BookmarkCategory
import com.ihfazh.simpledorar.bookmark.HadithBookmark
import com.ihfazh.simpledorar.data.DorarDatabase
import com.ihfazh.simpledorar.data.LocalSearchRepository
import com.ihfazh.simpledorar.data.SearchRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchResultDetailViewModel(application: Application): AndroidViewModel(application) {
    private val repo : SearchRepositoryInterface
    val selectedBookmark = MutableLiveData<BookmarkCategory>()

    val enableSaveHadith = MutableLiveData(false)

    init {
        repo = DorarDatabase.getInstance(application.applicationContext).let{
            LocalSearchRepository(it)
        }

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
        }
    }

//    fun createBookmark(text: String) =
//        viewModelScope.launch(Dispatchers.IO){
//            repo.inputCategory(text)
//        }

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