package com.ihfazh.simpledorar.bookmark

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.ihfazh.simpledorar.data.DorarDatabase
import com.ihfazh.simpledorar.data.LocalSearchRepository
import com.ihfazh.simpledorar.data.SearchRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarkHadithBottomSheetFormViewModel(application: Application): AndroidViewModel(application) {
    private val repo : SearchRepositoryInterface = DorarDatabase.getInstance(application.applicationContext).let{
        LocalSearchRepository.getInstance(it)

    }

    val text = MutableLiveData("")
    val saveEnabled = Transformations.map(text){
        it.isNotEmpty()
    }
    private val selectedBookmarkCategory = MutableLiveData<BookmarkCategory>()

    fun setBookmark(bookmarkCategory: BookmarkCategory){
        selectedBookmarkCategory.value = bookmarkCategory
        text.value = bookmarkCategory.title
    }

    fun updateBookmark(){
        viewModelScope.launch(Dispatchers.IO){
            repo.updateBookmark(selectedBookmarkCategory.value!!.copy(title = text.value!!))
        }
    }
}