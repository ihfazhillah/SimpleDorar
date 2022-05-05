package com.ihfazh.simpledorar.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihfazh.simpledorar.data.LocalSearchRepository
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    private val repo = LocalSearchRepository()

    val searchState: MutableLiveData<SearchState> = MutableLiveData(SearchState.NoHistory)

    val query = MutableLiveData("")
    val histories: MutableLiveData<List<SearchQuery>> = MutableLiveData(listOf())

    init {
        viewModelScope.launch {
            val historyItems = repo.getHistoriesWithLimit()
            if (historyItems.isNotEmpty()){
                searchState.postValue(SearchState.HasHistory)
            }
            histories.postValue(historyItems)
        }
    }

    fun deleteAllQueries(){
        viewModelScope.launch {
            repo.deleteAllHistories()
            histories.postValue(listOf())
            searchState.postValue(SearchState.NoHistory)
        }
    }

    fun deleteQuery(id: Long){
        viewModelScope.launch {
            val remains = repo.deleteHistory(id)
            if (remains.isEmpty()){
                searchState.postValue(SearchState.NoHistory)
            }
            histories.postValue(remains)
        }
    }
}