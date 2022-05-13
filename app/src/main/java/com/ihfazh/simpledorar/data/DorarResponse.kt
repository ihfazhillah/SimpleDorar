package com.ihfazh.simpledorar.data

import com.ihfazh.simpledorar.search.ResultItem

sealed interface DorarResponse{
    data class Success(val hadithList: List<ResultItem>): DorarResponse
    data class Error(val message: String): DorarResponse
}