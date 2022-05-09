package com.ihfazh.simpledorar.bookmark

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.ihfazh.simpledorar.data.DorarDatabase
import com.ihfazh.simpledorar.data.LocalSearchRepository
import com.ihfazh.simpledorar.search.ResultItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class BookmarkDetailViewModel(application: Application): AndroidViewModel(application) {
    private val repo by lazy {
        LocalSearchRepository.getInstance(DorarDatabase.getInstance(application.applicationContext))
    }

//    private var bookmarkId : Long? = null
//    fun setBookmarkId(id: Long){
//        bookmarkId = id
//    }
//
//    val hadithList = repo.categoryHadithList(bookmarkId).asLiveData()

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

    companion object {
        private val TAG = BookmarkDetailViewModel::class.java.simpleName
    }
}