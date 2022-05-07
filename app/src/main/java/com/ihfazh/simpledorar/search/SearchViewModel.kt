package com.ihfazh.simpledorar.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihfazh.simpledorar.data.LocalSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    private val repo = LocalSearchRepository()

    val searchState: MutableLiveData<SearchState> = MutableLiveData(SearchState.NoHistory)

    val query = MutableLiveData("")
    val histories: MutableLiveData<List<SearchQuery>> = MutableLiveData(listOf())
    val searchResults: MutableLiveData<List<ResultItem>> = MutableLiveData()
    private val page = MutableLiveData(1)

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

        viewModelScope.launch(Dispatchers.IO){
            if (!isFromHistory){
                val _histories = repo.appendQuery(q)
                histories.postValue(_histories)
            }

            val currentResults = searchResults.value

            val results = repo.search(q, page.value!!)
            val finalResults = if (currentResults.isNullOrEmpty()) results else currentResults + results
            searchResults.postValue(finalResults)

            if (results.isEmpty()) {
                searchState.postValue(SearchState.NoSearchResult)
            } else {
                searchState.postValue(SearchState.SearchResult)
            }

        }

    }

    fun backToHistory() {
        query.value = ""
        page.value = 1
        searchResults.value = listOf()
        searchState.value = SearchState.HasHistory
    }

    fun searchNext() {
        page.value = page.value!! + 1
        search(query.value)
    }

    companion object {
        private val TAG = SearchViewModel::class.java.simpleName
    }


}