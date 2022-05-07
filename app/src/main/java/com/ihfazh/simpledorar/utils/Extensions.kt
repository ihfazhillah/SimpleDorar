package com.ihfazh.simpledorar.utils

import com.ihfazh.dorar.HadithItem
import com.ihfazh.simpledorar.search.ResultItem
import com.ihfazh.simpledorar.search.ResultItemHighlight

fun List<HadithItem>.toResultItem(): List<ResultItem> =
    map{
        ResultItem(
            it.rawText,
            it.rawi,
            it.mohaddith,
            it.mashdar,
            it.shafha,
            it.hokm,
            it.highlights.map{ highlight ->
                ResultItemHighlight(highlight.start, highlight.end)
            }
        )
    }