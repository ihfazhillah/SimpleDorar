package com.ihfazh.simpledorarnew.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ihfazh.simpledorarnew.data.DorarDatabase
import com.ihfazh.simpledorarnew.data.DorarResponse
import com.ihfazh.simpledorarnew.data.LocalSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): AndroidViewModel(application) {
    private val db = DorarDatabase.getInstance(application.applicationContext)
    private val repo = LocalSearchRepository.getInstance(db)

    val searchState: MutableLiveData<SearchState> = MutableLiveData(SearchState.NoHistory)

    val query = MutableLiveData("")
//    val histories: MutableLiveData<List<SearchQuery>> = MutableLiveData(listOf())
    val searchResults: MutableLiveData<List<ResultItem>> = MutableLiveData()
    val isLoading = MutableLiveData(false)
    val message = MutableLiveData("")

    val histories = repo.getHistoriesWithLimit()

    private val page = MutableLiveData(1)


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

            val results: DorarResponse = repo.search(q, page.value!!)

            when(results){
                is DorarResponse.Success -> {
                    val finalResults = if (currentResults.isNullOrEmpty()) results.hadithList else currentResults + results.hadithList
                    searchResults.postValue(finalResults)

                    if (finalResults.isEmpty()) {
                        searchState.postValue(SearchState.NoSearchResult)
                    } else {
                        searchState.postValue(SearchState.SearchResult)
                    }
                }
                is DorarResponse.Error -> {
                    message.postValue("Uh oh, something wrong happens. Check your connection")
                }
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