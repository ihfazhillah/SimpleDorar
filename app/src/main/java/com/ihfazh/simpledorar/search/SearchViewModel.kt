package com.ihfazh.simpledorar.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihfazh.simpledorar.data.LocalSearchRepository
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    private val repo = LocalSearchRepository()

    val searchState: MutableLiveData<SearchState> = MutableLiveData(SearchState.HasHistory)

    val query = MutableLiveData("")
    val histories: MutableLiveData<List<SearchQuery>> = MutableLiveData(listOf())

    init {
        viewModelScope.launch {
            val historyItems = repo.getHistoriesWithLimit()
            histories.postValue(historyItems)
        }
    }
}