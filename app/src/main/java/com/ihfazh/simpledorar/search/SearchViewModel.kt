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
    val searchResults: MutableLiveData<List<ResultItem>> = MutableLiveData()

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

    fun search(value: String? = null) {
        val q = value ?: query.value!!
        val isFromHistory = value != null

        if (isFromHistory){
            query.value = q
        }

        viewModelScope.launch{
            if (!isFromHistory){
                val _histories = repo.appendQuery(q)
                histories.postValue(_histories)
            }

            val results = repo.search(q, 0)
            searchResults.postValue(results)

            if (results.isEmpty()) {
                searchState.postValue(SearchState.NoSearchResult)
            } else {
                Log.d(TAG, "search: $results ")
                searchState.postValue(SearchState.SearchResult)
            }

        }

    }

    fun backToHistory() {
        query.value = ""
        searchState.value = SearchState.HasHistory
    }

    companion object {
        private val TAG = SearchViewModel::class.java.simpleName
    }


}