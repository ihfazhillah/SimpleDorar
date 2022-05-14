package com.ihfazh.simpledorarnew.data

import com.ihfazh.simpledorarnew.search.ResultItem

sealed interface DorarResponse{
    data class Success(val hadithList: List<ResultItem>): DorarResponse
    data class Error(val message: String): DorarResponse
}