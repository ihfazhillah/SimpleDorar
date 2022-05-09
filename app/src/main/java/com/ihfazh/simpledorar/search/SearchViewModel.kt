package com.ihfazh.simpledorar.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihfazh.simpledorar.data.DorarDatabase
import com.ihfazh.simpledorar.data.LocalSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): AndroidViewModel(application) {
    private val db = DorarDatabase.getInstance(application.applicationContext)
    private val repo = LocalSearchRepository.getInstance(db)

    val searchState: MutableLiveData<SearchState> = MutableLiveData(SearchState.NoHistory)

    val query = MutableLiveData("")
    val histories: MutableLiveData<List<SearchQuery>> = MutableLiveData(listOf())
    val searchResults: MutableLiveData<List<ResultItem>> = MutableLiveData()
    val isLoading = MutableLiveData(false)

    private val page = MutableLiveData(1)

    init {
        viewModelScope.launch (Dispatchers.IO){
            repo.getHistoriesWithLimit().collect { historyItems ->
                if (historyItems.isNotEmpty() and histories.value!!.isEmpty()){
                    searchState.postValue(SearchState.HasHistory)
                } else if (historyItems.isEmpty()) {
                    searchState.postValue(SearchState.NoHistory)
                }

                histories.postValue(historyItems)
            }
        }
    }

    fun deleteAllQueries(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAllHistories()
        }
    }

    fun deleteQuery(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteHistory(id)
        }
    }

    fun search(value: String? = null, forceNewRequest: Boolean = false) {
        isLoading.value = true
        val q = value ?: query.value!!
        val isFromHistory = value != null

        if (isFromHistory){
            query.value = q
        }

        if (forceNewRequest){
            page.value = 1
            searchResults.value = listOf()
        }

        viewModelScope.launch(Dispatchers.IO){
            repo.appendQuery(q)

            val currentResults = searchResults.value

            val results = repo.search(q, page.value!!)
            val finalResults = if (currentResults.isNullOrEmpty()) results else currentResults + results
            searchResults.postValue(finalResults)

            if (finalResults.isEmpty()) {
                searchState.postValue(SearchState.NoSearchResult)
            } else {
                searchState.postValue(SearchState.SearchResult)
            }

            isLoading.postValue(false)
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