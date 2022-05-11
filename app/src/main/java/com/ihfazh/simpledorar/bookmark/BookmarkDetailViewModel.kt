package com.ihfazh.simpledorar.bookmark

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ihfazh.simpledorar.data.DorarDatabase
import com.ihfazh.simpledorar.data.LocalSearchRepository
import com.ihfazh.simpledorar.search.ResultItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class BookmarkDetailViewModel(application: Application): AndroidViewModel(application) {
    private val repo by lazy {
        LocalSearchRepository.getInstance(DorarDatabase.getInstance(application.applicationContext))
    }


    private val expandedHadithList = MutableStateFlow<List<Long>>(listOf())
    fun toggleExpandedHadithList(id: Long){
        /*
        val found = expandedHadithList.value.indexOf(id) != -1
        val updatedExpandedList : List<Long> = if (found) {
            expandedHadithList.value.filter{ it != id}
        } else {
            expandedHadithList.value + id
        }
        expandedHadithList.value = updatedExpandedList
         */


        // functional expression :D
        expandedHadithList.value = expandedHadithList.value.let{ idList ->
            (idList.indexOf(id) != -1).let{ found ->
                if (found) idList.filter {it != id} else idList + id
            }
        }
    }

    fun getHadithList(id: Long): LiveData<List<HadithBookmarkUI>> =
            repo.categoryHadithList(id)
                .combine(expandedHadithList){ hadithUIs: List<HadithBookmarkUI>, expandedIdList: List<Long> ->
                    Log.d(TAG, "getHadithList: hadithUIs: $hadithUIs")
                    Log.d(TAG, "getHadithList: expandedList: $expandedIdList")

                    val updatedList = hadithUIs.map { ui ->
                        (expandedIdList.indexOf(ui.id) != -1).let { shouldExpand ->
                           ui.copy(isExpandHadith = shouldExpand)
                        }
                    }

                    Log.d(TAG, "getHadithList: updatedList = $updatedList")

                    updatedList
                }
                .flowOn(Dispatchers.IO)
                .conflate()
                .asLiveData()

    fun deleteHadith(bookmarkUI: HadithBookmarkUI) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteBookmarkHadith(bookmarkUI.hadithBookmark)
        }
    }

    companion object {
        private val TAG = BookmarkDetailViewModel::class.java.simpleName
    }
}