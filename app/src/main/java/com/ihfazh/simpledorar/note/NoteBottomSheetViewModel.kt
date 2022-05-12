package com.ihfazh.simpledorar.note

import android.app.Application
import androidx.lifecycle.*
import com.ihfazh.simpledorar.data.DorarDatabase
import com.ihfazh.simpledorar.data.LocalSearchRepository
import com.ihfazh.simpledorar.data.SearchRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class NoteBottomSheetViewModel(application: Application): AndroidViewModel(application) {
    private val repo: SearchRepositoryInterface = DorarDatabase.getInstance(application.applicationContext).let{ db ->
        LocalSearchRepository.getInstance(db)
    }

    private val categoryId = MutableLiveData<Long>()

    private val _text = MutableLiveData("")
    val text : LiveData<String>
        get() = _text

    fun setText(value: String){
        _text.value = value
    }

   val note: MutableLiveData<BookmarkNote?> = MutableLiveData()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            categoryId.asFlow().collectLatest {
                repo.getNoteCategory(it).collectLatest{ _note ->
                    note.postValue(_note)
                    _text.postValue(_note?.text)
                }
            }

        }
    }

    fun createOrUpdateNote(){
        viewModelScope.launch (Dispatchers.IO){
            val currentNote = note.value

            currentNote?.copy(text = text.value ?: "")?.let { updatedNote ->
                repo.createOrUpdateNote(updatedNote)
            } ?: repo.createOrUpdateNote(
                BookmarkNote(text = text.value ?: "", timestamp = Date().time, categoryId = categoryId.value!!)
            )
        }
    }

    fun setCategoryId(categoryId: Long) {
        this.categoryId.value = categoryId
    }
}